package com.example.myapplication.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.ExcursionDAO;
import com.example.myapplication.dao.VacationDAO;
import com.example.myapplication.entities.Excursion;
import com.example.myapplication.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 14, exportSchema = false)
public abstract class HotelDatadaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile HotelDatadaseBuilder INSTANCE;
    static HotelDatadaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HotelDatadaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HotelDatadaseBuilder.class, "hotel_database.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }

        }
        return INSTANCE;
    }

}
