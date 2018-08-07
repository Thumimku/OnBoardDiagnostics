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
 * This Factory class used to create various Executors using Factory Design pattern.
 * Ex:-
 * ActionExecutorFactory actionExecutorFactory = new ActionExecutorFactory();
 * ActionExecutor actionExecutor = actionExecutorFactory.getActionExecutor("Printlineececutor");
 * Now you created printLineExecutor instance.
 * 
 * @see ActionExecutor
 * @author thumilan@wso2.com
 */
public class ActionExecutorFactory {

    /**
     * This Method used to create Executor objects.
     * 
     * @param executorType the executor type.
     * @return ActionExecutor
     */
    public ActionExecutor getActionExecutor(String executorType) {

        if (executorType == null) { // input string is null so method returs null object
            return null;
        } else if (executorType.equalsIgnoreCase("PRINTLINEEXECUTOR")) {
            // check the input string and return printLineExecutor instance
            return PrintLineExecutor.getInstance();
        } else if (executorType.equalsIgnoreCase("zipfileexecutor")) {
            // check the input string and return zipLineExecutor instance
            return ZipFileExecutor.getInstance();
        } else {
            // if input string doesn't match any above cases, invalid input return null.
            return null;
        }

    }

}
