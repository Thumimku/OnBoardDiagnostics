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
 *
 * @author thumilan@wso2.com
 */
public class Interpreter {

    private ActionExecutorFactory actionExecutorFactory; // actionExecutorFactory to create executor objects

    private ActionExecutor actionExecutor;

    private String foldername;

    private String folderpath;

    private Boolean folderexists;



    public Interpreter() {
        this.actionExecutorFactory = new ActionExecutorFactory();
        this.actionExecutor = actionExecutorFactory.getActionExecutor("zipfileexecutor");
        createFolder();
        dothreaddump();

    }

    public void interpret(StringBuilder logLine){
        actionExecutor.execute(logLine, folderpath);
    }

    private void createFolder() {
        folderexists = false;
        folderpath = (System.getProperty("user.dir") + "/log/");
        foldername = new Timestamp(System.currentTimeMillis()).toString().replace(" ", "_");
        File dumpFolder = new File(folderpath + foldername);
        if (!dumpFolder.exists()) {
            try {
                dumpFolder.mkdir();
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

    private void dothreaddump() {
        ThreadDumper threadDumper = new ThreadDumper(ServerProcess.getInstance().getProcessId());
        threadDumper.doThreadDumping(folderpath);
    }
}
