package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private EditText itemET;
    private Button btn;
    private ListView itemsList;

    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemET = findViewById(R.id.item_edit_text);
        btn = findViewById(R.id.add_btn);
        itemsList = findViewById(R.id.items_list);

        // read the items from a file to an arraylist
        items = (FileHelper.readData(this) == null) ? new ArrayList<String>() : FileHelper.readData(this);
        // put the arraylist in an arrayadapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        // put the arrayadapter's content into the itemsList ListView
        itemsList.setAdapter(adapter);

        // set an action listener for the 'ADD' button
        btn.setOnClickListener(this);
        // set an action listener for the ListView onItemClick
        itemsList.setOnItemClickListener(this);
        itemsList.setOnItemLongClickListener(this);
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
                adapter.add(enteredText);
                itemET.setText("");
                FileHelper.writeData(items, this);
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    /** Action listener for the ListView object's item click */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        adapter.notifyDataSetChanged();
        FileHelper.writeData(items, this);
        Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String text = "Long click on element: " + items.get(position);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return false;
    }
}
