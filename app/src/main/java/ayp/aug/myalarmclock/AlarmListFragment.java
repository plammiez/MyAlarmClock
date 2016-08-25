package ayp.aug.myalarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmListFragment extends Fragment {

    protected static final String TAG = "AlarmListFragment";
    private static final int REQUEST_UPDATED_CONTACT = 1;

    private RecyclerView alarm_recycle_view;
    private AlarmAdapter _adapter;
    private Integer[] alarmPos;

    private Callbacks callbacks;

    public interface Callbacks {
        void onAlarmSelected(AlarmClock alarmClock);
        void onContactUpdated(AlarmClock alarmClock);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarm_fragment_list,container,false);

        alarm_recycle_view = (RecyclerView) v.findViewById(R.id.fragment_list_recycler_view);
        alarm_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_alarm:
                AlarmClock alarmClock = new AlarmClock();
                AlarmLab.getInstance(getActivity()).addAlarm(alarmClock);

//                Intent intent = AlarmListActivity.newIntent(getActivity(), alarmClock.getId());
//                startActivity(intent);

                updateUI();
                callbacks.onAlarmSelected(alarmClock);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_UPDATED_CONTACT){
            if(resultCode == Activity.RESULT_OK) {
                alarmPos = (Integer[]) data.getExtras().get("position");
                Log.d(TAG, "get alarmPos = " + alarmPos);
            }
            //blah blah
            Log.d(TAG, "Return from AlarmFragment");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void updateUI() {

        AlarmLab alarmLab = AlarmLab.getInstance(getActivity());
        List<AlarmClock> alarmClocks = alarmLab.getAlarms();

        int count = alarmLab.getAlarms().size();

        if (_adapter == null) {
            _adapter = new AlarmAdapter(this, alarmClocks);
            alarm_recycle_view.setAdapter(_adapter);
        }else {
            _adapter.setAlarm(alarmLab.getAlarms());
            _adapter.notifyDataSetChanged();
        }

        Log.d(TAG, "COUNT " + count);
    }

    private class AlarmHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleView;
        public Switch button_switch;

        AlarmClock _alarmClock;
        int _position;

        public AlarmHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.title_alarm);
            button_switch = (Switch) itemView.findViewById(R.id.button_switch);

            itemView.setOnClickListener(this);
        }

        public void bind(final AlarmClock alarmClock,int position) {
            callbacks = (Callbacks)getActivity();
            _alarmClock = alarmClock;
            _position = position;
            titleView.setText(_alarmClock.getHour() + " : " + _alarmClock.getMinute());

            AlarmLab.getInstance(getActivity()).updateAlarm(_alarmClock);
        }

        @Override
        public void onClick(View v) {
            callbacks.onAlarmSelected(_alarmClock);
        }
    }
    private class AlarmAdapter extends RecyclerView.Adapter<AlarmHolder> {

        private List<AlarmClock> _alarmClocks;
        private Fragment _f;
        private int _viewCreatingCount;

        public AlarmAdapter(Fragment f, List<AlarmClock> alarmClocks) {
            _f = f;
            _alarmClocks = alarmClocks;
        }

        protected void setAlarm(List<AlarmClock> alarmClocks) {
            _alarmClocks = alarmClocks;
        }

        @Override
        public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            _viewCreatingCount++;
            Log.d(TAG, "Create view holder for CrimeList: creating view time = "+ _viewCreatingCount);

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_alarm_clock, parent,false);

            return new AlarmHolder(v);
        }

        @Override
        public void onBindViewHolder(AlarmHolder holder, int position) {
            AlarmClock alarmClock = _alarmClocks.get(position);
            holder.bind(alarmClock, position);
        }

        @Override
        public int getItemCount() {
            return _alarmClocks.size();
        }
    }
}
