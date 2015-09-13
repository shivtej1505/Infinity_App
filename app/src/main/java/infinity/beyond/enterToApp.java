package infinity.beyond;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class enterToApp extends Activity {

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_to_app);

        editor = getSharedPreferences("beyond",MODE_PRIVATE).edit();
        editor.putString(shortcut.tokens.APP_ID,"520");
        editor.putString(shortcut.tokens.TOKEN_ID,"3088686");
        editor.putString(shortcut.tokens.TRENDING,"4db0cf0c1edfa3cc2694b16576b0019013007348");
        editor.putString(shortcut.tokens.POSTAD,"7fb12f6936ded6d304c534cfe27b510edaa5a696");
        editor.commit();

        final Button entry = (Button) findViewById(R.id.enter);
        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new loginUser().execute();
                Intent jmp = new Intent(enterToApp.this,MainActivity.class);
                startActivity(jmp);
            }
        });
    }

    private class loginUser extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            SQLiteDatabase db = new dbHelper(enterToApp.this).getWritableDatabase();
            new dbHelper(enterToApp.this).onCreate(db);

            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_ID,272);
            values.put(dbHelper.COLUMN_TYPE,"offer");
            values.put(dbHelper.COLUMN_TITLE,"There is no title");
            values.put(dbHelper.COLUMN_DES,"There is no description");
            values.put(dbHelper.COLUMN_DATE, "1995-05-15");

            long success = db.insert(dbHelper.TABLE_NAME,null,values);
            Log.i(shortcut.TAG,""+success);
            values.clear();
            db.close();
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_to_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
