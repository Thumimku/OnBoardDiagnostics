package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class ErrorRegexTree {

    public static ErrorRegexNode root;

    static final String description = "Description";
    static final String children = "Children";
    static final String diagnosis = "Diagnosis";
    static final String regex = "Regex";

    public static void createroot(JSONObject rootJSON) {

        root = new ErrorRegexNode(rootJSON.get(description).toString());
        JSONArray childrenJSONArray = (JSONArray) rootJSON.get(children);
        for (Object childObject : childrenJSONArray) {
            JSONObject childJSON = (JSONObject) childObject;
            ErrorRegexNode node = new ErrorRegexNode(childJSON.get(description).toString());
            root.addchildren((ArrayList<String>) childJSON.get(regex), node);
            if (childJSON.containsKey(diagnosis)) {
                node.diagnosis = (JSONArray) childJSON.get(diagnosis);
            }
        }
    }

    public static ErrorRegexNode addnode(JSONObject nodeJSON) {

        if (nodeJSON.containsKey(children)) {
            JSONArray childrenJSONArray = (JSONArray) nodeJSON.get(children);
            for (Object childObject : childrenJSONArray) {
                JSONObject childJSON = (JSONObject) childObject;
                ErrorRegexNode childnode = addnode(childJSON);
                return childnode;
            }
        } else {
            ErrorRegexNode node = new ErrorRegexNode(nodeJSON.get(description).toString());
            return node;
        }
        return null;
    }

    public static ErrorRegexNode expandTree(JSONObject nodeJSON) {

        ErrorRegexNode node = new ErrorRegexNode(nodeJSON.get(description).toString()); //create current parent node
        if (nodeJSON.containsKey(diagnosis)) {
            node.diagnosis = (JSONArray) nodeJSON.get(diagnosis);
        }
        if (nodeJSON.containsKey(children)) { //check for children
            JSONArray childrenJSONArray = (JSONArray) nodeJSON.get(children);
            for (Object childObject : childrenJSONArray) {
                JSONObject childJSON = (JSONObject) childObject;
                ErrorRegexNode childnode = expandTree(childJSON);
                node.addchildren((ArrayList<String>) childJSON.get(regex), childnode);

            }
        } else {
            ErrorRegexNode childnode = new ErrorRegexNode(nodeJSON.get(description).toString());
            if (nodeJSON.containsKey(diagnosis)) {
                childnode.diagnosis = (JSONArray) nodeJSON.get(diagnosis);
            }
            return node;
        }
        return node;
    }

    public static void checkTree(ErrorRegexNode root) {

        Set<ArrayList<String>> keys = root.children.keySet();
        System.out.print(root.children.toString() + "\n");
        for (ArrayList<String> key : keys) {
            checkTree(root.children.get(key));
        }
    }

    public static void findDiagnosis(StringBuilder logLine) {

        String logString = logLine.toString();
        ErrorRegexNode currentNode = root;
        Boolean finded = false;
        while (!finded) {

            if ((currentNode.children.isEmpty())) {
                System.out.print(currentNode.diagnosis.toString());
                finded = true;
            } else {
                System.out.print(currentNode.children);
                Set<ArrayList<String>> keys = currentNode.children.keySet();
                for (ArrayList<String> key : keys) {

                    ErrorRegexNode tempnode = digdeep(logString, key, currentNode);
                    if (tempnode != null) {
                        currentNode = tempnode;
                        System.out.print(currentNode.Description);
                        break;
                    }
                }
            }

        }

    }

    private static ErrorRegexNode digdeep(String logString, ArrayList<String> key, ErrorRegexNode currentNode) {

        for (String regex : key) {
            if (logString.contains(regex)) {

                return currentNode.children.get(key);

            }

        }
        return null;
    }
}
