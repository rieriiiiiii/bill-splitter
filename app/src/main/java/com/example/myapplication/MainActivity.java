package com.example.myapplication;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
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
    List<String> dateList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<String> costList1 = new ArrayList<>();
    List<String> costList2 = new ArrayList<>();
    List<String> costList3 = new ArrayList<>();
    ListView listView;
    Builder alert;
    SplitBaseAdapter splitBaseAdapter;
    List<Integer> checkedItemsList = new ArrayList<>();
    DatabaseHelper billDatabase;

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

        goButton.setOnClickListener(this);

        splitBaseAdapter = new SplitBaseAdapter(getApplicationContext(), dateList, nameList, costList1, costList2, costList3);
        listView = findViewById(R.id.splitListView);
        alert = new Builder(MainActivity.this);

        billDatabase = new DatabaseHelper(this);

        Cursor res = billDatabase.getData();
        while (res.moveToNext()) {
            dateList.add(res.getString(1));
            nameList.add(res.getString(2));
            costList1.add(res.getString(3));
            costList2.add(res.getString(4));
            costList3.add(res.getString(5));
        }

        listView.setAdapter(splitBaseAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                alert.setMessage("confirm delete ?");
                alert.setCancelable(true);

                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int u) {

                        dateList.remove(i);
                        nameList.remove(i);
                        costList1.remove(i);
                        costList2.remove(i);
                        costList3.remove(i);

                        Cursor index = billDatabase.getData();
                        int idNum;
                        int inc = 0;
                        while (index.moveToNext()) {
                            if (inc == i) {
                                idNum = index.getInt(0);
                                billDatabase.deleteData(idNum);
                            }
                            inc++;
                        }

                        splitBaseAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });

                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int u) {
                        dialog.cancel();
                    }
                });

                alert.show();
                return true;
            }
        });

// TODO: add a "checked" tag to SQL to persist grey out
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (checkedItemsList.contains(i)) {
//                    listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                    checkedItemsList.remove(Integer.valueOf(i));
//                } else {
//                    listView.getChildAt(i).setBackgroundColor(Color.parseColor("#f2f2f2"));
//                    checkedItemsList.add(i);
//                }
//            }
//        });

    }
    double split2(double cost) {
        return (double) Math.round((cost / 2) * 100) / 100;
    }
    double split3(double cost) { return (double) Math.round((cost / 3) * 100) / 100; }

    @Override
    public void onClick(View view) {

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

            dateList.add(currentDate.format(DateTimeFormatter.ofPattern("dd/MM")));
            nameList.add(itemNameText);

            if (checkedList.size() == 1) {
                switch (checkedList.get(0)) {
                    case 1:
                        costList1.add(String.valueOf(itemCostDouble));
                        costList2.add("");
                        costList3.add("");
                        break;
                    case 2:
                        costList2.add(String.valueOf(itemCostDouble));
                        costList1.add("");
                        costList3.add("");
                        break;
                    case 3:
                        costList3.add(String.valueOf(itemCostDouble));
                        costList1.add("");
                        costList2.add("");
                        break;
                }
            } else if (checkedList.size() == 2) {
                if (checkedList.get(0) == 1) {
                    if (checkedList.get(1) == 2) {
                        costList1.add(String.valueOf(split2(itemCostDouble)));
                        costList2.add(String.valueOf(split2(itemCostDouble)));
                        costList3.add("");
                    } else {
                        costList1.add(String.valueOf(split2(itemCostDouble)));
                        costList3.add(String.valueOf(split2(itemCostDouble)));
                        costList2.add("");
                    }
                } else {
                    costList2.add(String.valueOf(split2(itemCostDouble)));
                    costList3.add(String.valueOf(split2(itemCostDouble)));
                    costList1.add("");
                }
            } else {
                costList1.add(String.valueOf(split3(itemCostDouble)));
                costList2.add(String.valueOf(split3(itemCostDouble)));
                costList3.add(String.valueOf(split3(itemCostDouble)));
            }

            billDatabase.insertData(dateList.get(dateList.size() - 1), nameList.get(nameList.size() - 1), costList1.get(costList1.size() - 1), costList2.get(costList2.size() - 1), costList3.get(costList3.size() - 1));
        }

        splitBaseAdapter.notifyDataSetChanged();
    }

}