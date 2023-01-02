package com.byulvi.yolyolda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GoogleSignInAccount acc;
    private TextView tv;

    private Tripadapter adapter;
    private DatabaseReference ref;
    private List<Trip> listdata;
    private ListView lv;
    private Trip listitem;
    private List<Trip> listtemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acc = GoogleSignIn.getLastSignedInAccount(this);
        initlist();
        //SETUP
        tv = findViewById(R.id.tvtime);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String nows =sdf.format(cal.getTime());
        Log.d("TAG",nows);

        //VAR'S
        String sabah = "12:00:00";
        String axsam = "19:00:00";
        String etalon = "23:59:59";
        Date sabahdate= null;
        Date etalondate= null;
        Date axsamdate= null;
        Date now = null;
        try {
            now = sdf.parse(nows);
            sabahdate = sdf.parse(sabah);
            etalondate = sdf.parse(etalon);
            axsamdate = sdf.parse(axsam);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        if (now.before(sabahdate) && now.after(etalondate)){
            tv.setText("Sabahınız xeyir.");
        }
        else if(now.after(axsamdate) && now.before(etalondate)){
            tv.setText("Axşamınız xeyir.");
        }
        else{
            tv.setText("Günortanız xeyir.");
        }
        setonclickitem();









        acc = GoogleSignIn.getLastSignedInAccount(this);
        if (acc !=  null){
            String name = acc.getDisplayName();
        }
    }

    private void initlist() {
        lv = findViewById(R.id.listview);
        listdata =new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Trips");
        listtemp =new ArrayList<>();
        adapter = new Tripadapter(this, R.layout.adapter_list_view,listdata,getLayoutInflater());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if list is not clear, wiping it
                if(listdata.size() > 0 ){listdata.clear();}
                if(listtemp.size() > 0 )listdata.clear();
                //while size of elements in database do:
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //equals database values to user var
                    Trip trip = ds.getValue(Trip.class);
                    assert trip != null;
                    //sets user parameter(topic , text, image) to list
                    listitem = new Trip();
                    listitem.setFromloc(trip.fromloc);
                    listitem.setToloc(trip.toloc);
                    listitem.setCartype(trip.cartype);
                    listitem.setPassengers(trip.passengers);
                    listitem.setTime(trip.time);
                    listdata.add(listitem);
                    //fills listtemp to setonclickitem to use
                    listtemp.add(trip);
                    lv.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(listener);
    }

    private void setonclickitem(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trip trip = listtemp.get(position);
                Intent i = new Intent(MainActivity.this,tripdetailsActivity.class);
                i.putExtra("fromloc",trip.fromloc);
                i.putExtra("toloc",trip.toloc);
                i.putExtra("pass",trip.passengers);
                i.putExtra("cartype",trip.cartype);
                i.putExtra("time",trip.time);
                startActivity(i);
            }
        });
    }


    public void onclicknew(View view) {
        Intent i = new Intent(MainActivity.this, Newtripactivity.class);
        i.putExtra("name",acc.getDisplayName());
        startActivity(i);

    }
}