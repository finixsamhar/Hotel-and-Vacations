package com.example.myapplication.UI;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Excursion;
import com.example.myapplication.entities.Vacation;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Vacation> mVacations = new ArrayList<>();
    private final Context context;
    private final Repository repository;

    public SearchAdapter(Context context) {
        this.context = context;
        this.repository = new Repository((Application) context.getApplicationContext());
    }
    public List<Vacation> getVacations() {
        return mVacations;
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Vacation current = mVacations.get(position);
        holder.vacationName.setText(current.getVacationName());
        holder.vacationDates.setText(current.getStartDate() + " - " + current.getEndDate());


        StringBuilder excursionString = new StringBuilder("Excursions: ");
        boolean hasExcursions = false;

        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == current.getVacationId()) {
                excursionString.append("\n• ").append(e.getExcursionName());
                hasExcursions = true;
            }
        }

        if (!hasExcursions) {
            holder.excursionList.setText("Excursions: None");
        } else {
            holder.excursionList.setText(excursionString.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mVacations.size();
    }

    public void setVacations(List<Vacation> vacations) {
        this.mVacations = vacations;
        notifyDataSetChanged();
    }


    class SearchViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationName;
        private final TextView vacationDates;
        private final TextView excursionList;

        private SearchViewHolder(View itemView) {
            super(itemView);
            vacationName = itemView.findViewById(R.id.searchVacationName);
            vacationDates = itemView.findViewById(R.id.searchVacationDates);
            excursionList = itemView.findViewById(R.id.searchExcursionList);
        }
    }
}


