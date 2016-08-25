package ayp.aug.myalarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class AlarmActivity extends SingleFragment implements AlarmFragment.Callbacks{

    protected static final String ALARM_ID = "ALARM_ID";

    @Override
    protected Fragment onCreateFragment() {
        UUID alarmID = (UUID) getIntent().getSerializableExtra(ALARM_ID);
        Fragment fragment = AlarmFragment.newInstance(alarmID);
        return fragment;
    }

    public static Intent newIntent(Context activity, UUID id) {
        Intent intent = new Intent(activity, AlarmActivity.class);
        intent.putExtra(ALARM_ID, id);
        return intent;
    }

    public static Intent newIntent(Context activity) {
        Intent intent = new Intent(activity, AlarmActivity.class);
        return intent;
    }

    @Override
    public void onAlarmUpdated(AlarmClock alarmClock) {

    }
}
