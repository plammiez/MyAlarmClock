package ayp.aug.myalarmclock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.UUID;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmFragment extends Fragment {

    private static final String ALARM_ID = "ALARM_ID";
    private static final int REQUEST_DELTE = 1;
    private static final String DIALOG_DELETE = "DELETE";

    private AlarmClock alarmClock;
    private TimePicker timePicker;
    private Button button_del;

    private Callbacks callbacks;

    //Callback
    public interface Callbacks {
        void onAlarmUpdated(AlarmClock alarmClock);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    public static AlarmFragment newInstance(UUID alarmId) {

        Bundle args = new Bundle();
        args.putSerializable(ALARM_ID, alarmId);
        AlarmFragment fragment = new AlarmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlarmLab alarmLab = AlarmLab.getInstance(getActivity());

        if(getArguments().get(ALARM_ID) != null) {
            UUID alarmId = (UUID) getArguments().getSerializable(ALARM_ID);
            alarmClock = AlarmLab.getInstance(getActivity()).getAlarmByID(alarmId);
            Log.d(AlarmListFragment.TAG, " alarm.getId()=" + alarmClock.getId());
        }else {
            //== null
            AlarmClock alarmClock = new AlarmClock();
            alarmLab.addAlarm(alarmClock);
            this.alarmClock = alarmClock;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarm_fragment, container, false);

        timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        alarmClock.setHour(timePicker.getCurrentHour());
        alarmClock.setMinute(timePicker.getCurrentMinute());
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                alarmClock.setHour(timePicker.getCurrentHour());
                alarmClock.setMinute(timePicker.getCurrentMinute());
                updateAlarm();
            }
        });
        updateAlarm();

//        button_del = (Button) v.findViewById(R.id.button_del);
//        button_del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                DeleteFragment deleteFragment = DeleteFragment.newInstance(alarmClock.getId());
//                deleteFragment.setTargetFragment(AlarmFragment.this, REQUEST_DELTE);
//
//                deleteFragment.show(fm, DIALOG_DELETE);
//            }
//        });
        updateAlarm();
        return v;
    }

    public void updateAlarm(){
        AlarmLab.getInstance(getActivity()).updateAlarm(alarmClock);
    }

}
