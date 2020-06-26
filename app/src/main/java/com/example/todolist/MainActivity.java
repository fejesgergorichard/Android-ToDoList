package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemET;
    private Button btn;
    private LinearLayout mainLayout;
    public static ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.layout_main);

        itemET = findViewById(R.id.item_edit_text);
        btn = findViewById(R.id.add_btn);


        // read the items from a file to an arraylist
        list = (FileHelper.readData(this) == null) ? new ArrayList<String>() : FileHelper.readData(this);
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

                ListManager.addTaskToLayout(mainLayout, enteredText, this, list.size());
                list.add(enteredText);

                FileHelper.writeData(list, this);
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();

                itemET.setText("");
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
