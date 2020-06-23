package com.example.todolist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListManager {
    private static int elementID = 0;

    /** Adds a new task (linear layout) to a layout */
    public static ArrayList<LinearLayout> addTaskToLayout(LinearLayout layout, String text, final Context context, ArrayList<LinearLayout> views) {
        // Create horizontal linear layout for the task
        LinearLayout newLine = new LinearLayout(context);
        newLine.setOrientation(LinearLayout.HORIZONTAL);
        newLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Create text
        TextView newText = new TextView(context);
        newText.setText(text);
        newText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 5));

        // Create checkbox
        CheckBox cb = new CheckBox(context);
        elementID += 1;
        cb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        cb.setId(elementID);
        // Create onClickListener for the checkboxes
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                String toastString = "Completed task no. " + v.getId() + " parent: " + ((View) v.getParent()).getId();

                if (checked) {
                    Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add the elements to the line
        newLine.addView(newText);
        newLine.addView(cb);
        // Add the elements to the layout
        layout.addView(newLine);
        // Add the new line to the views ArrayList
        views.add(newLine);

        return views;

    }

    public static void redrawList(LinearLayout layout, ArrayList<LinearLayout> list) {
        for (LinearLayout currentLayout : list) {
            layout.addView(currentLayout);
        }
    }


}
