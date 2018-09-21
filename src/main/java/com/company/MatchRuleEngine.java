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

import com.company.helper.XmlHelper;
import com.company.logtailer.Tailer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *This class used to match the regex and the logLine.
 *
 * @author thumilan@wso2.com
 */

 public class MatchRuleEngine {

    private String generalErrorRegex; // general error regex
    private String generalInfoRegex; // general info regex
    private String oomErrorRegex; // oom error regex
    private Interpreter interpreter; // initiate Interpreter
    private Pattern pattern;
    private Matcher matcher;
    private Boolean hasEngineApproved; // check whether the line is eligible or not
    private Boolean alreadyOccured; // check whether current error is already occurred or not

    private StringBuilder logLine; // LogLine object initiated

    private ArrayList<String> errortypesArrayList;

//    private ActionExecutorFactory actionExecutorFactory; // actionExecutorFactory to create executor objects
//
//    private ActionExecutor actionExecutor;


    public MatchRuleEngine() {

        this.hasEngineApproved = false;
        this.generalInfoRegex = XmlHelper.generalInfoRegex;
        this.generalErrorRegex = XmlHelper.generalErrorRegex;
        this.oomErrorRegex = XmlHelper.ErrorRegex;
        this.interpreter = new Interpreter();
        this.errortypesArrayList = new ArrayList<>();
    }

    /**
     * This method checks whether current testLine is match with Error regex.
     *
     * @param testLine the line.
     * @return boolean - true if matches.
     */
    private boolean checkInitialErrorMatch(String testLine) {

        pattern = Pattern.compile(generalErrorRegex);
        matcher = pattern.matcher(testLine);
        return matcher.find();
    }

    /**
     * This method checks whether current testLine is match with Info regex.
     *
     * @param testLine the line.
     * @return boolean - true if matches.
     */
    private boolean checkInitialInfoMatch(String testLine) {

        pattern = Pattern.compile(generalInfoRegex);
        matcher = pattern.matcher(testLine);
        return matcher.find();
    }

    /**
     * This method checks whether current testLine is match with Oom error regex.
     *
     * @param testLine the line.
     * @return boolean - true if matches.
     */
    private boolean checkOomErrorMatch(String testLine) {

        pattern = Pattern.compile(oomErrorRegex);
        matcher = pattern.matcher(testLine);
        return matcher.find();
    }

    /**
     * This method checks whether the current line is error line or not.
     * Based on the hasEngineApproved boolean parameter this method appends line
     *
     * @param testLine the current line.
     */
    public void validateTestline(String testLine) {

        if (hasEngineApproved) { // previous lines were approved by the engine.

            if (checkInitialErrorMatch(testLine)){

                //need to check repeatness

//                repeatedErrorStatement(testLine);
//                interpreter.doNetstat();
//                interpreter.doThreadDump();
                //interpreter.doMemoryDump();
//                interpreter.extractRequestID(testLine);
//                interpreter.doDBQueryDump();
            }
            else if(checkOomErrorMatch(testLine)){
                //interpreter.doNetstat();
                //interpreter.doThreadDump();
                //interpreter.doMemoryDump();
                //interpreter.extractRequestID(testLine);
                //interpreter.doDBQueryDump();
            }
            else if (checkInitialInfoMatch(testLine)) {
                // Check whether current line is InfoLine.
                // If so switch the boolean parameter into false and execute collected logLine.
                hasEngineApproved = false;
                if (interpreter != null) {
                    interpreter.writeLogLine(logLine);
                    interpreter.executeZipFileExecuter();
                }


            }
            else if (Tailer.isEnd(testLine)){
                hasEngineApproved = false;
                logLine.append(testLine);
                if (interpreter != null) {
                    interpreter.writeLogLine(logLine);
                    interpreter.executeZipFileExecuter();
                }

            }

            else {
                //hasEngineApproved remain true
                //current line also error line append it to logLine.
                logLine.append(testLine);
                //what if command end in parse mode how to get Log line???
            }


        } else { // previous lines were not approved by engine.
            if (checkInitialErrorMatch(testLine)) {
                // Check whether current line is error line.
                // If so switch the boolean parameter into true and create new string builder.
                
//                repeatedErrorStatement(testLine);
                hasEngineApproved = true;
                logLine = new StringBuilder();
                logLine.append(testLine);
                interpreter.createFolder();
                interpreter.executelsof();
//                interpreter.doNetstat();
//                interpreter.doThreadDump();
                //interpreter.doMemoryDump();
//                interpreter.extractRequestID(testLine);
//                interpreter.doDBQueryDump();

                // need to check repeatness




            }
        }
    }
    
    private boolean repeatedErrorStatement(String testline){
        Pattern pattern = Pattern.compile(XmlHelper.ErrorRegex);
        Matcher matcher = pattern.matcher(testline);
        if (matcher.find()){

            if (errortypesArrayList.isEmpty()){
                this.errortypesArrayList.add(matcher.group(1));
                System.out.print(Arrays.toString(errortypesArrayList.toArray()));
                System.out.print("\n\n");
                return false;
            }else{
                for (String matchline : errortypesArrayList){
                    if (matchline.compareTo(matcher.group(1))==0){
                        return true;
                    }
                }
                this.errortypesArrayList.add(matcher.group(1));
                System.out.print(Arrays.toString(errortypesArrayList.toArray()));
                System.out.print("\n\n");
                return false;
            }
        } else {
            return false;
        }
    }

}
