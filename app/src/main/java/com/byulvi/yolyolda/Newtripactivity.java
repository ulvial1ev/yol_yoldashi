package com.byulvi.yolyolda;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Newtripactivity extends AppCompatActivity {
    private EditText ed,ed2;
    private int hour,min;
    private TextView tv;
    private DatabaseReference ref;
    //INFO
    private String cartype="";
    private String passengers="";
    private String date = null;
    private String fromloc;
    private String toloc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtrip_layout);
        ed = findViewById(R.id.edlocation);
        tv =findViewById(R.id.tvsettime);
        ref = FirebaseDatabase.getInstance().getReference();
        ed2 = findViewById(R.id.edlocation2);
        Places.initialize(getApplicationContext(),"AIzaSyBTq0CbJAzmfu5uj2ixIIpevci1Odg8SeQ");
        ed.setFocusable(false);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG, Place.Field.NAME);
                Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(Newtripactivity.this);
                startActivityForResult(i,89);
            }
        };
        ed.setOnClickListener(onClickListener);
        ed2.setOnClickListener(onClickListener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 89 & resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            ed.setText(place.getAddress());

        }
        else {
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.d("TAG",status.getStatusMessage());
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.r_sedan:
                cartype="";
                if (checked){
                    cartype = "s";
                }
                else{
                    cartype="";
                }

                    break;
            case R.id.r_jeep:
                cartype="";
                if (checked){
                    cartype="j";
                }
                else{
                    cartype="";
                }
                    break;
            case R.id.r_pickup:
                cartype="";
                if (checked){
                    cartype="p";
                }
                else{
                    cartype="";
                }
                break;
        }
    }

    public void onRadioButtonClicked1(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.r_1:
                passengers="";
                if (checked){
                    passengers = "1";
                }
                else{
                    passengers="";
                }
                break;
            case R.id.r_2or3:
                passengers="";
                if (checked){
                    passengers="2-3";
                }
                else{
                    passengers="";
                }
                break;
            case R.id.r_4ormore:
                passengers="";
                if (checked){
                    passengers="4+";
                }
                else{
                    passengers="";
                }
                break;
        }
    }

    public void onclickcreate(View view) {
        if (!cartype.equals("") && !passengers.equals("") && date != null){
            Trip trip = new Trip("Baku","Lankaran",cartype,passengers,date);
            ref.child("Trips").push().setValue(trip).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Newtripactivity.this, "Səyahət yaradıldı", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Newtripactivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            Toast.makeText(this, "Xanaları doldurun", Toast.LENGTH_SHORT).show();
        }
    }

    public void onclickselecttime(View view) {
        TimePickerDialog tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                Calendar calendar = Calendar.getInstance();
                calendar.set(0,0,0,hourOfDay,minute);
                tv.setBackground(getResources().getDrawable(R.drawable.radselected));
                tv.setTextColor(getResources().getColor(R.color.black));
                date = (String) DateFormat.format("HH:mm",calendar);
                tv.setText(DateFormat.format("HH:mm",calendar));
            }
        },12,0,true);
        tp.updateTime(hour,min);
        tp.show();
    }
}
