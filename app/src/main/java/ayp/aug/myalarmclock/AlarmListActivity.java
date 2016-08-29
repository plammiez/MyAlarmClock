package ayp.aug.myalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmListActivity extends SingleFragment
        implements AlarmListFragment.Callbacks{

    protected static final String ALARM_ID = "AlarmActivity.ALARM_ID";

    TimePicker alarmTimePicker;
    Switch button_switch;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        button_switch = (Switch) findViewById(R.id.button_switch);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    public void OnSwitchClicked(AlarmClock alarmClock) {

        long time;

        if (alarmClock.getAlarmOn() == true)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
            if(System.currentTimeMillis()>time)
            {
                if (calendar.AM_PM == 0)
                    time = time + (1000*60*60*12);
                else
                    time = time + (1000*60*60*24);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
        }
        else
        {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(AlarmListActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Fragment onCreateFragment() {
        return new AlarmListFragment();
    }

    public static Intent newIntent(Context activity, UUID id) {
        Intent intent = new Intent(activity, AlarmActivity.class);
        intent.putExtra(ALARM_ID, id);
        return intent;
    }

    @Override
    public void onAlarmSelected(AlarmClock alarmClock) {
        //single pane
        Intent intent = AlarmActivity.newIntent(this, alarmClock.getId());
        startActivity(intent);
    }

    @Override
    public void onContactUpdated(AlarmClock alarmClock) {
        //Update list
        AlarmListFragment listFragment = (AlarmListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
