package ayp.aug.myalarmclock.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import ayp.aug.myalarmclock.database.AlarmDBSchema.AlarmTable;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "alarmBase.db";
    private static final String TAG = "AlarmBaseHelper";

    public AlarmBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Create Database");

        db.execSQL("create table " + AlarmTable.NAME
                + "("
                + "_id integer primary key autoincrement, "
                + AlarmTable.Cols.UUID + ","
                + AlarmTable.Cols.DATE + ","
                + AlarmTable.Cols.HOUR + ","
                + AlarmTable.Cols.MINUTE + ","
                + AlarmTable.Cols.ISALARMON + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
