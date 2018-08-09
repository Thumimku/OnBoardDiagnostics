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


import com.company.actionexecutor.ActionExecutor;
import com.company.actionexecutor.ActionExecutorFactory;
import com.company.threaddumper.ServerProcess;
import com.company.threaddumper.ThreadDumper;

import java.io.File;
import java.sql.Timestamp;

/**
 * Whenever there is error occur in Wso2server MatchRuleEngine detects it.
 * It invokes the methods of this class's instance.
 * This class is used to interpret the error and do appropriate actions for that error.
 *
 * Example error scenario :-
 *      When adn error occurs interpreter instance create a new folder name as current Time stamp.
 *      Then let threadDumper do thread dumper in that folder.
 *      Then invoke zip execution to write logLine in the folder and zip it.
 *
 * @see MatchRuleEngine
 * @see ThreadDumper
 * @see com.company.actionexecutor.ZipFileExecutor
 * @author thumilan@wso2.com
 */
public class Interpreter {

    private ActionExecutorFactory actionExecutorFactory; // actionExecutorFactory to create executor objects

    private ActionExecutor actionExecutor; // action execute instance to execute actions

    private String foldername; // folder name of a collective thread dumps (TimpStamp)

    private String folderpath; // folder path of the TimeStamp Folder

    private Boolean folderexists; //  check whether folder exists or not.


    /**
     * public Constructor.
     * Current action executor is set as zipFile executor
     * This constructor calls createFolder to create the Thread Dump folder and do thread dump.
     */
    public Interpreter() {
        this.actionExecutorFactory = new ActionExecutorFactory();
        this.actionExecutor = actionExecutorFactory.getActionExecutor("zipfileexecutor");
        createFolder();
        dothreaddump();

    }

    /**
     * Method used to interpret logLine.
     * Here fodler path is abstracted from MatchRule Engine
     * @param logLine
     */
    public void interpret(StringBuilder logLine) {

        actionExecutor.execute(logLine, folderpath);
    }

    /**
     * Create folder for collective thread dump.
     */
    private void createFolder() {

        folderexists = false; // Initially fileexists flag set as false
        folderpath = (System.getProperty("user.dir") + "/src/main/resources/log/"); // get log file path

        // folder name set as timestamp
        foldername = new Timestamp(System.currentTimeMillis()).toString().replace(" ", "_");
        File dumpFolder = new File(folderpath + foldername);
        if (!dumpFolder.exists()) {
            try {
                dumpFolder.mkdir(); // create folder if not exists.
                folderexists = true;
                folderpath = folderpath + foldername;
            } catch (SecurityException se) {
                //handle it
                System.out.print(se.getMessage());
            }
        } else {
            folderexists = true;
        }
    }

    /**
     * This method create ThreadDumper instance and do thread dump.
     * java process of wso2 server is referenced as ServerProcess
     */
    private void dothreaddump() {
        ThreadDumper threadDumper = new ThreadDumper(ServerProcess.getInstance().getProcessId());
        threadDumper.doThreadDumping(folderpath);
    }
}
