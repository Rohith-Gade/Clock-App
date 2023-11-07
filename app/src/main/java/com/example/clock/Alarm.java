package com.example.clock;

import java.io.Serializable;
public class Alarm  implements Serializable{
    private int id;
    private String time;

    public Alarm() {
    }

    private String days;
    private String song;
    private int status;

    public Alarm(int id, String time, String days, String song, int status) {
        this.id = id;
        this.time = time;
        this.days = days;
        this.song = song;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", days='" + days + '\'' +
                ", song='" + song + '\'' +
                ", status=" + status +
                '}';
    }
}

