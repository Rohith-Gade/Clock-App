package com.example.clock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song>  {
    MediaPlayer mediaPlayer;
    Context context;
    private static Song selectedSong;
    private SongSelectedListener songSelectedListener;
    ArrayList<Song> songArrayList;

    public SongAdapter(Context context, ArrayList<Song> songArrayList, SongSelectedListener listener) {
        super(context, 0, songArrayList);
        this.context = context;
        this.songArrayList = songArrayList;
        this.songSelectedListener = listener;
    }

    @NonNull
    @Override
    @SuppressLint({"ViewHolder", "MissingInflatedId"})
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View list_item = layoutInflater.inflate(R.layout.song_list_item, null);
        TextView songName = list_item.findViewById(R.id.songname_tv);
        ImageButton songAudio = list_item.findViewById(R.id.songAudio_ib);
        Song songPosition = songArrayList.get(position);

        songName.setText(songPosition.getSongName());

        songAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(context, songPosition.getSongAudio());
                mediaPlayer.start();

                // Set the selected song
                selectedSong = songPosition;
                songSelectedListener.onSongSelected(selectedSong);
            }
        });

        return list_item;
    }
/*
    public static Song getSelectedSong() {
        return selectedSong;
    }*/

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public interface SongSelectedListener {
        void onSongSelected(Song song);
    }
}
