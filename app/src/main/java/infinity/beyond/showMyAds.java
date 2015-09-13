package infinity.beyond;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONException;

public class showMyAds extends Activity {

    ProgressDialog progressDialog;
    ListView myAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_ads);
        myAds = (ListView) findViewById(R.id.myads);
        new loadData().execute();
    }

    private class loadData extends AsyncTask<Void,Void,dbAdapter> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(showMyAds.this);
            progressDialog.setMessage("Reading database");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected dbAdapter doInBackground(Void... params) {

            dbAdapter adapter = new dbAdapter(showMyAds.this);
            dbHelper myHelper = new dbHelper(showMyAds.this);
            SQLiteDatabase db = myHelper.getReadableDatabase();
            String[] projection = dbHelper.columns;
            Cursor readData = db.query(
                    myHelper.TABLE_NAME, projection,null, null, null, null, null);
            ItemData data = null;
            try {
                data = new ItemData(null,null,null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (readData.moveToFirst()) {
                do {
                    data.setItemId(readData.getInt(readData.getColumnIndex(myHelper.COLUMN_ID)));
                    data.setItemType(readData.getString(readData.getColumnIndex(myHelper.COLUMN_TYPE)));
                    data.setItemTitle(readData.getString(readData.getColumnIndex(myHelper.COLUMN_TITLE)));
                    data.setItemDes(readData.getString(readData.getColumnIndex(myHelper.COLUMN_DES)));
                    adapter.add(data);
                } while (readData.moveToNext());
            }
            readData.close();
            return adapter;
        }

        @Override
        protected void onPostExecute(dbAdapter adapter) {
            myAds.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_my_ads, menu);
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
