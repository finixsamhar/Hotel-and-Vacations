package com.example.myapplication.database;
import android.app.Application;

import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.entities.User;
import com.example.myapplication.entities.Vacation;
import com.example.myapplication.entities.Excursion;
import com.example.myapplication.dao.ExcursionDAO;
import com.example.myapplication.dao.VacationDAO;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import androidx.lifecycle.LiveData;

public class Repository {
    private UserDAO mUserDao;
    private ExcursionDAO mExcursionsDao;
    private VacationDAO mVacationsDao;
    private List<Excursion> mAllExcursions;
    private List<Vacation> mAllVacations;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public Repository(Application application) {
        HotelDatadaseBuilder db = HotelDatadaseBuilder.getDatabase(application);
        mUserDao = db.userDAO();
        mExcursionsDao = db.excursionDAO();
        mVacationsDao = db.vacationDAO();
    }
    public User login(String username, String password){
        return mUserDao.login(username, password);
    }

    public List<Vacation> getAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationsDao.getAllVacations();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVacations;

    }
    public void insert(User user){
        databaseExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }
    public void insert(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationsDao.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationsDao.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationsDao.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public List<Excursion> getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionsDao.getAllExcursions();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;

    }
    public List<Excursion> getAssociatedExcursions(int vacationId) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionsDao.getAssociatedExcursions(vacationId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;

    }
    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionsDao.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionsDao.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionsDao.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public List<Vacation> getFilteredVacations(String search) {
        return mVacationsDao.getFilteredVacations("%" + search + "%");
    }

}
