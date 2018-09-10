package com.company.actionexecutor.dumper;
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


import java.io.File;
import java.io.IOException;

/**
 * MemoryDumper class is used to do memory dump for given process.
 * This Process was referenced by ServerProcess class.
 * @see ServerProcess
 *
 * This class use Java Runtinme enviroment and jmap command to do memory dump.
 *
 *
 * @author thumilan@wso2.com
 */
public class MemoryDumper {

    /**
     * This string is used to represent process id.
     */
    private String processId;

    /**
     * Creates MemoryDumper with process id.
     * @param processId process id which used for memory dumping
     */
    public MemoryDumper(String processId) {
        this.processId = processId;


    }


    /**
     * Method used to do memory dump with using Java Runtime Environment and jmap command.
     * @param filepath
     */
    public void doMemoryDumping(String filepath) {


        if (new File(filepath).exists()) { // check whether file exists before dumping.
            String filename= "/heap-dump.hprof ";
            String frame =  System.getenv("JAVA_HOME")+ "/bin/jmap -dump:live,format=b,file=" + filepath + filename +  this.processId;
            try {
                Runtime.getRuntime().exec(frame);
            } catch (IOException e) {
                System.out.print("Unable to do Memory dump for " + processId);
            }

        }

    }

}
