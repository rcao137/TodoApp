package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	private EditText etItem;
	private int pos;
	private String edText;
	private int code;

	// Create EditItem Activity
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		edText = getIntent().getStringExtra("itemname");
		pos = getIntent().getIntExtra("pos", 0);
		// code = getIntent().getIntExtra("code", 0);
		etItem = (EditText) findViewById(R.id.edItemFld);
		etItem.setText(edText);

	}

	// Callback for Save button
	public void onSubmit(View v) {
		// closes the activity and returns to main screen
		// Prepare data intent
		Intent data = new Intent();
		// Pass relevant data back as a result
		data.putExtra("itemname", etItem.getText().toString());
		data.putExtra("pos", pos);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for
									// response
		finish(); // closes the activity, pass data to parent
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
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
