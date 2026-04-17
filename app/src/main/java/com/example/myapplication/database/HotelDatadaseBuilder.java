package com.example.myapplication.database;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.ExcursionDAO;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.dao.VacationDAO;
import com.example.myapplication.entities.Excursion;
import com.example.myapplication.entities.User;
import com.example.myapplication.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class, User.class}, version = 18, exportSchema = false)
public abstract class HotelDatadaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    public abstract UserDAO userDAO();
    private static volatile HotelDatadaseBuilder INSTANCE;
    public static HotelDatadaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HotelDatadaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HotelDatadaseBuilder.class, "hotel_database.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(new RoomDatabase.Callback(){
                                @Override
                                public void onCreate(@NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {

                                        super.onCreate(db);
                                        new Thread(() -> {
                                            UserDAO dao = INSTANCE.userDAO();
                                            dao.insert(new User(0, "Admin", "12345"));
                                        }).start();
                                        }

                            })
                            .build();
                }
            }

        }
        return INSTANCE;
    }

}
