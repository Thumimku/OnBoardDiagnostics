package com.company.application;

import com.company.logtailer.Tailer;
import com.company.regexTree.ErrorRegexTree;
import org.json.simple.JSONObject;

import java.io.File;

public class LogFile {

    private Tailer tailer;
    private ErrorRegexTree errorRegexTree;
    private JSONObject initialConfig;
    private File regexTreeJsonFile;

    public LogFile(JSONObject initialConfig) {

        this(initialConfig,null);
    }

    public LogFile(JSONObject initialConfig, File regexTreeJsonFile) {

        this.initialConfig = initialConfig;
        this.regexTreeJsonFile = regexTreeJsonFile;
    }

    public Tailer getTailer() {

        return tailer;
    }

    public void setTailer(Tailer tailer) {

        this.tailer = tailer;
    }

    public ErrorRegexTree getErrorRegexTree() {

        return errorRegexTree;
    }

    public void setErrorRegexTree(ErrorRegexTree errorRegexTree) {

        this.errorRegexTree = errorRegexTree;
    }

    public JSONObject getInitialConfig() {

        return initialConfig;
    }

    public void setInitialConfig(JSONObject initialConfig) {

        this.initialConfig = initialConfig;
    }

    public File getRegexTreeJsonFile() {

        return regexTreeJsonFile;
    }

    public void setRegexTreeJsonFile(File regexTreeJsonFile) {

        this.regexTreeJsonFile = regexTreeJsonFile;
    }
}
