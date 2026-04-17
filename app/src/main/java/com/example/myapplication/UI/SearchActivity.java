package com.example.myapplication.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Vacation;

import java.util.List;

public class SearchActivity extends AppCompatActivity { //This is the inheritance of the activity


    private EditText editSearchName;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        editSearchName = findViewById(R.id.editSearchName);

        recyclerView = findViewById(R.id.searchRecyclerView);


        repository = new Repository(getApplication());
        searchAdapter = new SearchAdapter(this);

        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        editSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterResults();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        filterResults();
    }

    private void filterResults() {
        String nameInput = editSearchName.getText().toString();
        String query = "%" + nameInput + "%";



        List<Vacation> filteredList = repository.getFilteredVacations(query);


        searchAdapter.setVacations(filteredList);
    }
}


