package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

class ListManager {
    /** Adds a new task (linear layout) to a layout */
     static void addTaskToLayout(final LinearLayout layout, String text, final Context context, int elementID) {
        // Create horizontal linear layout for the task
        final LinearLayout newLine = new LinearLayout(context);
        newLine.setOrientation(LinearLayout.HORIZONTAL);
        newLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Create text
        TextView newText = new TextView(context);
        newText.setText(text);
        newText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 5));

        // Create checkbox
        CheckBox cb = new CheckBox(context);
        cb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        cb.setId(elementID);

        // Create onClickListener for the checkboxes
        cb.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
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
