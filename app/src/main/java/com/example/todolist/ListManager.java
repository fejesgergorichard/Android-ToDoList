package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;

class ListManager {
    /** Adds a new task (linear layout) to a layout */
     static void addTaskToLayout(final LinearLayout layout, String text, final Context context, int elementID) {
        // Create horizontal linear layout for the task
        final LinearLayout newLine = new LinearLayout(context);
        newLine.setOrientation(LinearLayout.HORIZONTAL);
        newLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Create text
        final TextView newText = new TextView(context);
        newText.setText(text);
        newText.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 7));

        // onClickListener for the texts
        newText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // set the text color to red if its black and vice versa
                newText.setTextColor((newText.getCurrentTextColor() == Color.RED) ? Color.BLACK : Color.RED);
                String text = (String)newText.getText();
                MainActivity.list.remove(text);
                MainActivity.list.add(0, text);
                redrawList(context, layout, MainActivity.list);
                TextView child = (TextView)((LinearLayout)layout.getChildAt(0)).getChildAt(0);
                child.setTextColor(Color.RED);
            }
        });

        // Create checkbox
        CheckBox cb = new CheckBox(context);
        cb.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        cb.setId(elementID);

        // Create onClickListener for the checkboxes
        cb.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(final View v) {
                // Delays the method for a specified amount of time to wait for the checkbox animation
                long delayMillis = 100;
                // New anonymus runnable class
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (((CheckBox) v).isChecked()) {
                            // Create a toast
                            String toastString = "Completed task no. " + v.getId();
                            Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();

                            // Remove the checked element from the list
                            MainActivity.list.remove(v.getId());
                            // Redraw the whole list and save it to a file
                            redrawList(context, layout, MainActivity.list);
                            FileHelper.writeData(MainActivity.list, context);
                        }
                    }
                }, delayMillis);
            }
        });

        // Add the elements to the line
        newLine.addView(newText);
        newLine.addView(cb);
        // Add the elements to the layout
        layout.addView(newLine);
    }

    /** Redraws the whole list */
    static void redrawList(Context context, LinearLayout layout, ArrayList<String> list) {
        layout.removeAllViews(); // clear the view
        if (list.size() == 0) return;
        // If the list is not empty, create a copy of it
        ArrayList<String> listCopy = (ArrayList<String>) list.clone();

        // Add a linear layout to each String, with the id of i
        for (int i = 0; i < listCopy.size(); i++) {
            String currentString = listCopy.get(i);
            addTaskToLayout(layout, currentString, context, i);
        }
    }


}
