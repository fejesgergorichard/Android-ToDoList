package com.example.todolist;

import android.content.Context;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileHelper {
    public static final String FILENAME = "listinfo.dat";

    /** Writes the passed ArrayList<LinearLayout> object to a file */
    public static void writeData(ArrayList<String> items, Context context) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** Reads the ArrayList<LinearLayout> object from a file */
    public static ArrayList<String> readData (Context context) {
        ArrayList<String> items = null;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            items = (ArrayList<String>) ois.readObject();
            ois.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return items;
    }
}
