package com.company.threaddumper;
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

import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * This class is used to represent the wso2carbon server process.
 * This class is designed as singleton because there is only one process exists while running this tool
 *
 * @author thumilan@wso2.com
 */
public class ServerProcess {

    //private singleton serverProcess
    private static ServerProcess serverProcess;
    //private process id
    private String processId;

    /**
     * Private constructor.
     *
     */
    private ServerProcess() {
        RandomAccessFile file = null;
        try {
            // read the process id from the wso2carbon.pid file
            file = new RandomAccessFile(new XmlHelper().getPidFilePath(), "r");
            processId = file.readLine();
        } catch (IOException e) {
            System.out.print("wso2carbon.pid file is Not Found.");
            System.out.print("Please Check the Access Permission and pidpath in wso2conf.xml");
        }
    }

    /**
     * Static method for get singleton object.
     * @return ServerProcess singleton Object
     */
    public static synchronized ServerProcess getInstance() {
        if (serverProcess == null) {
            serverProcess = new ServerProcess();
        }
        return serverProcess;
    }

    /**
     * Getter method for processId.
     * @return String processId
     */
    public String getProcessId() {
        return processId;
    }

    public boolean isAlive() {
        try {

             return (Runtime.getRuntime().exec("ps " + processId).isAlive());
        } catch (IOException e) {

        }
        return false;
    }
}
