package com.example.numad24fa_chuanzhaohuang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable EdgeToEdge display (hides the system bars for a full immersive experience)
        EdgeToEdge.enable(this);

        // Set the layout for this activity from the activity_main XML file
        setContentView(R.layout.activity_main);

        // Set the window insets to ensure that system bars (like the status bar) don’t overlap the content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply padding to the view to accommodate for system bars
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the "About Me" button by its ID from the layout
        Button aboutMeButton = findViewById(R.id.about_me_button);

        // Set a click listener for the "About Me" button
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display a Toast message showing the name and email when clicked
                Toast.makeText(MainActivity.this, "Chuanzhao Huang\nhuang.chua@northeastern.edu", Toast.LENGTH_LONG).show();
            }
        });

        // Find the "Quick Calculator" button by its ID from the layout
        Button quicCalcButton = findViewById(R.id.btn_quic_calc);

        // Set a click listener for the "Quick Calculator" button to launch the CalculatorActivity
        quicCalcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to CalculatorActivity
                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(intent); // Start the CalculatorActivity
            }
        });
    }
}
