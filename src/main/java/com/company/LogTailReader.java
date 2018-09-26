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

import com.company.logtailer.TailerListenerAdapter;

/**
 * This class is used to handle the line which pass from Tailer class.
 *
 * @author thumilan@wso2.com
 * @see TailerListenerAdapter
 */
public class LogTailReader extends TailerListenerAdapter {

    //Initiate MatchRule Engine
    private final MatchRuleEngine matchRuleEngine;

    //constructor - set initial Lof file length 0 and set Log file path;
    public LogTailReader() {

        matchRuleEngine = new MatchRuleEngine();

    }

    /**
     * This override method used to implement the logic over the read line.
     * Here new line passed matchRuleEngine.
     *
     * @param line the line.
     */
    @Override
    public void handle(String line) {

        matchRuleEngine.validateTestline(line);
    }

    /**
     * Whenever a exception occurs Tailer class pass that certain exception to this method.
     * Here we can handle that exception.
     *
     * @param ex the exception.
     */
    @Override
    public void error(Exception ex) {

        System.out.print(ex.getMessage());
    }
}
