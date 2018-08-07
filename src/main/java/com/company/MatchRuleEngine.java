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
import com.company.helper.XmlHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *This class used to match the regex and the logLine.
 *
 * @author thumilan@wso2.com
 */

 public class MatchRuleEngine {

    private String generalErrorRegex; // general error regex
    private String generalInfoRegex; // general info regex
    private Pattern pattern;
    private Matcher matcher;
    private Boolean hasEngineApproved; // check whether the line is eligible or not

    private StringBuilder logLine; // LogLine object initiated

    private ActionExecutorFactory actionExecutorFactory; // actionExecutorFactory to create executor objects

    private ActionExecutor actionExecutor;

    public MatchRuleEngine() {
        hasEngineApproved = false;
        generalInfoRegex = new XmlHelper().getGeneralInfoRegEx();
        generalErrorRegex = new XmlHelper().getGeneralErrorRegEx();
        actionExecutorFactory = new ActionExecutorFactory();
        actionExecutor = actionExecutorFactory.getActionExecutor("zipfileexecutor");
    }

    /**
     * This method checks whether current testLine is match with Error regex.
     * @param testLine the line.
     * @return  boolean - true if matches.
     */
    private boolean checkInitialErrorMatch(String testLine) {

        pattern = Pattern.compile(generalErrorRegex);
        matcher = pattern.matcher(testLine);
        return matcher.find();
    }

    /**
     * This method checks whether current testLine is match with Info regex.
     * @param testLine the line.
     * @return  boolean - true if matches.
     *
     */
    private boolean checkInitialInfoMatch(String testLine) {

        pattern = Pattern.compile(generalInfoRegex);
        matcher = pattern.matcher(testLine);
        return matcher.find();
    }

    /**
     * This method checks whether the current line is error line or not.
     * Based on the hasEngineApproved boolean parameter this method appends line
     * @param testLine the current line.
     */
    public void validateTestline(String testLine) {

        if (hasEngineApproved) { // previous lines were approved by the engine.
            if (checkInitialInfoMatch(testLine)) {
                // Check whether current line is InfoLine.
                // If so switch the boolean parameter into false and execute collected logLine.
                hasEngineApproved = false;
                actionExecutor.execute(logLine);

            } else {
                //hasEngineApproved remain true
                //current line also error line append it to logLine.
                logLine.append(testLine);
                //what if command end in parse mode how to get Log line???
            }


        } else { // previous lines were not approved by engine.
            if (checkInitialErrorMatch(testLine)) {
                // Check whether current line is error line.
                // If so switch the boolean parameter into true and create new string builder.
                hasEngineApproved = true;
                logLine = new StringBuilder();
                logLine.append(testLine);



            }
        }
    }
}
