package com.company;
/*
 * Copyright (c) 2005-2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

import com.company.actionexecutor.LogLineWriter;
import com.company.actionexecutor.ZipFileExecutor;
import com.company.actionexecutor.diagnosticCommand.ActionExecutor;
import com.company.actionexecutor.diagnosticCommand.ActionExecutorFactory;
import com.company.helper.XmlHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Whenever there is error occur in Wso2server MatchRuleEngine detects it.
 * It invokes the methods of this class's instance.
 * This class is used to interpret the error and do appropriate actions for that error.
 * <p>
 * Example error scenario :-
 * When an error occurs interpreter instance create a new folder name as current Time stamp.
 * Then let threadDumper do thread dumper in that folder.
 * Then invoke zip execution to write logLine in the folder and zip it.
 *
 * @author thumilan@wso2.com
 * @see MatchRuleEngine
 * @see com.company.actionexecutor.ZipFileExecutor
 */
public class Interpreter {

    private ActionExecutorFactory actionExecutorFactory; // ActionExecutorFactory to create executor objects

    private String folderpath; // Folder path of the TimeStamp Folder

    //This HashMap used to map error type and their waiting time
    private static HashMap<String, Long> errorTimingMap = new HashMap<>();

    /**
     * public Constructor.
     * Current action executor is set as zipFile executor
     * This constructor calls createFolder to create the Thread Dump folder and do thread dump.
     */
    public Interpreter() {

        this.actionExecutorFactory = new ActionExecutorFactory();
        createLogFolder();

    }

    /**
     * Method used to interpret logLine.
     * This method checks the validity of the error line.
     * Valid error log lines will go under diagnosis process.
     * If the diagnosis succeeds then certain dump files and error log line will be dumped at time stamped folder.
     * @param logLine error log line
     */
    public void interpret(StringBuilder logLine) {

        //First check whether the error line is valid or not.
        if (this.checkValidity(logLine)) {
            //If it is a valid error then diagnose it.
            if (this.diagnoseError(logLine)) {
                //Write the error log line into the folder
                this.writeLogLine(logLine);
                //Zip the folder
                //this.executeZipFileExecuter();

            }

        }

    }

    /**
     * This method used to diagnose error.
     * First  match error regex to find the error.
     * Then check whether the error occurred recently or not.
     * Finally do the analysis.
     *
     * @param logLine error line
     * @return
     */
    private boolean diagnoseError(StringBuilder logLine) {

        for (String testRegex : ErrorInfo.getErrorSyndrome()) {
            Pattern pattern = Pattern.compile(testRegex);
            Matcher matcher = pattern.matcher(logLine);
            if (matcher.find()) {
                if (checkErrorTime(logLine.toString(), ErrorInfo.getErrortype(testRegex))) {
                    System.out.print(ErrorInfo.getErrorDescription(testRegex) + " Occurred...............\n");
                    this.createFolder();
                    this.doAnalysis(testRegex);
                    return true;
                }

            }

        }
        return false;

    }

    /**
     * This method is used to do analysis.
     * First get diagnose json array and invoke certain action executor
     *
     * @param testRegex errorRegex
     */
    private void doAnalysis(String testRegex) {

        JSONArray errorJsonArray = (JSONArray) ErrorInfo.getErrorDiagnosis(testRegex);
        for (Object object:errorJsonArray){
            JSONObject errorJsonObject = (JSONObject) object;
            ActionExecutor actionExecutor = actionExecutorFactory.getActionExecutor(errorJsonObject.get("executor").toString());
            if (actionExecutor != null) {
                actionExecutor.execute(this.folderpath);
            }


        }

    }

    /**
     * This method used to check validity of the error.
     *
     * @param logLine error log
     * @return
     */
    private boolean checkValidity(StringBuilder logLine) {

        return ((logLine.toString().split("\n")).length > 2);
    }

    /**
     * this method used to create the log folder.
     */
    private void createLogFolder() {

        folderpath = (System.getProperty("user.dir") + "/log/"); // get log file path

        File logfolder = new File(folderpath);
        if (!(logfolder.exists())) {
            logfolder.mkdir();
        }
    }

    /**
     * Create folder for dump.
     */

    public void createFolder() {

        folderpath = (System.getProperty("user.dir") + "/log/"); // get log file path

        File logfolder = new File(folderpath);
        if (!(logfolder.exists())) {
            logfolder.mkdir();
        }

        // folder name set as timestamp
        String foldername = new Timestamp(System.currentTimeMillis()).toString().replace(" ", "_");
        foldername = "WSO2_IS_@_"+foldername;
        File dumpFolder = new File(folderpath + foldername);
        if (!dumpFolder.exists()) {
            try {
                dumpFolder.mkdir(); // create folder if not exists.

                folderpath = folderpath + foldername;
            } catch (SecurityException se) {
                //handle it
                System.out.print(se.getMessage());
            }
        } else {

        }
    }

    /**
     * This method is used to call LogLine Writer to write the log line.
     *
     * @param Logline
     */
    public void writeLogLine(StringBuilder Logline) {

        LogLineWriter logLineWriter = new LogLineWriter();
        logLineWriter.execute(Logline, folderpath);
        System.out.print("Error Dumped in :" + folderpath + "\n");
    }

    /**
     * This method is used to call ZipFileExecutor to file the dump folder.
     */
    public void executeZipFileExecuter() {

        ZipFileExecutor zipFileExecutor = new ZipFileExecutor();
        zipFileExecutor.execute(null, folderpath);
    }

    /**
     * This method is used to check current error time and previous time when the same error occurred.
     * It also return whether tool has to do analysis for current error.
     *
     * @param testline error line
     * @param error    error type
     * @return
     */
    private boolean checkErrorTime(String testline, String error) {

        //Grep the first line of the error line.
        String[] errorLine = testline.split("\n");

        Pattern pattern = Pattern.compile(XmlHelper.TimeRegex);

        Matcher matcher = pattern.matcher(errorLine[0]);
        if (matcher.find()) {

            long errorTime = calculatetime(matcher.group(0));

            if (errorTimingMap.containsKey(error)) {
                // System.out.print((errorTimingMap.get(error)+" : "+errorTime+" : "+(errorTimingMap.get(error)-errorTime)+"\n"));
                if ((errorTime - errorTimingMap.get(error)) > 150L) {
                    errorTimingMap.replace(error, errorTime);
                } else {
                    return false;
                }

            } else {
                errorTimingMap.put(error, errorTime);
                return true;
            }
        }
        return true;
    }

    /**
     * This method is used to calculate current error time form log line.
     *
     * @param timeStr
     * @return
     */
    private int calculatetime(String timeStr) {

        String[] timeArray = timeStr.split(":");
        int hour = Integer.parseInt(timeArray[0]);
        int minute = Integer.parseInt(timeArray[1]);
        int second = Integer.parseInt(timeArray[0].substring(0, 2));
        return (hour * 3600) + (minute * 60) + second;
    }
}
