package ayp.aug.myalarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ayp.aug.myalarmclock.database.AlarmBaseHelper;
import ayp.aug.myalarmclock.database.AlarmCursorWrapper;
import ayp.aug.myalarmclock.database.AlarmDBSchema.AlarmTable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmLab {

    private static final String TAG = "CrimeLab";
    /////////////////////////STATIC ZONE/////////////////////////////////
    private static AlarmLab instance;


    public static AlarmLab getInstance(Context context){

        if(instance == null){
            instance = new AlarmLab(context);
        }
        return instance;
    }

    public static ContentValues getContentValues(AlarmClock alarmClock){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmTable.Cols.UUID, alarmClock.getId().toString());
        contentValues.put(AlarmTable.Cols.DATE, alarmClock.getAlarmDate().getTime());
        contentValues.put(AlarmTable.Cols.HOUR, alarmClock.getHour());
        contentValues.put(AlarmTable.Cols.MINUTE, alarmClock.getMinute());
        return contentValues;
    }
////////////////////////////////////////////////////////////////////

    private Context context;
    private SQLiteDatabase database;

    private AlarmLab(Context context){
        this.context = context;
        AlarmBaseHelper alarmBaseHelper = new AlarmBaseHelper(context);
        database = alarmBaseHelper.getWritableDatabase();

    }

    public  AlarmClock getAlarmByID(UUID uuid){
        AlarmCursorWrapper cursor = queryCrimes(AlarmTable.Cols.UUID
                + " = ? ", new String[] { uuid.toString()});

        try{
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getAlarm();
        }finally {
            cursor.close();
        }
    }

    public AlarmCursorWrapper queryCrimes(String whereCause, String[] whereArgs){
        Cursor cursor = database.query(AlarmTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null);

        return new AlarmCursorWrapper(cursor);
    }

    public List<AlarmClock> getAlarms(){
        List<AlarmClock> alarmClocks = new ArrayList<>();

        AlarmCursorWrapper cursorWrapper = queryCrimes(null, null);
        try {
            cursorWrapper.moveToFirst();
            while ( !cursorWrapper.isAfterLast()){
                alarmClocks.add(cursorWrapper.getAlarm());

                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return alarmClocks;
    }

    public static void main(String [] args){
        AlarmLab alarmLab = AlarmLab.getInstance(null);
        List<AlarmClock> alarmClockList = alarmLab.getAlarms();
        int size = alarmClockList.size();
        for(int i = 0; i < size; i++){
            System.out.println(alarmClockList.get(i));
        }
    }

    public void addAlarm(AlarmClock alarmClock) {
        Log.d(TAG, "Add crime " + alarmClock.toString());
        ContentValues contentValues = getContentValues(alarmClock);

        database.insert(AlarmTable.NAME, null, contentValues);
    }

    public void deleteAlarm(UUID uuid) {
        database.delete(AlarmTable.NAME, AlarmTable.Cols.UUID
                + " = ? ", new String[] {uuid.toString() });
    }

    public void updateAlarm(AlarmClock alarmClock){
        String uuidStr = alarmClock.getId().toString();
        ContentValues contentValues = getContentValues(alarmClock);

        database.update(AlarmTable.NAME, contentValues, AlarmTable.Cols.UUID
                + " = ?", new String[] { uuidStr}); // uuidStr will manage n put in ? position (sql injection)
    }
}
