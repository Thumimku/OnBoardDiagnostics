package com.company;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Hashtable;

public class ErrorRegexNode {

    public Hashtable<ArrayList<String>, ErrorRegexNode> children;
    public JSONArray diagnosis;
    public String Description;

    public ErrorRegexNode(String Description, JSONArray data) {

        this.diagnosis = data;
        this.Description = Description;

    }

    public ErrorRegexNode(String Description) {

        this.diagnosis = null;
        this.Description = Description;
        this.children = new Hashtable<>();

    }

    public void addchildren(ArrayList<String> regex, ErrorRegexNode node) {

        this.children.put(regex, node);
    }

}
