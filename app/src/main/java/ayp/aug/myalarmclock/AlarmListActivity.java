package ayp.aug.myalarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmListActivity extends SingleFragment
        implements AlarmListFragment.Callbacks{

    protected static final String ALARM_ID = "AlarmActivity.ALARM_ID";

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
