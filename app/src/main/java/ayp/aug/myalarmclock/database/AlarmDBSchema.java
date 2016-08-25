package ayp.aug.myalarmclock.database;

/**
 * Created by Waraporn on 8/24/2016.
 */
public class AlarmDBSchema {

    public static final class AlarmTable{

        public static final String NAME = "alarms";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String HOUR = "hour";
            public static final String MINUTE = "minute";
        }

    }
}
