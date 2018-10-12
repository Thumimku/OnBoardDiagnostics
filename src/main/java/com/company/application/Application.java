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

import com.company.actionexecutor.diagnosticCommand.ActionExecutor;
import com.company.actionexecutor.diagnosticCommand.ActionExecutorFactory;
import com.company.actionexecutor.diagnosticCommand.MemoryDumper;
import com.company.helper.XmlHelper;
import com.company.logtailer.Tailer;
import com.company.regexTree.ErrorRegexTree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Typical Java Application class.
 */
public class Application {

    public static void main(String[] args) throws IOException {

        System.out.print("................Loading  OnBoard Diagnostics Tool For WSO2 IS.............\n\n");
        System.out.print("......Loading Log File path Configuration data from wso2conf.xml .........\n\n");
        XmlHelper.parsingData();
        System.out.print(" wso2carbon.log File path :- " + new XmlHelper().getLogFilePath() + "\n");
        System.out.print(" wso2carbon.pid File path :- " + XmlHelper.PIdFilePath + "\n");
        System.out.print("......Loading Regex Patterns from RegExPattern.xml........................\n\n");

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject =(JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/resources/RegexTree.json"));


            ErrorRegexTree.root =ErrorRegexTree.expandTree(jsonObject);
            ErrorRegexTree.root.actionexecutorReloadTime = new Hashtable<>();
            JSONArray actionExecutorConfig = (JSONArray) jsonObject.get("ActionExecutorConfiguration");
            ErrorRegexTree.root.actionExecutorConfiguration = actionExecutorConfig;
            for (Object AEObject: actionExecutorConfig){
                JSONObject AEJSON = (JSONObject) AEObject;
                ErrorRegexTree.root.actionexecutorReloadTime.put(AEJSON.get("Executor").toString(),AEJSON.get("ReloadTime").toString());
            }
            System.out.print(ErrorRegexTree.root.actionexecutorReloadTime.toString());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } catch (ParseException e) {
            System.out.print(e.getMessage());
        }
//
//
//
//        //Initiate tailer class to tail the file
//
      Tailer carbonLogTailor= new  Tailer(new File(new XmlHelper().getLogFilePath()), new LogReader(),1000,true);
//      Thread correlationLogTailor= new Tailer(new File(XmlHelper.CorrelationLogPath),new LogReader(),1000,true);
      carbonLogTailor.start();
//      correlationLogTailor.start();




    }
}
