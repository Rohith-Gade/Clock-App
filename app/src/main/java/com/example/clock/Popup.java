package com.example.clock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class Popup extends DialogFragment {
    ArrayList<String> languages;
    ArrayList<String> selectedLanguages;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        languages = new ArrayList<>();
        selectedLanguages=new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Weekdays");
        String[] weekdayArray = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        builder.setMultiChoiceItems(weekdayArray, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    languages.add(String.valueOf(i));
                }else if(languages.contains(i)){
                    languages.remove(i);
                }
            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("selected items : " + languages);
                for (int j = 0; j < languages.size(); j++) {
                    String index = languages.get(j);
                    int intIndex = Integer.parseInt(index);
                    if (intIndex >= 0 && intIndex < weekdayArray.length) {
                        String value = weekdayArray[intIndex];
                        // Check if the value is not already in the selectedlanguages list
                        if (!selectedLanguages.contains(value)) {
                            selectedLanguages.add(value);
                        }
                        System.out.println("selected value is : " + value);
                    }
                }
                System.out.println("Value arraylist : " + selectedLanguages);
                Alaramsetting.onOkButtonClicked(selectedLanguages);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}


