package com.example.mario;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ChatActivity extends AppCompatActivity {

    private EditText mMessage;
    private FloatingActionButton mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get chat ID from Bundle and load it in RecyclerView
    }
}
