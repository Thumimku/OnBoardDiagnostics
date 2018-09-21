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


import com.company.helper.XmlHelper;
import com.company.logtailer.Tailer;

import java.io.File;

/**
 * Typical Java Main class.
 */
public class Main {

    public static void main(String[] args) {
        System.out.print("................Loading  OnBoard Diagnostics Tool For WSO2 IS.............\n\n");
//        TailerListener logTailReader = new LogTailReader();
//        File file =  new File (new XmlHelper().getLogFilePath());
//        Thread tailer = new Tailer(file, logTailReader);
        System.out.print("......Loading Log File path Configuration data from wso2conf.xml .........\n\n");
        XmlHelper.parsingData();
        System.out.print(" wso2carbon.log File path :- "+XmlHelper.LogFilePath+"\n");
        System.out.print(" wso2carbon.pid File path :- "+XmlHelper.PIdFilePath+"\n");
        System.out.print(" timing.log File path :- "+XmlHelper.TimingLogPath+"\n\n");
        System.out.print("......Loading Regex Patterns from RegExPattern.xml........................\n\n");
        new Tailer(new File(XmlHelper.LogFilePath),new LogTailReader()).run();


    }
}
