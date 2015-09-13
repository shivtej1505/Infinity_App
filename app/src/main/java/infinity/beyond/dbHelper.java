package infinity.beyond;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 13/9/15.
 */
public class dbHelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "adsByUser";
    final static String COLUMN_ID = "itemId";
    final static String COLUMN_TYPE = "itemType";
    final static String COLUMN_TITLE = "itemTitle";
    final static String COLUMN_DES = "itemDes";
    final static String COLUMN_DATE = "itemPostedOn";

    final static Integer VERSION = 1;
    final static String dbName = "userDB";

    final private static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " ( " + COLUMN_ID + " INTEGER NOT NULL " + ", " +
            COLUMN_TYPE + " TEXT NOT NULL " + ", " +
            COLUMN_TITLE + " TEXT NOT NULL " + ", " +
            COLUMN_DES + " DATE NOT NULL " + ", " +
            COLUMN_DATE + " DATE "  + ");" ;

    final private static String DROP_TABLE = "DROP TABLE IF EXISTS " +  TABLE_NAME + " ;";
    final private Context mContext;

    public static String[] columns = {COLUMN_ID,COLUMN_TYPE,COLUMN_TITLE,COLUMN_DES,
            COLUMN_DATE};


    public dbHelper(Context context) {
        super(context, dbName, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            db.execSQL(CREATE_TABLE);
    }
}
