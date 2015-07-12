package com.startup.edy.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Justin on 7/5/2015.
 */
public class CriminalIntentJSONSerializer {

    private Context mContext;
    private String mFilename;

    public CriminalIntentJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    /*public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try {
            FileInputStream in = null;
            // Open and read the file into a StringBuilder.
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                // Load from sd card if available.
                in = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/CriminalIntent/" + mFilename);
            else
                in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
                // Line breaks are omitted and irrelevant
                jsonString.append(line);

            // Parse the JSON using JSONTokener.
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            // Build the array of crimes from JSONObjects
            for (int i = 0; i <array.length(); ++i)
                crimes.add(new Crime(array.getJSONObject(i)));
        } catch (FileNotFoundException e) {
            // Starting without a file, so ignore.
        } finally {
            if (reader != null)
                reader.close();
        }
        return crimes;
    }*/

    /*public void saveCrime(ArrayList<Crime> crimes) throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Crime c: crimes)
            array.put(c.toJSON());

        // Write the file to disk
        OutputStreamWriter writer = null;
        try {
            FileOutputStream out = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                // Save to sd card if available.
                out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/CriminalIntent/" + mFilename);
            else
                out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }*/

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;

        // see if the sd card is mounted and also check to see if the crimes file exists
        // find out if the SD card is mounted
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        // set variable pointing to file so we can check if it exists
        File extCrimeFile = new File(mContext.getExternalFilesDir(null), mFilename);

        if (isSDPresent && extCrimeFile.exists()) {
            //Log.e(TAG, "The loadCrimes method found the SD Card mounted and found that the crimes file exists");
            try {
                // Open and read the file into a StringBuilder
                FileInputStream extFileInputStream = new FileInputStream(extCrimeFile);
                reader = new BufferedReader(new InputStreamReader(extFileInputStream));
                StringBuilder jsonString = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    // Line breaks are omitted and irrelevant
                    jsonString.append(line);
                }// end while loop
                // Parse the JSON using JSONTokener
                JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
                // Build the array of crimes from JSONObjects
                for (int i = 0; i < array.length(); i++) {
                    crimes.add(new Crime(array.getJSONObject(i)));
                }
            } catch (FileNotFoundException e) {
                // Ignore this one ; it happens when starting fresh
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }// end try/catch
            return crimes;

        } else {

            try {
                // Open and read the file into a StringBuilder
                InputStream in = mContext.openFileInput(mFilename);
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder jsonString = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    // Line breaks are omitted and irrelevant
                    jsonString.append(line);
                }// end while loop
                // Parse the JSON using JSONTokener
                JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
                // Build the array of crimes from JSONObjects
                for (int i = 0; i < array.length(); i++) {
                    crimes.add(new Crime(array.getJSONObject(i)));
                }
            } catch (FileNotFoundException e) {
                // Ignore this one ; it happens when starting fresh
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }// end try/catch
            return crimes;

        }
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        // build and array in JSON
        JSONArray array = new JSONArray();


        // find out if the SD card is mounted
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if (isSDPresent) {
            // yes SD-card is present
            //Log.e(TAG, "The saveCrimes method found that the SD card is mounted");

            // get the external files dir
            File extDataDir = new File(mContext.getExternalFilesDir(null), mFilename);
            // log the dir to be written to
            //Log.e(TAG, "The external files dir is: " + extDataDir.toString());

            for (Crime c : crimes) {
                array.put(c.toJSON());
                // write file to disk
                Writer writer = null;
                try {
                    File extCrimeFile = new File(extDataDir.toString());
                    FileOutputStream extFOS = new FileOutputStream(extCrimeFile);
                    writer = new OutputStreamWriter(extFOS);
                    writer.write(array.toString());
                } finally {
                    if (writer != null) {
                        writer.close();
                    }// end if statement
                }// end try/catch
            }// end for statement

        } else {
            // The SD card was not mounted
            //Log.e(TAG, "The SD card is not mounted");

            for (Crime c : crimes) {
                array.put(c.toJSON());
                // write file to disk
                Writer writer = null;
                try {
                    OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
                    writer = new OutputStreamWriter(out);
                    writer.write(array.toString());
                } finally {
                    if (writer != null) {
                        writer.close();
                    }// end if statement
                }// end try/catch
            }// end for statement
        }
    }
}
