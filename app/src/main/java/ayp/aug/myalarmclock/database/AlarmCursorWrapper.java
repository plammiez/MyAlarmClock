package ayp.aug.myalarmclock.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import ayp.aug.myalarmclock.AlarmClock;
import ayp.aug.myalarmclock.database.AlarmDBSchema.AlarmTable;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public AlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public AlarmClock getAlarm(){

        String uuidString = getString(getColumnIndex(AlarmTable.Cols.UUID));
        long date = getLong(getColumnIndex(AlarmTable.Cols.DATE));
        int hour = getInt(getColumnIndex(AlarmTable.Cols.HOUR));
        int minute = getInt(getColumnIndex(AlarmTable.Cols.MINUTE));

        AlarmClock alarmClock = new AlarmClock(UUID.fromString(uuidString));
        alarmClock.setAlarmDate(new Date(date));
        alarmClock.setHour(hour);
        alarmClock.setMinute(minute);

        return alarmClock;
    }
}
