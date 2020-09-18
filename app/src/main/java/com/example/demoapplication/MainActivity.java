package com.example.demoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.demoapplication";
    private final String EDIT_POST = "key1", EDIT_TITLE= "key2";

    ArrayList<Post> arrayListPost = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        final EditText editText = (EditText) findViewById(R.id.base_edit_post);
        editText.setText(mPreferences.getString(EDIT_POST,""));
        editText.setSelection(mPreferences.getString(EDIT_POST,"").length());

        final EditText editTitleText = (EditText) findViewById(R.id.base_edit_post_title);
        editTitleText.setText(mPreferences.getString(EDIT_TITLE,""));
        editTitleText.setSelection(mPreferences.getString(EDIT_TITLE,"").length());


        String str="";
        str = loadJSONFromAsset();
        try {
            JSONArray json = new JSONArray(str);

            for(int i=0;i<json.length();i++)
            {
                JSONObject jsonObject = json.getJSONObject(i);


                int idPost = jsonObject.getInt("id");
                String title = jsonObject.optString("title").toString();
                String body = jsonObject.optString("body").toString();


                arrayListPost.add(new Post(title,body,idPost));
            }



        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final PostAdapter postAdapter = new PostAdapter(this, arrayListPost);

        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(postAdapter);


        Button postButton = (Button) findViewById(R.id.post_button);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        //Log.v("MainActivity.class",editText.getText().toString());
                        if(editText.getText().toString()==null) {
                            Toast.makeText(MainActivity.this,"Write Post", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.v("MainActivity.class",editText.getText().toString());
                            arrayListPost.add(0, new Post(editTitleText.getText().toString(),editText.getText().toString(),5));
                            postAdapter.notifyDataSetChanged();
                            listView.smoothScrollToPosition(0);
                            editText.setText("");
                            editTitleText.setText("");
                        }

                    }
                });
            }
        });



    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("posts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onPause() {
        super.onPause();

        EditText editText = (EditText) findViewById(R.id.base_edit_post);
        String s = editText.getText().toString();

        EditText editTitleText = (EditText) findViewById(R.id.base_edit_post_title);
        String s_title = editTitleText.getText().toString();


        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(EDIT_POST,s);
        preferencesEditor.putString(EDIT_TITLE,s_title);
        preferencesEditor.apply();
    }



}