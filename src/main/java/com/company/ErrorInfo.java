package com.company;




import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ErrorInfo {

    private static List<String> errorSyndrome;

    private static Hashtable<String,JSONObject> errorSyndromeMap;

    public ErrorInfo(JSONArray errorInfoJsonArray) {
        errorSyndrome = new ArrayList<>();
        errorSyndromeMap = new Hashtable<>();
        for (Object object:errorInfoJsonArray){
            JSONObject errorInfoJson = (JSONObject) object;
            errorSyndrome.add(errorInfoJson.get("regex").toString());
            errorSyndromeMap.put(errorInfoJson.get("regex").toString(),errorInfoJson);
        }
    }

    public static List<String> getErrorSyndrome() {

        return errorSyndrome;
    }

    public static Object getErrorDiagnosis(String syndrome){
        if (errorSyndromeMap.containsKey(syndrome)){
            return errorSyndromeMap.get(syndrome).get("diagnosis");
        } else {
            return null;
        }
    }
    public static String getErrortype(String syndrome){
        if (errorSyndromeMap.containsKey(syndrome)){
            return errorSyndromeMap.get(syndrome).get("error").toString();
        } else {
            return null;
        }
    }
    public static String getErrorDescription(String syndrome){
        if (errorSyndromeMap.containsKey(syndrome)){
            return errorSyndromeMap.get(syndrome).get("description").toString();
        } else {
            return null;
        }
    }
}
