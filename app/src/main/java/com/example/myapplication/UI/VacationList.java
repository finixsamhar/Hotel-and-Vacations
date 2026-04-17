package com.example.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Vacation;
import com.example.myapplication.entities.Excursion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {
    VacationAdapter vacationAdapter;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getAllVacations();
        vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);

        //System.out.println(getIntent().getStringExtra("test"));
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getAllVacations();
        vacationAdapter.setVacations(allVacations);}
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId()==R.id.Save){
        Toast.makeText(VacationList.this, "Saving vacation", Toast.LENGTH_SHORT).show();

            Vacation vacation = new Vacation(0, "Orlando", "Four seasons", "03/27/26", "03/28/26");
            repository.insert(vacation);
             Excursion excursion = new Excursion(0, "Disneyland", "Four seasons", 0, "03/28/26");
            repository.insert(excursion);
            Excursion excursion1 = new Excursion(0, "Universal studios", "Four seasons", 0, "03/28/26");
            repository.insert(excursion1);
            Excursion excursion2 = new Excursion(0, "Swimming", "Four seasons", 0, "03/28/26");
            repository.insert(excursion2);
            return true;
        }
        if (item.getItemId() == R.id.Add){
            Toast.makeText(VacationList.this, "Adding new vacation", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            intent.putExtra("vacationId", -1);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(VacationList.this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return true;
    }
}