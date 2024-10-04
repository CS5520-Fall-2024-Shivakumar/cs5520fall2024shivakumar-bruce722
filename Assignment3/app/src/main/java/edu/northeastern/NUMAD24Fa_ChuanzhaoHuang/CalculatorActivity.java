package edu.northeastern.NUMAD24Fa_ChuanzhaoHuang;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator); // 关联到你的 XML 布局文件

        tvDisplay = findViewById(R.id.tv_display);

        // 数字按钮 (0-9)
        int[] numberButtonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2,
                R.id.btn_3, R.id.btn_4, R.id.btn_5,
                R.id.btn_6, R.id.btn_7, R.id.btn_8,
                R.id.btn_9
        };
        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    input.append(button.getText().toString());
                    tvDisplay.setText(input.toString());
                }
            });
        }


        int[] operatorButtonIds = {R.id.btn_plus, R.id.btn_minus, R.id.btn_multiply};
        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    input.append(" " + button.getText().toString() + " ");
                    tvDisplay.setText(input.toString());
                }
            });
        }

        Button btnEquals = findViewById(R.id.btn_equals);
        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateExpression();
            }
        });

        Button btnClear = findViewById(R.id.btn_multiply);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0) {
                    input.deleteCharAt(input.length() - 1);
                    tvDisplay.setText(input.toString());
                }
            }
        });


    }


    private void evaluateExpression() {
        try {
            Expression expression = new ExpressionBuilder(input.toString()).build();
            double result = expression.evaluate();
            tvDisplay.setText(String.valueOf(result));
            input.setLength(0);
            input.append(result);
        } catch (Exception e) {
            tvDisplay.setText("Error");
        }
    }


}