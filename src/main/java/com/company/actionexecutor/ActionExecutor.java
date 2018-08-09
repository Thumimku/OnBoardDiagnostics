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
 * This Interface is used to abstract the execute method for various executors.
 * Executor classes can use this interface by implementation.
 *
 * Public class PrintLineExecutor implements ActionExecutor{
 *     public void execute(StringBuilder logLine) {
 *         System.out.print(logLine + "\n");
 *     }
 * }
 *
 * Factory design pattern is used to create executors referenced by ActionExecutor interface and run execute
 * @see ActionExecutorFactory
 * @author thumilan@wso2.com
 */

 public interface ActionExecutor {
    /**
     * This method called by executor to do the execution.
     * @param logLine the line
     */
    void execute(StringBuilder logLine, String folderpath);
}
