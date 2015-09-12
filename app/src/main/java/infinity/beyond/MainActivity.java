package infinity.beyond;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private final String TAG = "Infinity";
    TextView textView;
    ListView trending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        trending = (ListView) findViewById(R.id.trending);
        trending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object tmp = adapterView.getItemAtPosition(i);
                Log.i(TAG,tmp.toString());
            }
        });
        new HttpsGet().execute();
    }

    private class HttpsGet extends AsyncTask<Void,Void,List<String>>{

        private static final String URL = "https://api.quikr.com/public/trending";
        AndroidHttpClient client = AndroidHttpClient.newInstance("beyond");

        @Override
        protected List<String> doInBackground(Void... voids) {
            HttpGet get = new HttpGet(URL);
            get.setHeader("X-Quikr-App-Id","520");
            get.setHeader("X-Quikr-Token-Id","2865896");
            get.setHeader("X-Quikr-Signature","a8e02a923ef6aa3d3ae8d37975638ca7ed45fc63");
            get.setHeader("Content-Type","application/json");

            try {
                JSONHandler responseHandler = new JSONHandler();
                List<String> l =  client.execute(get, responseHandler);
                return l;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(List<String> result) {
            if(result != null) {
                Log.i(TAG, String.valueOf(result));
                trending.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.listitem,result));
            }
        }
    }

    private class JSONHandler implements ResponseHandler<List<String>>{

        private static final String TRENDING = "getTrendingResponse";
        private static final String LOL = "trendingData";
        @Override
        public List<String> handleResponse(HttpResponse httpResponse)
                throws ClientProtocolException, IOException {
                List<String> result = new ArrayList<String>();
                String JSONResponse = new BasicResponseHandler().handleResponse(httpResponse);
            try {
                JSONObject jsonObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();
                JSONObject object = jsonObject.getJSONObject(TRENDING);
                JSONArray treandingData = object.getJSONArray(LOL);

                Log.i(TAG, String.valueOf(treandingData));

                for(int i = 0; i < treandingData.length(); i++) {
                    JSONObject data = (JSONObject) treandingData.get(i);
                    Log.i(TAG, String.valueOf(data));
                    Log.i(TAG, String.valueOf(data.getJSONObject("attr")));
                    JSONObject tmp = data.getJSONObject("attr");
                    String s = data.getString("cat_id");
                    if (s.equals("56")) {
                        result.add("Home - Office Furniture" + "\n" + tmp.getString("attribute_Furniture_Type"));

                    } else if (s.equals("71")) {
                        result.add("Cars" + "\n" + tmp.getString("attribute_Model") + tmp.getString("attribute_Brand_name"));
                    } else if(s.equals("72")){
                        result.add("Bikes & Scooters" + "\n" + tmp.getString("attribute_Model") + tmp.getString("attribute_Brand_name"));
                    } else if(s.equals("51")){
                        result.add("TV - DVD - Multimedia" + "\n" + tmp.getString("attribute_Product_Type") + tmp.getString("attribute_Brand_name"));
                    } else if(s.equals("112")){
                        result.add("" + "\n" + data.getString("count"));
                    } else if(s.equals("148")){
                        result.add("Cameras - Digicams" + "\n" + data.getString("count"));
                    } else if(s.equals("149")){
                        result.add("Mobile Phones" + "\n" + data.getString("count"));
                    }

                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.newPost) {
            Toast.makeText(MainActivity.this,"New post",Toast.LENGTH_SHORT).show();
            Intent jmp = new Intent(MainActivity.this,addNew.class);
            startActivity(jmp);

        } else if(id == R.id.showMyAds){
            Toast.makeText(MainActivity.this,"New post",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
