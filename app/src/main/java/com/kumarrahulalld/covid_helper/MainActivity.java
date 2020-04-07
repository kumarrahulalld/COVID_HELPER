package com.kumarrahulalld.covid_helper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    String key="";
String lat="",lon="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=auth = FirebaseAuth.getInstance();
        key=getIntent().getStringExtra("phone");
        lat=(getIntent().getStringExtra("Lat"));
        lon=(getIntent().getStringExtra("Lon"));

    }

    public void logout(View view)
    {
        auth.signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
    }
    public void need(View view){
        Intent intent=new Intent(this,NeedHelp.class);
        intent.putExtra("phone",key+"");
        intent.putExtra("Lat",lat+"");
        intent.putExtra("Lon",lon+"");

        startActivity(intent);
    }
    public void yourreq(View view){
        Intent intent=new Intent(this,YourRequests.class);
        intent.putExtra("Phone",key+"");
        startActivity(intent);
    }
    public void updts(View view){
        Intent intent=new Intent(this,Updates.class);
        startActivity(intent);
    }

    public void willreq(View view){
        Intent intent=new Intent(this,WillRequest.class);
        intent.putExtra("Phone",key+"");
        startActivity(intent);
    }
}
