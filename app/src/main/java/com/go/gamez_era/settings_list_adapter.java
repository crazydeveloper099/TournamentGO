package com.go.gamez_era;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class settings_list_adapter extends ArrayAdapter<model_for_settings_list_view>{

    public settings_list_adapter(Context context, ArrayList<model_for_settings_list_view> al)
    {
        super(context,0,al);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vw=convertView;
        if(vw==null){
            vw= LayoutInflater.from(getContext()).inflate(R.layout.settings_row,parent,false);
        }
        model_for_settings_list_view clas=getItem(position);



        TextView title=(TextView)vw.findViewById(R.id.settings_list_heading);
        title.setText(clas.getHeading());

        TextView ml=(TextView)vw.findViewById(R.id.settings_list_description);
        ml.setText(clas.getDescription());



        return vw;

    }

}
