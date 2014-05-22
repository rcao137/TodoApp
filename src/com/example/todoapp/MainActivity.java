package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	private final int REQUEST_CODE = 20;
	private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lvItems = (ListView) findViewById(R.id.lvItems);
        populateArrayItems();
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setListViewListener();
	}
	
	private void populateArrayItems() {
        readItem();
        
    }
	
	public void addTodoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.edItem);
		todoAdapter.add(etNewItem.getText().toString());
		saveItem();
		etNewItem.setText("");
	}

	public void setListViewListener(){
		lvItems.setOnItemLongClickListener( new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				todoItems.remove(pos);
				saveItem();
				todoAdapter.notifyDataSetChanged();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
		        String s= todoItems.get(pos);
				launchComposeView(s, pos);
				return;
			}
		});
	}
	
	public void launchComposeView(String name, int pos) {
		  // first parameter is the context, second is the class of the activity to launch
		  Intent i = new Intent(MainActivity.this, EditItemActivity.class);
		  // put "extras" into the bundle for access in the second activity
		  i.putExtra("itemname", name); 
		  i.putExtra("pos", pos); 
		  i.putExtra("code", 400);
		  // brings up the second activity
		  startActivityForResult(i, REQUEST_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String name = data.getExtras().getString("itemname");
	     int pos = data.getIntExtra("pos", 0);
	     todoItems.set(pos, name);
	     saveItem();
		 todoAdapter.notifyDataSetChanged();

	  }
	} 
	private void readItem() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveItem(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
