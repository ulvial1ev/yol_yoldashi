package com.byulvi.yolyolda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Tripadapter  extends ArrayAdapter<Trip> {
    private LayoutInflater inflater;
    private List<Trip> ListItem = new ArrayList<>();
    private Context context;
    public Tripadapter(@NonNull Context context, int resource,List<Trip> ListItem, LayoutInflater inflater) {
        super(context, resource,ListItem);
        this.context = context;
        this.inflater = inflater;
        this.ListItem = ListItem;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder viewholder;
        Trip listitemmain = ListItem.get(position);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.adapter_list_view,null,false);
            viewholder = new Viewholder();
            viewholder.from =convertView.findViewById(R.id.tvfrom);
            viewholder.to =convertView.findViewById(R.id.tvto);
            viewholder.time = convertView.findViewById(R.id.tvadaptertime);
            viewholder.props = convertView.findViewById(R.id.tvprops);
        }
        else{
            viewholder = (Viewholder) convertView.getTag();
        }

        viewholder.from.setText(listitemmain.getFromloc());
        viewholder.to.setText(listitemmain.getToloc());
        viewholder.time.setText(listitemmain.getTime());
        String budget;
        if (listitemmain.getPassengers().equals("1"))budget = "50/50";
        else if(listitemmain.getPassengers().equals("2-3")) budget = "33/33/33";
        else budget = "20/20/20/20";
        viewholder.props.setText("Insan sayı: "+ listitemmain.getPassengers() + ", Avtomobilin növü: " + listitemmain.getCartype()+ " Büdcə " + budget);
        convertView.setTag(viewholder);
        return convertView;


    }

    private class Viewholder{
        TextView from;
        TextView to;
        TextView time;
        TextView props;
    }
}
