package com.example.termtest;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class IDActivity extends AppCompatActivity implements View.OnClickListener {
    String id,password;
    private FirebaseAuth auth;
    private Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("ID/PASSWORD");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        setContentView(R.layout.activity_id);
        Logout = (Button)findViewById(R.id.Logout);
        auth = FirebaseAuth.getInstance();
        idlayout();
        Logout.setOnClickListener(this);

    }
    @Override
    public void onClick(View view)
    {
        if(view==Logout)
        {
            Toast.makeText(IDActivity.this, "Log out complete", Toast.LENGTH_SHORT).show();
            auth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
    public void idlayout() {
        TextView ID = (TextView) findViewById(R.id.textView1);
        TextView PASSWORD = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        password = intent.getStringExtra("Password");
        ID.setText("ID: "+id);
        PASSWORD.setText("PASSWORD: " +password);
    }

}
