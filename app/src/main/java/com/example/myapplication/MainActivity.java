package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    float split2(float cost) {
        return cost / 2;
    }
    float split3(float cost) {
        return cost / 3;
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
            float itemCostFloat = Float.parseFloat(itemCost.getText().toString());
            LocalDate currentDate = LocalDate.now();

            dateText.setText(currentDate.format(DateTimeFormatter.ofPattern("dd/MM")));
            nameText.setText(itemNameText);

            if (checkedList.size() == 1) {
                switch (checkedList.get(0)) {
                    case 1:
                        costText1.setText(String.valueOf(itemCostFloat));
                        break;
                    case 2:
                        costText2.setText(String.valueOf(itemCostFloat));
                        break;
                    case 3:
                        costText3.setText(String.valueOf(itemCostFloat));
                        break;
                }
            } else if (checkedList.size() == 2) {
                switch (checkedList.get(0)) {
                    case 1:
                        costText1.setText(String.valueOf(split2(itemCostFloat)));
                        break;
                    case 2:
                        costText2.setText(String.valueOf(split2(itemCostFloat)));
                        break;
                    case 3:
                        costText3.setText(String.valueOf(split2(itemCostFloat)));
                        break;
                }
                switch (checkedList.get(1)) {
                    case 2:
                        costText2.setText(String.valueOf(split2(itemCostFloat)));
                        break;
                    case 3:
                        costText3.setText(String.valueOf(split2(itemCostFloat)));
                        break;
                }
            } else {
                costText1.setText(String.valueOf(split3(itemCostFloat)));
                costText2.setText(String.valueOf(split3(itemCostFloat)));
                costText3.setText(String.valueOf(split3(itemCostFloat)));
            }
        }

    }
}