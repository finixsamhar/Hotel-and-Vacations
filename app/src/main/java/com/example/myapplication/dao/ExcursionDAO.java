package com.example.myapplication.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.OnConflictStrategy;
import com.example.myapplication.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);
    @Update
    void update(Excursion excursion);
    @Delete
    void delete(Excursion excursion);
    @Query("SELECT * FROM excursions ORDER BY excursionId ASC")
    List<Excursion>getAllExcursions();
    @Query("SELECT * FROM excursions WHERE vacationId = :vacationId ORDER BY excursionId ASC")
    List<Excursion> getAssociatedExcursions(int vacationId);


}
