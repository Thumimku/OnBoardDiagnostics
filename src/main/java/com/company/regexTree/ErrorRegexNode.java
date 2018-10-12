package com.company.regexTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

public class ErrorRegexNode {

    public Hashtable<ArrayList<String>, ErrorRegexNode> children;
    public JSONArray diagnosis;
    public String Description;
    public String Id;
    //public String reloadTime;
    public Hashtable<String, String> actionexecutorReloadTime;
    public JSONArray actionExecutorConfiguration;

    public ErrorRegexNode(String Id, String Description, JSONArray data) {

        this.Id = Id;
        this.diagnosis = data;
        this.Description = Description;
        this.children = new Hashtable<>();

    }

    public ErrorRegexNode(String Id, String Description) {

        this(Id, Description, null);

    }

    public void addchildren(ArrayList<String> regex, ErrorRegexNode node) {

        this.children.put(regex, node);
    }

    public JSONObject getconfiguration(String className) {

        for (Object AEObject : this.actionExecutorConfiguration) {
            JSONObject AEJSON = (JSONObject) AEObject;
            if (AEJSON.get("Executor").toString().compareTo(className) == 0) {

                return AEJSON;
            }
        }
        return null;
    }

}
