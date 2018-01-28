package com.example.ishu.repairproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class addCompany extends Fragment {


    DataBaseHelper db;
    EditText txtname,txtservice,txtfrom,txtto,txtloc,txtnum;
    public static ArrayList<Company> al=new ArrayList<Company>();
    int doesexits=0;
    String query1="";
    ImageButton stupic;
    private final int requestCode = 20;
    Bitmap bitmap;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.activity_add_company, container, false);
        db = new DataBaseHelper(getActivity());
        File folder=new File("/sdcard/repair");
        folder.mkdirs();

        txtname=(EditText)rootView.findViewById(R.id.txtname);
        txtservice=(EditText)rootView.findViewById(R.id.txtservice);
        txtfrom=(EditText)rootView.findViewById(R.id.txtfrom);
        txtto=(EditText)rootView.findViewById(R.id.txtto);
        txtloc=(EditText)rootView.findViewById(R.id.txtloc);
        txtnum=(EditText)rootView.findViewById(R.id.txtnum);
        stupic=(ImageButton)rootView.findViewById(R.id.stupic);
        btn=(Button)rootView.findViewById(R.id.submit);

        stupic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);
                //flag=1;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String str1=txtname.getText().toString().toUpperCase().trim();
                String str2=txtservice.getText().toString().toUpperCase().trim();
                String str3=txtfrom.getText().toString().toUpperCase().trim();
                String str4=txtto.getText().toString().toUpperCase().trim();
                String str5=txtloc.getText().toString().toUpperCase().trim();
                String str6=txtnum.getText().toString().toUpperCase().trim();

//        String partFilename = "pic1";
//        storeCameraPhotoInSDCard(bitmap, partFilename);

                query1 = "";
                query1 = "SELECT trim(companyname) from CompanyDetails where trim(companyname)='" + str1+ "'";
                Cursor c = db.getdata(query1);
                if (c.getCount() == 0) {

                    String partFilename ="hi";
                    storeCameraPhotoInSDCard(bitmap,partFilename);
                    Toast.makeText(getActivity(), "Image Saved to!!",
                            Toast.LENGTH_SHORT).show();
                    query1="INSERT INTO CompanyDetails VALUES('"+str1+"','-','"+str2+"','"+str6+"','"+str5+"','"+str3+"-"+str4+"','-','"+db.getDateTime()+"','-','"+db.getDateTime()+"');";
                    Toast.makeText(getActivity(), query1, Toast.LENGTH_SHORT).show();
                }
                else
                    doesexits=1;

                db.settdata(query1);
                db.closeDB();

                if (DataBaseHelper.returnval.equalsIgnoreCase("success")) {
                    Toast.makeText(getActivity(), "data saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (doesexits == 1) {
                        Toast.makeText(getActivity(), "Record already exists!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
        return rootView;
    }

   /* public void submit(View view) {

        String str1=txtname.getText().toString().toUpperCase();
        String str2=txtservice.getText().toString().toUpperCase();
        String str3=txtfrom.getText().toString().toUpperCase();
        String str4=txtto.getText().toString().toUpperCase();
        String str5=txtloc.getText().toString().toUpperCase();
        String str6=txtnum.getText().toString().toUpperCase();

//        String partFilename = "pic1";
//        storeCameraPhotoInSDCard(bitmap, partFilename);

        query1 = "";
        query1 = "SELECT trim(companyname) from CompanyDetails where trim(companyname)='" + str1+ "'";
        Cursor c = db.getdata(query1);
        if (c.getCount() == 0) {

            String partFilename ="hi";
            storeCameraPhotoInSDCard(bitmap,partFilename);
            Toast.makeText(getActivity(), "Image Saved to!!",
                    Toast.LENGTH_SHORT).show();
            query1="INSERT INTO CompanyDetails VALUES('"+str1+"','-','"+str2+"','"+str6+"','"+str5+"','"+str3+"-"+str4+"','-','"+db.getDateTime()+"','-','"+db.getDateTime()+"');";
            Toast.makeText(getActivity(), query1, Toast.LENGTH_SHORT).show();
        }
        else
            doesexits=1;

        db.settdata(query1);
        db.closeDB();

        if (DataBaseHelper.returnval.equalsIgnoreCase("success")) {
            Toast.makeText(getActivity(), "data saved", Toast.LENGTH_SHORT).show();
        }
        else {
            if (doesexits == 1) {
                Toast.makeText(getActivity(), "Record already exists!!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

//        Intent in = new Intent(addCompany.this,Main2Activity.class);
//        in.putExtra("name",str1);
//        in.putExtra("service",str2);
//        in.putExtra("from",str3);
//        in.putExtra("to",str4);
//        in.putExtra("loc",str5);
//        in.putExtra("num",str6);
//        startActivity(in);

    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == getActivity().RESULT_OK){
            bitmap = (Bitmap)data.getExtras().get("data");
            stupic.setImageBitmap(bitmap);
        }
    }
    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate) {


        File outputFile = new File(Environment.getExternalStorageDirectory()+"/repair", currentDate + ".jpg");
        if (outputFile.exists()){
            outputFile.delete(); //DELETE existing file
            outputFile = new File(Environment.getExternalStorageDirectory()+"/repair",  currentDate + ".jpg");
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory()+"/repair",filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
