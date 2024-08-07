package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText itemName, itemCost;
    CheckBox payer1, payer2, payer3;
    Button goButton;
    TextView dateText, nameText, costText1, costText2, costText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemName = findViewById(R.id.item_name);
        itemCost = findViewById(R.id.item_cost);
        payer1 = findViewById(R.id.split_1);
        payer2 = findViewById(R.id.split_2);
        payer3 = findViewById(R.id.split_3);
        goButton = findViewById(R.id.button_go);
        dateText = findViewById(R.id.date);
        nameText = findViewById(R.id.name_text);
        costText1 = findViewById(R.id.cost_1);
        costText2 = findViewById(R.id.cost_2);
        costText3 = findViewById(R.id.cost_3);

        goButton.setOnClickListener(this);
    }
    double split2(double cost) {
        return (double) Math.round((cost / 2) * 100) / 100;
    }
    double split3(double cost) {
        return (double) Math.round((cost / 3) * 100) / 100;
    }

    @Override
    public void onClick(View view) {
        // temp until i figure out how to make the text persist
        costText1.setText("");
        costText2.setText("");
        costText3.setText("");

        List<Integer> checkedList = new ArrayList<>();
        if (payer1.isChecked()) {
            checkedList.add(1);
        }
        if (payer2.isChecked()) {
            checkedList.add(2);
        }
        if (payer3.isChecked()) {
            checkedList.add(3);
        }

        if (itemName.getText().toString().isEmpty()) {
            Toast.makeText(this,"enter item name", Toast.LENGTH_SHORT).show();
        } else if (itemCost.getText().toString().isEmpty()) {
            Toast.makeText(this,"enter item cost", Toast.LENGTH_SHORT).show();
        } else if (checkedList.isEmpty()) {
            Toast.makeText(this,"check at least one payer", Toast.LENGTH_SHORT).show();
        } else {
            String itemNameText = itemName.getText().toString();
            double itemCostDouble = Double.parseDouble(itemCost.getText().toString());
            LocalDate currentDate = LocalDate.now();

            dateText.setText(currentDate.format(DateTimeFormatter.ofPattern("dd/MM")));
            nameText.setText(itemNameText);

            if (checkedList.size() == 1) {
                switch (checkedList.get(0)) {
                    case 1:
                        costText1.setText(String.valueOf(itemCostDouble));
                        break;
                    case 2:
                        costText2.setText(String.valueOf(itemCostDouble));
                        break;
                    case 3:
                        costText3.setText(String.valueOf(itemCostDouble));
                        break;
                }
            } else if (checkedList.size() == 2) {
                switch (checkedList.get(0)) {
                    case 1:
                        costText1.setText(String.valueOf(split2(itemCostDouble)));
                        break;
                    case 2:
                        costText2.setText(String.valueOf(split2(itemCostDouble)));
                        break;
                    case 3:
                        costText3.setText(String.valueOf(split2(itemCostDouble)));
                        break;
                }
                switch (checkedList.get(1)) {
                    case 2:
                        costText2.setText(String.valueOf(split2(itemCostDouble)));
                        break;
                    case 3:
                        costText3.setText(String.valueOf(split2(itemCostDouble)));
                        break;
                }
            } else {
                costText1.setText(String.valueOf(split3(itemCostDouble)));
                costText2.setText(String.valueOf(split3(itemCostDouble)));
                costText3.setText(String.valueOf(split3(itemCostDouble)));
            }
        }

    }
}