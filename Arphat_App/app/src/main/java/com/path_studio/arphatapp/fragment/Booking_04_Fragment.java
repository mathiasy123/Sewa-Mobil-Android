package com.path_studio.arphatapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.path_studio.arphatapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking_04_Fragment extends Fragment implements View.OnClickListener{

    private TextView mPrev, mNext;
    private Button mSet1, mSet2;
    private EditText mDate1, mDate2, mTime1, mTime2;

    private String takeOff_date, return_date;
    private String takeOff_time, return_time;
    private SharedPreferences mSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_04_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSettings = getActivity().getSharedPreferences("Booking_data", Context.MODE_PRIVATE);

        mDate1 = view.findViewById(R.id.date_takeoff);
        mDate2 = view.findViewById(R.id.date_return);

        mTime1 = view.findViewById(R.id.time_takoff);
        mTime2 = view.findViewById(R.id.time_return);

        mNext = (TextView) view.findViewById(R.id.next_booking_05);
        mNext.setOnClickListener(this);

        mPrev = (TextView) view.findViewById(R.id.prev_booking_03);
        mPrev.setOnClickListener(this);

        mSet1 = view.findViewById(R.id.set_takeoff_datetime);
        mSet1.setOnClickListener(this);

        mSet2 = view.findViewById(R.id.set_Return_datetime);
        mSet2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.next_booking_05:
                ft.replace(R.id.fragment_container_booking, new Booking_05_Fragment(), "Next to Page 5 Booking");
                ft.commit();
                break;
            case R.id.prev_booking_03:
                ft.replace(R.id.fragment_container_booking, new Booking_03_Fragment(), "Back to Page 3 Booking");
                ft.commit();
                break;
            case R.id.set_takeoff_datetime:
                //calendar dan time picker
                new SingleDateAndTimePickerDialog.Builder(getActivity())
                        //.bottomSheet()
                        //.curved()
                        .mustBeOnFuture()
                        .minutesStep(5)
                        //.displayHours(false)
                        //.displayMinutes(false)
                        //.todayText("aujourd'hui")
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                //retrieve the SingleDateAndTimePicker
                            }
                        })

                        .title("Take Off Date & Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                final String OLD_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
                                final String NEW_FORMAT = "yyyy-MM-dd";

                                // old = Sat Aug 31 21:20:00 GMT+07:00 2019
                                String oldDateString = date.toString();

                                SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
                                Date d = null;
                                try {
                                    d = sdf.parse(oldDateString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                sdf.applyPattern(NEW_FORMAT);
                                takeOff_date = sdf.format(d);

                                //format jam -------------------------------------------------------

                                final String NEW_FORMAT_TIME = "HH:mm";

                                sdf = new SimpleDateFormat(OLD_FORMAT);
                                Date datetime = null;
                                try {
                                    datetime = sdf.parse(oldDateString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                sdf.applyPattern(NEW_FORMAT_TIME);
                                takeOff_time = sdf.format(d);

                                //simpan di sharepref
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("takeOff_Date", takeOff_date);
                                editor.putString("takeOff_time", takeOff_time);
                                editor.apply();

                                //tampilkan di form
                                mDate1.setText(takeOff_date);
                                mTime1.setText(takeOff_time);

                                Log.e("Pilihan Tanggal Pengembalian = ", return_date + " Jam = "+return_time);

                            }
                        }).display();
                break;
            case R.id.set_Return_datetime:
                //calendar dan time picker
                new SingleDateAndTimePickerDialog.Builder(getActivity())
                        //.bottomSheet()
                        //.curved()
                        .mustBeOnFuture()
                        .minutesStep(5)
                        //.displayHours(false)
                        //.displayMinutes(false)
                        //.todayText("aujourd'hui")
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                //retrieve the SingleDateAndTimePicker
                            }
                        })

                        .title("Return Date & Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {

                                final String OLD_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
                                final String NEW_FORMAT = "yyyy-MM-dd";

                                // old = Sat Aug 31 21:20:00 GMT+07:00 2019
                                String oldDateString = date.toString();

                                SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
                                Date d = null;
                                try {
                                    d = sdf.parse(oldDateString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                sdf.applyPattern(NEW_FORMAT);
                                return_date = sdf.format(d);

                                //format jam -------------------------------------------------------

                                final String NEW_FORMAT_TIME = "HH:mm";

                                sdf = new SimpleDateFormat(OLD_FORMAT);
                                Date datetime = null;
                                try {
                                    datetime = sdf.parse(oldDateString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                sdf.applyPattern(NEW_FORMAT_TIME);
                                return_time = sdf.format(d);

                                //simpan di sharepref
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("return_Date", return_date);
                                editor.putString("return_time", return_time);
                                editor.apply();

                                //tampilkan di form
                                mDate2.setText(return_date);
                                mTime2.setText(return_time);

                                Log.e("Pilihan Tanggal Pengembalian = ", return_date + " Jam = "+return_time);

                            }
                        }).display();
                break;
        }
    }

}