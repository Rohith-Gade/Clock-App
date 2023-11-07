package com.example.clock;

import java.io.Serializable;

public class Song implements Serializable {
    String songName;
    int songAudio;

    public Song(String songName, int songAudio) {
        this.songName = songName;
        this.songAudio = songAudio;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongAudio() {
        return songAudio;
    }

    public void setSongAudio(int songAudio) {
        this.songAudio = songAudio;
    }
}

