package com.mysticwater.thewitcher3bestiary;

import android.provider.BaseColumns;

public final class BeastsContract {

    public BeastsContract() {}

    public static abstract class BeastEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "beasts";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_BEAST = "beast";
        public static final String COLUMN_NAME_VULNERABILITIES = "vulnerabilities";
    }
}
