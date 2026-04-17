package com.example.myapplication.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.myapplication.entities.User;
@Dao
public interface UserDAO {
    @Insert
    void insert (User user);
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);
}
