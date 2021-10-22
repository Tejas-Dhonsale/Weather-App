package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Rvmodel> rvmodelArrayList;

    public RvAdapter(Context context, ArrayList<Rvmodel> rvmodelArrayList) {
        this.context = context;
        this.rvmodelArrayList = rvmodelArrayList;
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_iteam,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int position) {
       Rvmodel model =rvmodelArrayList.get(position);
       holder.tvtemp.setText(model.getTemperature()+"°c");
       holder.tvtime.setText(model.getTime());

        Picasso.get().load("https:".concat(model.getIcon())).into(holder.tvcondition);

    }

    @Override
    public int getItemCount() {
        return rvmodelArrayList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
       private TextView tvtemp ,tvtime;
       private ImageView tvcondition;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtemp =  itemView.findViewById(R.id.Tvtemp);
            tvcondition = itemView.findViewById(R.id.Tvcondition);
            tvtime = itemView.findViewById(R.id.Tvtime);

        }
    }
}
