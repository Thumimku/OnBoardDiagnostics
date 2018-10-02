package com.company.actionexecutor.diagnosticCommand;
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

import com.company.actionexecutor.PostExecutor;
import com.company.actionexecutor.diagnosticCommand.ActionExecutor;
import com.company.actionexecutor.diagnosticCommand.DatabaseConnectionScanner;
import com.company.actionexecutor.diagnosticCommand.NetstatExecuter;
import com.company.actionexecutor.diagnosticCommand.OpenFileFinder;
import com.company.actionexecutor.diagnosticCommand.dumper.MemoryDumper;
import com.company.actionexecutor.diagnosticCommand.dumper.ServerProcess;
import com.company.actionexecutor.diagnosticCommand.dumper.ThreadDumper;

import java.lang.reflect.InvocationTargetException;

/**
 * This Factory class used to create various Executors using Factory Design pattern.
 * Ex:-
 * ActionExecutorFactory actionExecutorFactory = new ActionExecutorFactory();
 * PostExecutor actionExecutor = actionExecutorFactory.getActionExecutor("Printlineececutor");
 * Now you created printLineExecutor instance.
 *
 * @author thumilan@wso2.com
 */
public class ActionExecutorFactory {

    /**
     * This Method used to create Executor objects.
     *
     * @param executorType the executor type.
     * @return PostExecutor
     */
    public ActionExecutor getActionExecutor(String executorType) {

//        if (executorType == null) { // input string is null so method returs null object
//            return null;
//        } else if (executorType.equalsIgnoreCase("MemoryDump")) {
//            // check the input string and return printLineExecutor instance
//            return new MemoryDumper(new ServerProcess().getProcessId());
//        } else if (executorType.equalsIgnoreCase("lsof")) {
//            // check the input string and return zipLineExecutor instance
//            return new OpenFileFinder(new ServerProcess().getProcessId());
//        } else if (executorType.equalsIgnoreCase("databaseConnectionScanner")) {
//            // check the input string and return zipLineExecutor instance
//            return new DatabaseConnectionScanner();
//        } else if (executorType.equalsIgnoreCase("ThreadDump")) {
//            // check the input string and return zipLineExecutor instance
//            return new ThreadDumper(new ServerProcess().getProcessId());
//        } else if (executorType.equalsIgnoreCase("netstat")) {
//            // check the input string and return zipLineExecutor instance
//            return new NetstatExecuter();
//        } else {
//            // if input string doesn't match any above cases, invalid input return null.
//            return null;
//        }
        try {
            return (ActionExecutor) Class.forName(executorType).getConstructor(String.class).newInstance();
        } catch (NoSuchMethodException e) {
            System.out.print("Invalid executor configured as "+executorType+" . Unable to load the class");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
