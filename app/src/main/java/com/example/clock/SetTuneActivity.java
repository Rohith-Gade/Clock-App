package com.example.clock;

import static com.example.clock.Alaramsetting.selectedsong;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SetTuneActivity extends AppCompatActivity implements SongAdapter.SongSelectedListener {
    ListView songslist;
    SongAdapter songAdapter;
    private static Song selectedSong;
    ArrayList<Song> songArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tune);
        songslist=findViewById(R.id.songs_lv);

        songArrayList= new ArrayList<>();
        songArrayList.add(new Song("Senorita",R.raw.senorita));
        songArrayList.add(new Song("Inthandam",R.raw.sitaramam));
        songArrayList.add(new Song("Masteru",R.raw.masteru));
        songArrayList.add(new Song("JinthaakChithaka",R.raw.jinthaakchithaka));
        songArrayList.add(new Song("Saami Saami",R.raw.saamisaami));
        songArrayList.add(new Song("Senorita",R.raw.senorita));
        songArrayList.add(new Song("Inthandam",R.raw.sitaramam));
        songArrayList.add(new Song("Masteru",R.raw.masteru));
        songArrayList.add(new Song("JinthaakChithaka",R.raw.jinthaakchithaka));
        songArrayList.add(new Song("Saami Saami",R.raw.saamisaami));
        songArrayList.add(new Song("Senorita",R.raw.senorita));
        songArrayList.add(new Song("Inthandam",R.raw.sitaramam));
        songArrayList.add(new Song("Masteru",R.raw.masteru));
        songArrayList.add(new Song("JinthaakChithaka",R.raw.jinthaakchithaka));
        songArrayList.add(new Song("Saami Saami",R.raw.saamisaami));
        songArrayList.add(new Song("Senorita",R.raw.senorita));
        songArrayList.add(new Song("Inthandam",R.raw.sitaramam));
        songArrayList.add(new Song("Masteru",R.raw.masteru));
        songArrayList.add(new Song("JinthaakChithaka",R.raw.jinthaakchithaka));
        songArrayList.add(new Song("Saami Saami",R.raw.saamisaami));
        songAdapter=new SongAdapter(this,songArrayList,this);
        songslist.setAdapter(songAdapter);
    }

    @Override
    protected void onDestroy() {
        if (songAdapter != null) {
            songAdapter.releaseMediaPlayer();
        }
        super.onDestroy();
    }
    @Override
    public void onSongSelected(Song song) {
        // Do something with the selected song, for example, display it in a TextView
        selectedsong.setText(song.getSongName());
    }
}