package com.company.actionexecutor;
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

/**
 * PrintLineExecutor used to print the logLines into the console.
 * Currently this class designed as a Singleton class inorder to optimise CPU usage.
 * Further research is required to change the design.
 *
 * ActionExecutor interface is implemented for code reuse.
 * @see ActionExecutor
 * @author thumilan@wso2.com
 */
public class PrintLineExecutor implements ActionExecutor {

    private static PrintLineExecutor printLineExecutor; // static instance for singleton method.

    /**
     * private Constructor.
     */
    private PrintLineExecutor() {
    }

    /**
     * Singleton method used to create object.
     * @return PrintLineExecutor
     */
    static synchronized PrintLineExecutor getInstance() {

        if (printLineExecutor == null) { // return new instance if current object is null.
            printLineExecutor = new PrintLineExecutor();
        }
        return printLineExecutor;
    }

    /**
     * override method used to print the logLines.
     * @param logLine the line
     */
    @Override
    public void execute(StringBuilder logLine) {
        System.out.print(logLine + "\n");
    }
}