package com.company.regexTree;

import com.company.regexTree.ErrorRegexNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class ErrorRegexTree {

    public static  ErrorRegexNode root;

    private static final String description = "Description";
    private static final String children = "Children";
    private static final String diagnosis = "Diagnosis";
    private static final String regex = "Regex";
    private static final String id = "Id";
  //  private static final String reloadTime = "ReloadTime";

//    public static void createroot(JSONObject rootJSON) {
//
//        root = new ErrorRegexNode(rootJSON.get(id).toString(), rootJSON.get(description).toString());
//        JSONArray childrenJSONArray = (JSONArray) rootJSON.get(children);
//        for (Object childObject : childrenJSONArray) {
//            JSONObject childJSON = (JSONObject) childObject;
//            ErrorRegexNode node = new ErrorRegexNode(childJSON.get(id).toString(), childJSON.get(description).toString());
//            root.addchildren((ArrayList<String>) childJSON.get(regex), node);
//            if (childJSON.containsKey(diagnosis)) {
//                node.diagnosis = (JSONArray) childJSON.get(diagnosis);
//            }
//        }
//    }
//
//    public static ErrorRegexNode addnode(JSONObject nodeJSON) {
//
//        if (nodeJSON.containsKey(children)) {
//            JSONArray childrenJSONArray = (JSONArray) nodeJSON.get(children);
//            for (Object childObject : childrenJSONArray) {
//                JSONObject childJSON = (JSONObject) childObject;
//                ErrorRegexNode childnode = addnode(childJSON);
//                return childnode;
//            }
//        } else {
//            ErrorRegexNode node = new ErrorRegexNode(nodeJSON.get(id).toString(), nodeJSON.get(description).toString());
//            return node;
//        }
//        return null;
//    }

    public static  ErrorRegexNode expandTree(JSONObject nodeJSON) {

        ErrorRegexNode node = new ErrorRegexNode(nodeJSON.get(id).toString(), nodeJSON.get(description).toString()); //create current parent node
        if (nodeJSON.containsKey(diagnosis)) {
            node.diagnosis = (JSONArray) nodeJSON.get(diagnosis);
            //node.reloadTime= (String)nodeJSON.get(reloadTime);
        }
        if (nodeJSON.containsKey(children)) { //check for children
            JSONArray childrenJSONArray = (JSONArray) nodeJSON.get(children);
            for (Object childObject : childrenJSONArray) {
                JSONObject childJSON = (JSONObject) childObject;
                ErrorRegexNode childnode = expandTree(childJSON);
                node.addchildren((ArrayList<String>) childJSON.get(regex), childnode);

            }
        } else {
            ErrorRegexNode childnode = new ErrorRegexNode(nodeJSON.get(id).toString(), nodeJSON.get(description).toString());
            if (nodeJSON.containsKey(diagnosis)) {
                childnode.diagnosis = (JSONArray) nodeJSON.get(diagnosis);
                //childnode.reloadTime= (String)nodeJSON.get(reloadTime);
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

    public static ErrorRegexNode findDiagnosis(StringBuilder logLine) {

        // Change the logLine into String data type to use contains() method.
        String logString = logLine.toString();

        // Set current Node to root
        ErrorRegexNode currentNode = root;
        System.out.print(currentNode.Id + "\n");

        //Initialise finded as false
        Boolean finded = false;

        //Loop until find suitable node for the error log Line
        while (!finded) {

            // if current node is leaf then return it
            if ((currentNode.children.isEmpty())) {
//                System.out.print(currentNode.Id+"\n");
                return currentNode;
            } else {
                // create a temprory node for
                ErrorRegexNode tempnode = null;
                Set<ArrayList<String>> keys = currentNode.children.keySet();
                for (ArrayList<String> key : keys) {

                    tempnode = digDeep(logString, key, currentNode);
                    if (tempnode != null) {
                        currentNode = tempnode;
                        System.out.print(currentNode.Id + "\n");
                        break;
                    }
                }
                if (tempnode == null) {

                    if (currentNode.diagnosis != null) {
                        return currentNode;

                    } else {
                        System.out.print("Error found but No diagnosis in the node :-" + currentNode.Id + "\n");
                        return currentNode;
                    }


                }
            }

        }
        return null;

    }

    private  static ErrorRegexNode digDeep(String logString, ArrayList<String> key, ErrorRegexNode currentNode) {

        for (String regex : key) {
            if (logString.contains(regex)) {

                return currentNode.children.get(key);

            }

        }
        return null;
    }
    private ErrorRegexNode getRoot(){
        return this.root;
    }
}
