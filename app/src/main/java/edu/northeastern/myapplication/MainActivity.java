package edu.northeastern.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference to the About Me button
        Button aboutMeButton = findViewById(R.id.about_me_button);

        // Set click listener for the button
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a Toast with name and email
                Toast.makeText(MainActivity.this, "Name: Chuanzhao Huang\nEmail: huang.chua@northeastern.edu", Toast.LENGTH_LONG).show();
            }
        });
    }
}
