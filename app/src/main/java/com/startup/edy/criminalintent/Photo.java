package com.startup.edy.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Justin on 7/19/2015.
 */
public class Photo {
    private static final String JSON_FILENAME = "filename";

    private String mFilename;

    //Creating photo representing existing file on disk
    public Photo(String filename){
        mFilename = filename;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, mFilename);
        return json;
    }

    public String getFilename(){
        return mFilename;
    }
}
