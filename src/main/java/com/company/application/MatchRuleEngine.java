package com.company.application;

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

import com.company.application.Interpreter;
import com.company.helper.XmlHelper;
import com.company.logtailer.Tailer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class used to match the regex and the logLine.
 *
 * @author thumilan@wso2.com
 */

public class MatchRuleEngine {

    private String generalStartRegex; // general error regex

    private String generalEndRegex; // general info regex

    private Interpreter interpreter; // initiate Interpreter

    private Pattern pattern;

    private Matcher matcher;

    private Boolean hasEngineApproved; // check whether the line is eligible or not

    private Boolean enableTailerCheck; // check whether current error is already occurred or not

    private StringBuilder logLine; // LogLine object initiated

    public MatchRuleEngine() {

        this.hasEngineApproved = false;
        this.generalEndRegex = XmlHelper.generalInfoRegex;
        this.generalStartRegex = XmlHelper.generalErrorRegex;
        this.interpreter = new Interpreter();
        this.enableTailerCheck = true;
    }

    /**
     * This method checks whether current testLine is match with Error regex.
     *
     * @param testLine the line.
     * @return boolean - true if matches.
     */
    private boolean checkInitialErrorMatch(String testLine) {

        pattern = Pattern.compile(generalStartRegex);
        matcher = pattern.matcher(testLine);
        return matcher.find();
    }

    /**
     * This method checks whether current testLine is match with Info regex.
     *
     * @param testLine the line.
     * @return boolean - true if matches.
     */
    private boolean checkInitialInfoMatch(String testLine) {

        pattern = Pattern.compile(generalEndRegex);
        matcher = pattern.matcher(testLine);
        return matcher.find();
    }

    /**
     * This method checks whether the current line is error line or not.
     * Based on the hasEngineApproved boolean parameter this method appends line
     *
     * @param testLine the current line.
     */
    public void validateTestline(String testLine,Tailer tailer) {

        if (hasEngineApproved) { // previous lines were approved by the engine.

            if (checkInitialErrorMatch(testLine)) {

                enableTailerCheck = false;
                hasEngineApproved = false;
                if (interpreter != null) {
                    interpreter.interpret(logLine);
                } else {
                    interpreter = new Interpreter();
                    interpreter.interpret(logLine);
                }
            } else if (checkInitialInfoMatch(testLine)) {
                // Check whether current line is InfoLine.
                // If so switch the boolean parameter into false and execute collected logLine.
                hasEngineApproved = false;
                if (interpreter != null) {
                    interpreter.interpret(logLine);
                } else {
                    interpreter = new Interpreter();
                    interpreter.interpret(logLine);
                }

            } else if (enableTailerCheck) {
                //System.out.print(testLine);
                if (tailer.isEnd(testLine)) {
                    hasEngineApproved = false;
                    logLine.append(testLine);
                    if (interpreter != null) {
                        interpreter.interpret(logLine);
                    } else {
                        interpreter = new Interpreter();
                        interpreter.interpret(logLine);
                    }

                }
            }

            //hasEngineApproved remain true
            //current line also error line append it to logLine.
            logLine.append(testLine);
            //what if command end in parse mode how to get Log line???

        } else { // previous lines were not approved by engine.
            if (checkInitialErrorMatch(testLine)) {
                // Check whether current line is error line.
                // If so switch the boolean parameter into true and create new string builder.
                hasEngineApproved = true;
                logLine = new StringBuilder();
                logLine.append(testLine);
                enableTailerCheck = true;

            }
        }
    }

}
