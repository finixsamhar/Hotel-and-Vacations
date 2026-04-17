package com.example.myapplication.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Excursion;
import com.example.myapplication.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String name;
    String hotel;

    String startDate;
    String endDate;
    int vacationId;
    EditText editName;
    EditText editHotel;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
    Vacation currentVacation;
    int numExcursion;
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    private static final String MY_FORMAT = "MM/dd/yy";
    private final SimpleDateFormat sdf = new SimpleDateFormat(MY_FORMAT, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);

        repository = new Repository(getApplication());

        vacationId = getIntent().getIntExtra("vacationId", -1);
        name = getIntent().getStringExtra("vacationName");
        hotel = getIntent().getStringExtra("Hotel");
        startDate = getIntent().getStringExtra("StartDate");
        endDate = getIntent().getStringExtra("EndDate");

        editName = findViewById(R.id.Titletext);
        editHotel = findViewById(R.id.Hoteltext);
        editStartDate = findViewById(R.id.StartDate);
        editEndDate = findViewById(R.id.EndDate);
        editName.setText(name);
        editHotel.setText(hotel);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        startDateListener = (view, year, month, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, month);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStart();

        };
        endDateListener = (view, year, month, dayOfMonth) -> {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, month);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEnd();

        };
        editStartDate.setOnClickListener(v -> {
            String info = editStartDate.getText().toString();
            if (info.isEmpty()) info = "03/22/26";
            try {
                myCalendarStart.setTime(sdf.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(VacationDetails.this, startDateListener,
                    myCalendarStart.get(Calendar.YEAR),
                    myCalendarStart.get(Calendar.MONTH),
                    myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });
        editEndDate.setOnClickListener(v -> {
            String info = editEndDate.getText().toString();
            if (info.isEmpty()) info = "03/22/26";
            try {
                myCalendarEnd.setTime(sdf.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(VacationDetails.this, endDateListener,
                    myCalendarEnd.get(Calendar.YEAR),
                    myCalendarEnd.get(Calendar.MONTH),
                    myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
        });


        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationId", vacationId);
                startActivity(intent);
            }

        });

        updateRecyclerView();
    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));

    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));

    }



    private void updateRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerView);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getVacationId() == vacationId) {
                filteredExcursions.add(p);
            }
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.save_vacation) {
            if (item.getItemId() == R.id.save_vacation) {
                String StartDate = editStartDate.getText().toString();
                String EndDate = editEndDate.getText().toString();
                try {
                    Date StartD = sdf.parse(StartDate);
                    Date EndD = sdf.parse(EndDate);
                    if (StartD.after(EndD) ) {
                        Toast.makeText(VacationDetails.this, "Start date must be before end date", Toast.LENGTH_LONG).show();
                        return true;
                    }
                    } catch (ParseException e) {
                    Toast.makeText(VacationDetails.this, "Invalid date format", Toast.LENGTH_LONG).show();
                    return true;
                }

            }
            Vacation vacation;
            if (vacationId == -1) {
                if (repository.getAllVacations().isEmpty()) {
                    vacationId = 1;
                } else {
                    vacationId = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationId() + 1;
                }
                vacation = new Vacation(vacationId, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.insert(vacation);
            } else {
                vacation = new Vacation(vacationId, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.update(vacation);
            }
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.update_vacation) {
            editName.requestFocus();
            return true;
        }

        if (item.getItemId() == R.id.delete_vacation) {
            for (Vacation vacation : repository.getAllVacations()) {
                if (vacation.getVacationId() == vacationId) currentVacation = vacation;
            }


            numExcursion = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationId() == vacationId) numExcursion++;
            }

            if (numExcursion == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.notify){
            String StartDate = editStartDate.getText().toString();
            String EndDate = editEndDate.getText().toString();
            String title = editName.getText().toString();
            try{
                Date startDate = sdf.parse(StartDate);
                if (startDate != null){
                    long trigger = startDate.getTime();
                    Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                    intent.putExtra("key",  title + " is starting today");
                    PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }

                Date endDate = sdf.parse(EndDate);
                if (endDate != null) {
                    long trigger = endDate.getTime();
                    Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                    intent.putExtra("key", title + " is ending today!");

                    PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }

                Toast.makeText(this, "Vacation alerts scheduled!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error setting alerts", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.sharevacation) {
            String title = editName.getText().toString();
            String hotel = editHotel.getText().toString();
            String start = editStartDate.getText().toString();
            String end = editEndDate.getText().toString();


            String shareBody = "Vacation Details:\n" +
                    "Title: " + title + "\n" +
                    "Hotel: " + hotel + "\n" +
                    "Dates: " + start + " to " + end;

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Share Vacation Details");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView();
    }
}

