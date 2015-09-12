package infinity.beyond;

import android.app.Activity;
import android.app.AlertDialog;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class addNew extends Activity {

    String cate,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        Button submit = (Button) findViewById(R.id.submit);
        EditText itemTitle = (EditText) findViewById(R.id.title);
        EditText itemDes = (EditText) findViewById(R.id.description);
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

        JSONObject attr = new JSONObject();
        try {
            attr.put("Ad_Type","offer");
            attr.put("You_are", "Dealer");
            attr.put("Kms_Driven", "10");
            attr.put("Condition", "Used");
            attr.put("Year", "2015");
            attr.put("Brand_name", "Honda");
            attr.put("Model", "City");
            Log.i("Response",attr.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", "shivang@nagaria.com");
            jsonObject.put("remoteAddr", "14.139.82.6");
            jsonObject.put("subCategory", "cars");
            jsonObject.put("cityName", "Hyderabad");
            jsonObject.put("title", "Hi this is a dummy title  a dummy car for hackathon");
            jsonObject.put("description", "This is a dummy description of atleast 30 characters.");
            jsonObject.put("locations", "Gachibowli");
            jsonObject.put("attributes", attr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Response",jsonObject.toString());
                new itemPost().execute(jsonObject);
            }
        });
    }

    private class itemPost  extends AsyncTask<JSONObject,Void,String> {

        AndroidHttpClient client = AndroidHttpClient.newInstance("");
        private static final String URL = "https://api.quikr.com/public/postAds";
        //DefaultHttpClient client = new DefaultHttpClient();
        @Override
        protected String  doInBackground(JSONObject... jsonObjects) {
            HttpPost post = new HttpPost(URL);

            StringEntity stringEntity = null;
            try {
                stringEntity = new StringEntity(jsonObjects.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            post.setEntity(stringEntity);
            post.setHeader("X-Quikr-App-Id", "520");
            post.setHeader("X-Quikr-Token-Id", "2865896");
            post.setHeader("X-Quikr-Signature", "e6290e090774ee95a2a09db74c7e8c44a954d0d8");
            post.setHeader(HTTP.CONTENT_TYPE, "application/json");

            try {
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                Log.i("Response", EntityUtils.toString(entity));
                Log.i("Response",httpResponse.getStatusLine().getStatusCode()+"");
                return httpResponse.toString();
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
