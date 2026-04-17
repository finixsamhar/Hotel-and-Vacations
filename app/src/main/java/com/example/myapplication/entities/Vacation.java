package com.example.myapplication.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;


@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationId;
    private String vacationName;
    private String hotel;
    private String startDate;
    private String endDate;

    public Vacation(int vacationId, String vacationName, String hotel, String startDate, String endDate) {
        this.vacationId = vacationId;
        this.vacationName = vacationName;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getVacationId() {
        return vacationId;
    }
    public String toString(){
        return vacationName;
    }


    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
