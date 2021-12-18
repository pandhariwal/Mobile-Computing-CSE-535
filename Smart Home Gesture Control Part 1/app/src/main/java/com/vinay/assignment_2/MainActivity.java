package com.vinay.assignment_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final Object ALL_PERMISSION_CODE = 100;

    @Override
    protected void onStart() {
        super.onStart();

        createPermissions();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setSelection(0);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Gestures, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>0) {
                    String inp = String.valueOf(spinner.getSelectedItem());

                    Intent intent;
                    intent = new Intent(getApplicationContext(), Page2.class);
                    ContentValues values = null;
                    try {

                        Bundle b=getIntent().getExtras();
                        if(b==null)
                        {
                        values = new ContentValues();
                        b = new Bundle();
                        values.put("selectedPage2", inp);
                        b.putParcelable("Extras", values);

                        intent.putExtras(b);}else{
                            values=b.getParcelable("Extras");
                            values.put("selectedPage2", inp);
                            b.putParcelable("Extras", values);
                            intent.putExtras(b);
                        }

                    } catch (SQLException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    startActivity(intent);
                }
                }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void createPermissions(){
        String permission = Manifest.permission.CAMERA;
        String permission3 = Manifest.permission.INTERNET;
        String permission1=Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String permission2=Manifest.permission.RECORD_AUDIO;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, permission1) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, permission2) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, permission3) != PackageManager.PERMISSION_GRANTED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, permission) || !ActivityCompat.shouldShowRequestPermissionRationale(this, permission1) || !ActivityCompat.shouldShowRequestPermissionRationale(this, permission2) || !ActivityCompat.shouldShowRequestPermissionRationale(this, permission3)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,Manifest.permission.INTERNET}, (Integer) ALL_PERMISSION_CODE);
            }
        }
    }
}