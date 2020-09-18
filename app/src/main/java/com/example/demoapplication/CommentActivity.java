package com.example.demoapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CommentActivity extends AppCompatActivity {
    int postId,flag=0;

    private SharedPreferences mCommentShPref;
    private String commentShaPreFile = "com.example.demoapplication";
    private final String EDIT_COMMENT = "key5", EDIT_TITLE = "key4" ;
    private final String POST_ID = "key6";

    ArrayList<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mCommentShPref = getSharedPreferences(commentShaPreFile, MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();
        if(extras!= null)
        {
            postId = extras.getInt("postId");
        }

        final EditText editText = (EditText) findViewById(R.id.base_edit_comment);
        final EditText editTitleText = (EditText) findViewById(R.id.base_edit_comment_title);
        if(mCommentShPref.getInt(POST_ID,0)==postId)
        {
            editText.setText(mCommentShPref.getString(EDIT_COMMENT,""));
            editText.setSelection(mCommentShPref.getString(EDIT_COMMENT,"").length());

//            Set <String> hs = new HashSet<>();
//            mCommentShPref.getStringSet(Integer.toString(postId), hs);
//
//
//            for (Iterator<String> it = hs.iterator(); it.hasNext(); ) {
//                String f = it.next();
//                editText.setText(f);
//
//            }



            editTitleText.setText(mCommentShPref.getString(EDIT_TITLE,""));
            editTitleText.setSelection(mCommentShPref.getString(EDIT_TITLE,"").length());

        }



        String str="";
        str=loadJSONFromAsset();
        try {
            JSONArray json = new JSONArray(str);

            for(int i=0;i<json.length();i++)
            {
                JSONObject jsonObject = json.getJSONObject(i);
                int idPost = jsonObject.getInt("postId");

                if(idPost==postId)
                {
                    flag=1;
                    String name = jsonObject.optString("name").toString();
                    String body = jsonObject.optString("body").toString();


                    comments.add(new Comment(name,body,idPost));
                }


            }



        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        if(flag==1)
        {
            final CommentAdapter commentAdapter = new CommentAdapter(this, comments);

            final ListView listView = (ListView) findViewById(R.id.list_comment);
           listView.setAdapter(commentAdapter);

            Button postButton = (Button) findViewById(R.id.comment_button);

            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listView.post(new Runnable() {
                        @Override
                        public void run() {
                            //Log.v("MainActivity.class",editText.getText().toString());
                            if(editText.getText().toString()==null) {
                                Toast.makeText(CommentActivity.this,"Write Comment", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Log.v("MainActivity.class",editText.getText().toString());
                                comments.add(0, new Comment(editTitleText.getText().toString(),editText.getText().toString(),postId));
                                commentAdapter.notifyDataSetChanged();
                                listView.smoothScrollToPosition(0);
                                editText.setText("");
                                editTitleText.setText("");
                            }

                        }
                    });
                }
            });




        }








    }



    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("comments.json");
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

        EditText editText = (EditText) findViewById(R.id.base_edit_comment);
        EditText editTitleText = (EditText) findViewById(R.id.base_edit_comment_title);

        String s = editText.getText().toString();
        String s_title = editTitleText.getText().toString();

       Set<String> hs = new HashSet<>();
       hs.add(s_title);
       hs.add(s);


        SharedPreferences.Editor commentPrefEditor = mCommentShPref.edit();
        commentPrefEditor.putString(EDIT_COMMENT,s);
        commentPrefEditor.putString(EDIT_TITLE,s_title);
        commentPrefEditor.putInt(POST_ID,postId);
        commentPrefEditor.putStringSet(Integer.toString(postId), hs);


        commentPrefEditor.apply();

    }
}