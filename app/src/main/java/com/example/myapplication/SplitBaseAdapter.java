package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class SplitBaseAdapter extends BaseAdapter {

    Context context;
    List<String> dateList;
    List<String> nameList;
    List<String> costList1;
    List<String> costList2;
    List<String> costList3;
    LayoutInflater inflater;

    public SplitBaseAdapter(Context context, List<String> dateList, List<String> nameList, List<String> costList1, List<String> costList2, List<String> costList3) {
        this.context = context;
        this.dateList = dateList;
        this.nameList = nameList;
        this.costList1 = costList1;
        this.costList2 = costList2;
        this.costList3 = costList3;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_split_list_view, null);

        TextView dateText = view.findViewById(R.id.date);
        TextView nameText = view.findViewById(R.id.name_text);
        TextView costText1 = view.findViewById(R.id.cost_1);
        TextView costText2 = view.findViewById(R.id.cost_2);
        TextView costText3 = view.findViewById(R.id.cost_3);

        dateText.setText(dateList.get(i));
        nameText.setText(nameList.get(i));
        costText1.setText(costList1.get(i));
        costText2.setText(costList2.get(i));
        costText3.setText(costList3.get(i));

        return view;
    }
}
