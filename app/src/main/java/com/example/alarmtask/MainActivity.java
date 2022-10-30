package com.example.alarmtask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    Date currentAlarmTime;

    int requestCode = 0;
    String actualAlarmTime = "";
    int alarmInterval = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textAlarmPrompt = (TextView) findViewById(R.id.alarmprompt);
        buttonstartSetDialog = (Button) findViewById(R.id.startAlaram);
    }

    public void onClickSetAlarmButton(View view) {
        if(currentAlarmTime == null){
            currentAlarmTime =  Calendar.getInstance().getTime();

        } else {
            currentAlarmTime = new Date(currentAlarmTime.getTime() + (alarmInterval * 60000));
        }
        Log.i("msg", "Alarm: " + currentAlarmTime);
        textAlarmPrompt.setText("");
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentAlarmTime);
        cal.add(Calendar.MINUTE, alarmInterval);
        setAlarm(cal);
    }

    private void setAlarm(Calendar targetCal) {
        actualAlarmTime = actualAlarmTime + "\n" + targetCal.getTime();
        requestCode = requestCode + 1;
        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set for-"
                + actualAlarmTime + "\n" + "***\n");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }
}
