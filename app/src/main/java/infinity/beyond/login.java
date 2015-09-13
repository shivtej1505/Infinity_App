package infinity.beyond;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class login extends Activity {

    @Override
    protected void onStart() {

        super.onStart();

        final SharedPreferences preferences = getSharedPreferences(shortcut.prefs.PREFS_NAME,MODE_PRIVATE);
        if(preferences.getBoolean(shortcut.prefs.FIRST_RUN_DONE,false)) {
            Intent jmp = new Intent(login.this,enterToApp.class);
            startActivity(jmp);
            finish();
        }
    }

    String pickQ,ansE,userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // creating reference
        setContentView(R.layout.activity_login);

        final EditText pass = (EditText) findViewById(R.id.pass);
        final EditText passAgain = (EditText) findViewById(R.id.passAgain);
        Spinner spinQ = (Spinner) findViewById(R.id.spinQ);
        final EditText ansQ = (EditText) findViewById(R.id.ansQ);
        Button saveBtn;
        saveBtn = (Button) findViewById(R.id.saveBtn);

        // setting adapter to spinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                login.this,R.array.hintQ,R.layout.listitem);
        spinQ.setAdapter(arrayAdapter);
        spinQ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickQ = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(login.this,"Please select a question",Toast.LENGTH_SHORT).show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansE = ansQ.getText().toString();
                if(!(pass.getText().toString().equals(passAgain.getText().toString()))) {
                    Toast.makeText(login.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                    pass.setText(null);
                    passAgain.setText(null);
                } else if(pass.getText().toString().length() < 8 || passAgain.getText().toString().length() < 8 ) {
                    Toast.makeText(login.this, "Minimum password length 8", Toast.LENGTH_SHORT).show();
                    pass.setText(null);
                    passAgain.setText(null);
                }else if(ansE.equals("")) {
                    Toast.makeText(login.this,"Please provide answer",Toast.LENGTH_SHORT).show();
                } else {
                    // save details in SharedPreferences
                    userPassword = pass.getText().toString();

                    // saving details
                    new backWork().execute();

                    Intent jmpToentry = new Intent(login.this,enterToApp.class);
                    startActivity(jmpToentry);
                }
            }
        });
    }


    private class backWork extends AsyncTask<Void,Void,Void> {

        ProgressDialog mProgressDialog = new ProgressDialog(login.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage(getResources().getString(R.string.savingData));
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            // saving details
            Log.i(shortcut.TAG, pickQ);
            Log.i(shortcut.TAG, ansE);
            Log.i(shortcut.TAG, userPassword);

            SharedPreferences.Editor editor = getSharedPreferences(shortcut.prefs.PREFS_NAME,MODE_PRIVATE).edit();
            editor.putString(shortcut.prefs.HINT_QUES, pickQ);
            editor.putString(shortcut.prefs.HINT_ANS, ansE);
            editor.putString(shortcut.prefs.PASS, userPassword);
            editor.putBoolean(shortcut.prefs.FIRST_RUN_DONE, true);
            editor.commit();


            SQLiteDatabase db = new dbHelper(login.this).getWritableDatabase();
            new dbHelper(login.this).onUpgrade(db,dbHelper.VERSION,dbHelper.VERSION+1);

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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
