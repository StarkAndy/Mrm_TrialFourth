package com.example.ishu.repairproject;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class testing extends AppCompatActivity {

    ListView listview;
    MyAdapter m;
    String str1,str2,str3,str4,str5,str6;
    DataBaseHelper db;
    String query1="";
    EditText etxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        listview = (ListView)findViewById(R.id.listView);
        db = new DataBaseHelper(this);

        m=new MyAdapter();
        m.notifyDataSetChanged();
        listview.setAdapter(m);

        query1 = "";
        query1 = "SELECT * from CompanyDetails where trim(services)='" + search_data.search1+ "'";
        Cursor c = db.getdata(query1);
        if (c.getCount() == 0) {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,etxt.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (c.moveToNext()) {
                Toast.makeText(this, c.getString(0) + c.getString(1) + c.getString(2) + c.getString(3) + c.getString(4) + c.getString(5), Toast.LENGTH_SHORT).show();
                str1=c.getString(0);
                str5=c.getString(4);
                str3=c.getString(5);
//            str4=c.getString(0);
                Company comp=new Company(str1,str5,str3);
                addCompany.al.add(comp);
                m.notifyDataSetChanged();
            }
        }


    }

    public void f2() {
    }

//    public void f1(String i) {
//        query1 = "";
//        query1 = "SELECT * from CompanyDetails where trim(services)='" + etxt.getText().toString().toUpperCase().trim()+ "'";
//        Cursor c = db.getdata(query1);
//        if (c.getCount() == 0) {
//            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,etxt.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            while (c.moveToNext()) {
//                Toast.makeText(this, c.getString(0) + c.getString(1) + c.getString(2) + c.getString(3) + c.getString(4) + c.getString(5), Toast.LENGTH_SHORT).show();
//                str1=c.getString(0);
//                str5=c.getString(4);
//                str3=c.getString(5);
////            str4=c.getString(0);
//                Company comp=new Company(str1,str5,str3);
//                addCompany.al.add(comp);
//                m.notifyDataSetChanged();
//            }
//        }
//
//    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return addCompany.al.size();
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

            Company c=addCompany.al.get(i);
            String str1=c.getCname();
            String str2=c.getClocation();
            String str3=c.getCtimmings();

            view = getLayoutInflater().inflate(R.layout.list_layout,null);
            TextView txt1 = (TextView) view.findViewById(R.id.txt1);//name
            TextView txt2 = (TextView) view.findViewById(R.id.txt2);//loc
            TextView txt3 = (TextView) view.findViewById(R.id.txt3);//timings
            TextView txt4 = (TextView) view.findViewById(R.id.txt4);//dist
            RatingBar rb = (RatingBar) view.findViewById(R.id.rb);
            ImageView image=(ImageView)view.findViewById(R.id.imageView_round);

            txt1.setText(str1);
            txt2.setText(str2);
            txt3.setText(str3);
            txt4.setText("4kms");

            rb.setRating(2.5f);
            rb.setIsIndicator(true);
            image.setImageResource(R.drawable.student);


            if (i % 2 == 0)
                view.setBackgroundResource(R.drawable.gradiant_bg);
            else
                view.setBackgroundColor(Color.parseColor("WHITE"));

            return view;
        }
    }
}
