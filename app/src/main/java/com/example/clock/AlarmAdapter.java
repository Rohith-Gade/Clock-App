package com.example.clock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private Context context;
    private ArrayList<Alarm> alarmArrayList;
    private DatabaseHelper dbHelper;
    int currentPosition;
    public AlarmAdapter(Context context, ArrayList<Alarm> alarmArrayList, DatabaseHelper dbHelper) {
        this.context = context;
        this.alarmArrayList = alarmArrayList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alarmlistitem, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.AlarmViewHolder holder, int position) {
        Alarm alarmData = alarmArrayList.get(position);
        holder.bind(alarmData);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                currentPosition = holder.getAdapterPosition();
                popupMenu.getMenuInflater().inflate(R.menu.main2,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.delete){
                            deleteAlarm();
                        }
                        return true;
                    }

                    private void deleteAlarm() {
                        dbHelper.deleteAlarm(alarmArrayList.get(currentPosition).getTime());
                        alarmArrayList.remove(currentPosition);
                        notifyItemRemoved(currentPosition);

                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarmArrayList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTextView;
        private TextView daysTextView;
        private TextView songTextView;
        private Switch switchStatus;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time);
            daysTextView = itemView.findViewById(R.id.days);
            songTextView = itemView.findViewById(R.id.song);
            switchStatus = itemView.findViewById(R.id.switch_status);

            switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int status = isChecked ? 1 : 0;
                    int position = getAdapterPosition();
                    Alarm alarm = alarmArrayList.get(position);
                    alarm.setStatus(status);
                    dbHelper.updateAlarmStatus(alarm);
                }
            });
        }

        public void bind(Alarm alarmData) {
            timeTextView.setText(alarmData.getTime());
            daysTextView.setText(alarmData.getDays());
            songTextView.setText(alarmData.getSong());
            int status = alarmData.getStatus();
            switchStatus.setChecked(status == 1);
        }
    }

    public void setData(ArrayList<Alarm> alarms) {
        alarmArrayList.clear();
        alarmArrayList.addAll(alarms);
        notifyDataSetChanged();
    }
}
