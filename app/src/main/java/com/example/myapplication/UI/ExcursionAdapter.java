package com.example.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entities.Excursion;


import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private Context context;
    private final LayoutInflater mInflater;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;


        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionId());
                    intent.putExtra("name", current.getExcursionName());
                    intent.putExtra("date", current.getDate());
                    intent.putExtra("hotel", current.getHotel());
                    intent.putExtra("VacationId", current.getVacationId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionAdapter.ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursion current = mExcursions.get(position);
            String name = current.getExcursionName();
            int vacationId = current.getVacationId();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(String.valueOf(vacationId));
        } else {
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemView2.setText("No vacation Id");

        }

    }
    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();
        } else return 0;
    }


}