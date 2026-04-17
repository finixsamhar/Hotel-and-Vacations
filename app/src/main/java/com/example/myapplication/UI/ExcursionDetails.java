package com.example.myapplication.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.entities.Excursion;
import com.example.myapplication.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String name;

    String date;
    String hotel;
    String note;
    int excursionId;
    int vacationId;
    EditText editName, editHotel, editNote, editDate;

    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    private static final String MY_FORMAT = "MM/dd/yy";
    private final SimpleDateFormat sdf = new SimpleDateFormat(MY_FORMAT, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());


        name = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");
        hotel = getIntent().getStringExtra("hotel");
        excursionId = getIntent().getIntExtra("id", -1);
        vacationId = getIntent().getIntExtra("vacationId", -1);


        editName = findViewById(R.id.excursionName);
        editDate = findViewById(R.id.date);
        editHotel = findViewById(R.id.hotel);
        editNote = findViewById(R.id.note);




        editName.setText(name);
        editDate.setText(date);
        editHotel.setText(hotel);
        editNote.setText(note);



            startDate = (view, year, month, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, month);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStart();
        };

        editDate.setOnClickListener(v -> {
            String info = editDate.getText().toString();
            if (info.isEmpty()) info = "03/22/26";
            try {
                myCalendarStart.setTime(sdf.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(ExcursionDetails.this, startDate,
                    myCalendarStart.get(Calendar.YEAR),
                    myCalendarStart.get(Calendar.MONTH),
                    myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });
    }
    
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.excursionsave) {
            Excursion excursion;
            if (excursionId == -1) {
                int newId = repository.getAllExcursions().isEmpty() ? 1 :
                        repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionId() + 1;
                excursion = new Excursion(newId, editName.getText().toString(), editHotel.getText().toString(), vacationId, editDate.getText().toString());
                repository.insert(excursion);
            } else {
                excursion = new Excursion(excursionId, editName.getText().toString(), editHotel.getText().toString(), vacationId, editDate.getText().toString());
                repository.update(excursion);
            }
            finish();
            return true;
        }
        if (id == R.id.excursionupdate) {
            editName.requestFocus();
        }
        if (id == R.id.excursiondelete) {
            Excursion excursion = new Excursion(excursionId, editName.getText().toString(), editHotel.getText().toString(), vacationId, editDate.getText().toString());
            repository.delete(excursion);
            Toast.makeText(ExcursionDetails.this, name + "Excursion was deleted", Toast.LENGTH_LONG).show();
            finish();
            return true;
        }

        if (id == R.id.share) {
            String name = editName.getText().toString();
            String hotel = editHotel.getText().toString();
            String date = editDate.getText().toString();
            String note = editNote.getText().toString();




            String shareBody = "Excursion Details:\n" +
                    "name: " + name + "\n" +
                    "date: " + date + " \n " +
                    "hotel: " + hotel + "\n" +
                    "note: " + note;


            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Share Excursion Details");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (id == R.id.notify) {
            try {
                Date myDate = sdf.parse(editDate.getText().toString());
                if (myDate != null) {
                    long trigger = myDate.getTime();
                    Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                    intent.putExtra("key", "Starting Excursion: " + name);


                    PendingIntent sender = PendingIntent.getBroadcast(this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
