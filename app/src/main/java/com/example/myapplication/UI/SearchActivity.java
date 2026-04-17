package com.example.myapplication.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Vacation;

import java.util.List;

public class SearchActivity extends AppCompatActivity { //This is the inheritance of the activity

    private Button reportButton;
    private EditText editSearchName;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //This is the polymorphism
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        reportButton = findViewById(R.id.reportButton);
        editSearchName = findViewById(R.id.editSearchName);
        recyclerView = findViewById(R.id.searchRecyclerView);
        repository = new Repository(getApplication());
        searchAdapter = new SearchAdapter(this);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportButton.setOnClickListener(v -> {
            showReport(editSearchName.getText().toString());
        });

        editSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterResults();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        filterResults();
    }

    private void filterResults() {
        String nameInput = editSearchName.getText().toString();
        String query = "%" + nameInput + "%";


        List<Vacation> filteredList = repository.getFilteredVacations(query);


        searchAdapter.setVacations(filteredList);
    }

    private void showReport(String searchName) {
        StringBuilder report = new StringBuilder();
        report.append("Search Filter: ").append(searchName).append("\n");
        String dateStamp = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        report.append("Reported on: ").append(dateStamp).append("\n");
        report.append("------------------------------------------\n\n");
        List<Vacation> currentList = searchAdapter.getVacations();
        for (Vacation v : currentList) {
            report.append(String.format("%-15s | %s - %s\n",
                    v.getVacationName(), v.getStartDate(), v.getEndDate()));

        }
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Vacation Report");
        builder.setMessage(report.toString());
        builder.setPositiveButton("OK", null);
        builder.show();

    }
}


