package com.startup.edy.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by Justin on 6/27/2015.
 */
public class TimePickerFragment extends DialogFragment{
    public static final String EXTRA_TIME =
            "com.bignerdranch.android.criminalintent.date";
    private Date mTime;

    public static TimePickerFragment newInstance(Date time){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mTime = (Date)getArguments().getSerializable(EXTRA_TIME);

        // Create a Calendar to get the hours and minutes
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);


        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_time, null);

        //Can be an error from here
        //DatePicker timePicker = (DatePicker)v.findViewById(R.id.time);
        TimePicker timePicker = (TimePicker)v.findViewById(R.id.time);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);


        // Use a calendar object to get the month, day, and year
        final int mYear = calendar.get(Calendar.YEAR);
        final int mMonth = calendar.get(Calendar.MONTH);
        final int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // Translate to a Date object using a Calendar
                mTime = new GregorianCalendar(mYear,mMonth,mDay,hourOfDay,minute).getTime();
                getArguments().putSerializable(EXTRA_TIME, mTime);
            }});



       /* timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
           *//* public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mTime.setHours(hour);
                mTime.setMinutes(minute);
            }*//*
            public void onDateChanged(DatePicker view, int hour, int minute) {
                // Translate year, month, day into a Date object using a calendar
                mTime = new GregorianCalendar(hour, minute).getTime();
                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_TIME, mTime);
            }
        });*/
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }



}
