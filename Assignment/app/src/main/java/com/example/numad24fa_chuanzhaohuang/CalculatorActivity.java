package com.example.numad24fa_chuanzhaohuang;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorActivity extends AppCompatActivity {

    // TextView to display the current input and result
    private TextView tvDisplay;

    // StringBuilder to store the current input for the calculator
    private StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity from the activity_calc XML file
        setContentView(R.layout.activity_calc);

        // Bind the TextView from the layout using its ID
        tvDisplay = findViewById(R.id.tv_display);

        // Array of button IDs for the number buttons (0-9)
        int[] numberButtonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2,
                R.id.btn_3, R.id.btn_4, R.id.btn_5,
                R.id.btn_6, R.id.btn_7, R.id.btn_8,
                R.id.btn_9
        };

        // Set click listeners for all the number buttons
        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Append the number pressed to the input
                    Button button = (Button) v;
                    input.append(button.getText().toString());
                    // Update the display with the current input
                    tvDisplay.setText(input.toString());
                }
            });
        }

        // Array of button IDs for the operator buttons (+, -, x)
        int[] operatorButtonIds = {R.id.btn_plus, R.id.btn_minus, R.id.btn_multiply};

        // Set click listeners for the operator buttons
        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Append the operator and spaces for better readability in the display
                    Button button = (Button) v;
                    input.append(" " + button.getText().toString() + " ");
                    // Update the display with the current input
                    tvDisplay.setText(input.toString());
                }
            });
        }

        // "=" button to evaluate the expression when clicked
        Button btnEquals = findViewById(R.id.btn_equals);
        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateExpression(); // Call method to evaluate the input expression
            }
        });

        // "x" button to delete the last character (assuming this button acts as a delete key)
        Button btnClear = findViewById(R.id.btn_multiply); // Assuming multiply button is used as delete
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there are characters to delete
                if (input.length() > 0) {
                    // Remove the last character from the input
                    input.deleteCharAt(input.length() - 1);
                    // Update the display after deletion
                    tvDisplay.setText(input.toString());
                }
            }
        });
    }

    // Method to evaluate the mathematical expression entered by the user
    private void evaluateExpression() {
        try {
            // Build the expression from the input and evaluate it
            Expression expression = new ExpressionBuilder(input.toString()).build();
            double result = expression.evaluate(); // Calculate the result
            // Display the result in the TextView
            tvDisplay.setText(String.valueOf(result));
            input.setLength(0); // Clear the input
            input.append(result); // Save the result for future calculations
        } catch (Exception e) {
            // If there is an error in the expression, display "Error"
            tvDisplay.setText("Error");
        }
    }
}
