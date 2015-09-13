package infinity.beyond;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
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
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class addNew extends Activity {

    String cate,type,tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        Button submit = (Button) findViewById(R.id.submit);
        final EditText itemTitle = (EditText) findViewById(R.id.title);
        final EditText itemDes = (EditText) findViewById(R.id.description);
        Spinner category = (Spinner) findViewById(R.id.category);
        Spinner adType = (Spinner) findViewById(R.id.ad_type);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.category,R.layout.listitem);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.adType,R.layout.listitem);
        category.setAdapter(adapter1);
        adType.setAdapter(adapter2);


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cate = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = "{ \n" +
                        "\"email\": \"shiv@nag.com\",\n" +
                        "\"remoteAddr\": \"192.168.51.57\",\n" +
                        "\"subCategory\": \"" + cate + "\",\n" +
                        "\"cityName\": \"Hyderabad\",\n" +
                        "\"title\": \"" + itemTitle.getText().toString() + "\",\n" +
                        "\"description\": \"" + itemDes.getText().toString() +"\",\n" +
                        "\"locations\": \"Gachibowli\",\n" +
                        "\"attributes\":\n" +
                        "{ \"Ad_Type\": \"" + type + "\", \"You_are\": \"Dealer\", \"Kms_Driven\": \"272\", \"Condition\": \"Used\", \"Year\": \"2015\", \"Brand_name\": \"Honda\", \"Model\": \"City\" }\n" +
                        "}";

                new itemPost().execute();
            }
        });
    }

    private class itemPost  extends AsyncTask<Void,Void,String> {

        SharedPreferences preferences = getSharedPreferences("beyond", MODE_PRIVATE);

        private static final String URL = "https://api.quikr.com/public/postAds";

        //DefaultHttpClient client = new DefaultHttpClient();
        @Override
        protected String  doInBackground(Void... voids) {
            HttpPost post = new HttpPost(URL);

            post.setHeader("X-Quikr-App-Id", preferences.getString(shortcut.tokens.APP_ID, "520"));
            post.setHeader("X-Quikr-Token-Id", preferences.getString(shortcut.tokens.TOKEN_ID, "3088686"));
            post.setHeader("X-Quikr-Signature", preferences.getString(shortcut.tokens.POSTAD, "7fb12f6936ded6d304c534cfe27b510edaa5a696"));

            post.addHeader("Content-Type", "application/json");


            Log.i(shortcut.TAG,tmp);
            int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpClient client = new DefaultHttpClient(httpParams);

            try {
                post.setEntity(new ByteArrayEntity(
                        tmp.getBytes("UTF8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                Log.i("Response", EntityUtils.toString(entity));
                Log.i("Response",httpResponse.getStatusLine().getStatusCode()+"");
                return httpResponse.getStatusLine().toString();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Log.i("Response",s);
                Log.i("Infinity","Success");
                new AlertDialog.Builder(addNew.this).setMessage(s).create().show();
            } else {
                Log.i("Infinity","failed");
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new, menu);
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
