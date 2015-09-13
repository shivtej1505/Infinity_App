package infinity.beyond;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Toast;

public class enterToApp extends Activity {

    String entredPass,hintQues,hintAns;
    EditText entryPass;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_to_app);

        final SharedPreferences preferences = getSharedPreferences(shortcut.prefs.PREFS_NAME,MODE_PRIVATE);
        final String userPass = preferences.getString(shortcut.prefs.PASS, "password");

        editor = preferences.edit();
        editor.putString(shortcut.tokens.APP_ID,"520");
        editor.putString(shortcut.tokens.TOKEN_ID,"3088686");
        editor.putString(shortcut.tokens.TRENDING,"4db0cf0c1edfa3cc2694b16576b0019013007348");
        editor.putString(shortcut.tokens.POSTAD,"7fb12f6936ded6d304c534cfe27b510edaa5a696");
        editor.commit();




        entryPass = (EditText) findViewById(R.id.entryPass);
        Button entryBtn = (Button) findViewById(R.id.enterBtn);
        Button forgetBtn = (Button) findViewById(R.id.forgetBtn);

        entryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entredPass = entryPass.getText().toString();
                Log.i(shortcut.TAG,entredPass);
                Log.i(shortcut.TAG,userPass);
                if(entredPass.equals(userPass)) {
                    Intent jmpToDocs = new Intent(enterToApp.this,MainActivity.class);
                    startActivity(jmpToDocs);
                } else {
                    Toast.makeText(enterToApp.this,"Wrong password!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintQues = preferences.getString(shortcut.prefs.HINT_QUES,"What is your favorite color?");
                hintAns = preferences.getString(shortcut.prefs.HINT_ANS,"blue");

                final EditText hintPut = new EditText(enterToApp.this);
                hintPut.setHint(R.string.putHint);
                hintPut.setSingleLine(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(enterToApp.this);
                builder.setTitle(R.string.ansQues).setMessage(hintQues).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // TODO: check for hint answer and then pop up appropriate dialog box.
                        String hAnsFill = hintPut.getText().toString();
                        Log.i(shortcut.TAG,hAnsFill);
                        Log.i(shortcut.TAG,hintAns);
                        if (hAnsFill.equals(hintAns)) {
                            // New dialog box showing password

                            AlertDialog.Builder myBuilder = new AlertDialog.Builder(enterToApp.this);
                            myBuilder.setTitle("Your password").setMessage("Your password: "+userPass).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            myBuilder.create();
                            myBuilder.show();
                        } else {
                            Toast.makeText(enterToApp.this,"Wrong answer!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setView(hintPut);
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        entryPass.setText(null);
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
