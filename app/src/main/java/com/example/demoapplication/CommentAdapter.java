package com.example.demoapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {
    public CommentAdapter(@NonNull Context context,  @NonNull ArrayList<Comment> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_comment,parent,false);
        }

        final Comment currentComment = getItem(position);

        TextView idComment = (TextView) listItemView.findViewById(R.id.id_comment);
        idComment.setText(Integer.toString(currentComment.getmPostId()));

        TextView titleText = (TextView) listItemView.findViewById(R.id.name_of_comment);
        titleText.setText(currentComment.getmName());

        final TextView postText = (TextView) listItemView.findViewById(R.id.text_comment);
        postText.setText(currentComment.getmComment());

        final LinearLayout remoavaleLayout = (LinearLayout) listItemView.findViewById(R.id.removable_comment_layout);
        remoavaleLayout.setVisibility(View.GONE);

        final LinearLayout staticLayout = (LinearLayout) listItemView.findViewById(R.id.static_comment_layout);

        final EditText editComment = (EditText) listItemView.findViewById(R.id.edit_comment);
        editComment.setText(currentComment.getmComment());
        editComment.setSelection(currentComment.getmComment().length());

        if(remoavaleLayout.getVisibility()==View.GONE && staticLayout.getVisibility()==View.GONE)
        {
            remoavaleLayout.setVisibility(View.VISIBLE);
        }

        editComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remoavaleLayout.setVisibility(View.VISIBLE);
            }
        });



        final Button editButton = (Button) listItemView.findViewById(R.id.edit_comment_button);
        Button saveButton = (Button) listItemView.findViewById(R.id.save_comment_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentComment.setmComment(editComment.getText().toString());
                postText.setText(currentComment.getmComment());

                staticLayout.setVisibility(View.VISIBLE);
                remoavaleLayout.setVisibility(View.GONE);


            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editComment.setText(currentComment.getmComment());
                staticLayout.setVisibility(View.GONE);
                remoavaleLayout.setVisibility(View.VISIBLE);
            }
        });

//        Button button = (Button) listItemView.findViewById(R.id.see_comment);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PostAdapter.super.getContext(),CommentActivity.class);
//                intent.putExtra("postId", currentPost.getmId());
//                PostAdapter.super.getContext().startActivity(intent);
//                Log.v("CommentActivity","Done\n\n\n\n\n");
//
//
//            }
 //       });

        return listItemView;


    }
}
