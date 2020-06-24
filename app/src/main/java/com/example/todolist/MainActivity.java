package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static HashMap<Integer, String> list;

    private EditText itemET;
    private Button btn;
    private LinearLayout mainLayout;

    private ArrayAdapter<String> adapter;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.layout_main);

        itemET = findViewById(R.id.item_edit_text);
        btn = findViewById(R.id.add_btn);

        // read the items from a file to an arraylist
        list = (FileHelper.readData(this) == null) ? new HashMap<Integer, String>() : FileHelper.readData(this);
        System.out.println(list);
        ListManager.redrawList(this, mainLayout, list);

        // set an action listener for the 'ADD' button
        btn.setOnClickListener(this);
    }

    /** Action listener for the 'ADD' button */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                String enteredText = itemET.getText().toString();
                if (enteredText.equals("")) {
                    Toast.makeText(this, "Enter text to add.", Toast.LENGTH_SHORT).show();
                    break;
                }

                ListManager.addTaskToLayout(mainLayout, enteredText, this);
                id++;
                list.put(id, enteredText);

                FileHelper.writeData(list, this);
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    /** Action listener for the ListView object's item click */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileHelper.writeData(list, this);
        Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
    }
}
