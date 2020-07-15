package com.example.slat_2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.annotation.Retention;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {


    Context context;
    int resource;
    Item dataList;
    ArrayList<String> titlestr = new ArrayList<>();
    ArrayList<String> contentstr = new ArrayList<>();



    public CustomAdapter(Context context, int resource, Item dataList) {
        this.context = context;
        this.resource = resource;
        this.dataList = dataList;
        titlestr = dataList.getTitlestr();
        contentstr = dataList.getDescriptionstr();
    }


    @Override
    public int getCount() {return titlestr.size();}

    @Override
    public Object getItem(int position) {return dataList.getTitlestr().get(position);}

    @Override
    public long getItemId(int position) {return 0;}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_item,null);
        }


        //convertView 세팅(내용 + 이벤트)
        ImageView imageIcon = convertView.findViewById(R.id.imageIcon);
        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewContent = convertView.findViewById(R.id.textViewContent);
        Button deleteBtn = convertView.findViewById(R.id.deleteBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlestr.remove(position);
                contentstr.remove(position);
                notifyDataSetChanged();

            }
        });

//        switch(dataList.get(position).getSubjectstr()){
//
//            case "국어":
//                imageIcon.setImageResource(R.drawable.red);
//                break;
//
//            case "수학":
//                imageIcon.setImageResource(R.drawable.yellow);
//                break;
//
//            case "한국사":
//                imageIcon.setImageResource(R.drawable.green);
//                break;
//        }

        textViewTitle.setText(titlestr.get(position));
        textViewContent.setText(contentstr.get(position));
        return convertView;

    }


}
