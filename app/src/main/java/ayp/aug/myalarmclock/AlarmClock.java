package ayp.aug.myalarmclock;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmClock {

    private static final String TAG = "AlarmClock";

    private UUID id;
    private Date alarmDate;
    private int hour;
    private int minute;
    private boolean isAlarmOn;

    public AlarmClock() {
        this(UUID.randomUUID());
        alarmDate  = new Date();
    }

    public AlarmClock(UUID uuid) {
        this.id = uuid;
        alarmDate  = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public boolean getAlarmOn() {
        return isAlarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        isAlarmOn = alarmOn;
    }

}
