package com.byulvi.yolyolda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class tripdetailsActivity extends AppCompatActivity {
    Intent i ;
    private TextView from,to,pass,type,req,time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tripdetails);
        from =findViewById(R.id.tvfromdet);
        to = findViewById(R.id.tvtodet);
        pass = findViewById(R.id.tvpassdet);
        time = findViewById(R.id.tvtimedep);
        type =findViewById(R.id.tvtypedet);
        req = findViewById(R.id.tvrequest);
        i = getIntent();
        from.setText(i.getStringExtra("fromloc"));
        to.setText(i.getStringExtra("toloc"));
        pass.setText(i.getStringExtra("pass"));
        time.setText(i.getStringExtra("time"));
        String cartype = null;
        if (i.getStringExtra("cartype").equals("s"))cartype = "Sedan";
        if (i.getStringExtra("cartype").equals("j"))cartype = "Jeep";
        if (i.getStringExtra("cartype").equals("p"))cartype = "Pickup";
        type.setText(cartype);


    }
}
