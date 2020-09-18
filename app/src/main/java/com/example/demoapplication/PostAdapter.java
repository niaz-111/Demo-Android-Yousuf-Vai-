package com.example.demoapplication;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class PostAdapter extends ArrayAdapter<Post> {
    public PostAdapter(@NonNull Context context,  @NonNull ArrayList<Post> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View listItemView = convertView;
        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_post,parent,false);
        }

        final Post currentPost = getItem(position);

        TextView titleText = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleText.setText(currentPost.getmTitle());

        final TextView postText = (TextView) listItemView.findViewById(R.id.post_text_view);
        postText.setText(currentPost.getmPost());

        final EditText editText = (EditText) listItemView.findViewById(R.id.edit_text);
        editText.setText(postText.getText());
        editText.setSelection(editText.getText().length());

        final LinearLayout removable = (LinearLayout) listItemView.findViewById(R.id.removable_layout);
        removable.setVisibility(View.GONE);

        if(postText.getVisibility()==View.GONE && removable.getVisibility()==View.GONE)
        {
             removable.setVisibility(View.VISIBLE);
        }


        Button commentbutton = (Button) listItemView.findViewById(R.id.see_comment);
        Button editButton = (Button)  listItemView.findViewById(R.id.edit_post);
        Button saveButton = (Button) listItemView.findViewById(R.id.save_button);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  postText.setVisibility(View.GONE);
                removable.setVisibility(View.VISIBLE);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentPost.setmPost(editText.getText().toString());
                postText.setText(editText.getText());

                postText.setVisibility(View.VISIBLE);
                removable.setVisibility(View.GONE);

//                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postText.setVisibility(View.GONE);
                removable.setVisibility(View.VISIBLE);



            }
        });



        commentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostAdapter.super.getContext(),CommentActivity.class);
                intent.putExtra("postId", currentPost.getmId());
                PostAdapter.super.getContext().startActivity(intent);
               // Log.v("CommentActivity","Done\n\n\n\n\n");


            }




        });

        return listItemView;


    }
}
