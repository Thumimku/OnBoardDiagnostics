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
import com.company.actionexecutor.ActionExecutorConnector;
import com.company.helper.XmlHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class matches log line with the regex.
 *
 * @author thumilan@wso2.com
 */

 public class MatchRuleEngine {
     //this class is used match the test line with the regex
    private String generalErrorRegex; // general error regex
    private String generalInfoRegex; //general info regex
    private Pattern pattern;
    private Matcher matcher;
    private Boolean hasEngineApproved; // check whether the line is eligible or not

    private StringBuilder logLine; // stringBuilder used to build logLine

    private ActionExecutorConnector actionExecutorConnector; // connector used to connect the action executor

    public MatchRuleEngine() {
        hasEngineApproved = false;
        generalInfoRegex = new XmlHelper().getGeneralInfoRegEx();
        generalErrorRegex = new XmlHelper().getGeneralErrorRegEx();
        actionExecutorConnector = new ActionExecutorConnector();

    }
    private boolean checkInitialErrorMatch(String testline) {
        // check the testline with error regex
        pattern = Pattern.compile(generalErrorRegex);
        matcher = pattern.matcher(testline);
        return matcher.find();
    }
    private boolean checkInitialInfoMatch(String testline) {
        // check the testline with info regex
        pattern = Pattern.compile(generalInfoRegex);
        matcher = pattern.matcher(testline);
        return matcher.find();
    }
    public void validateTestline(String testline) {
        //this method is used to validate the test line
        if (hasEngineApproved) {
            if (checkInitialInfoMatch(testline)) {
                hasEngineApproved = false;
                System.out.print(logLine);
                actionExecutorConnector.zipfileexecution(logLine);

            } else {
                //hasEngineApproved remain true
                
                logLine = logLine.append(testline);

                //what if command end in parse mode how to get Log line???
            }


        } else {
            if (checkInitialErrorMatch(testline)) {
                hasEngineApproved = true;
                logLine = new StringBuilder();
                logLine = logLine.append(testline);


            }
        }
    }
}
