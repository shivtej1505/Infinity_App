package infinity.beyond;

import android.app.Activity;
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
    ListView trending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trending = (ListView) findViewById(R.id.trending);
        trending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Object tmp = adapterView.getItemAtPosition(i);
                startActivity(new Intent(MainActivity.this,showItem.class));
                Log.i(TAG,tmp.toString());
            }
        });

        new HttpsGet().execute();
    }

    private class HttpsGet extends AsyncTask<Void,Void,myAdapter>{

        private static final String URL = "https://api.quikr.com/public/trending";
        AndroidHttpClient client = AndroidHttpClient.newInstance("beyond");

        @Override
        protected myAdapter doInBackground(Void... voids) {

            SharedPreferences preferences = getSharedPreferences("beyond",MODE_PRIVATE);

            HttpGet get = new HttpGet(URL);

            get.setHeader("X-Quikr-App-Id",preferences.getString(shortcut.tokens.APP_ID,"520"));
            get.setHeader("X-Quikr-Token-Id",preferences.getString(shortcut.tokens.TOKEN_ID,"3088686"));
            get.setHeader("X-Quikr-Signature",preferences.getString(shortcut.tokens.TRENDING,"4db0cf0c1edfa3cc2694b16576b0019013007348"));

            get.setHeader("Content-Type","application/json");

            try {
                JSONHandler responseHandler = new JSONHandler();
                myAdapter adaptr = client.execute(get, responseHandler);
                client.close();
                return adaptr;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(myAdapter result) {
            if(result != null) {
                Log.i(TAG, String.valueOf(result));
                //trending.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.listitem,result));
                trending.setAdapter(result);
            }
        }
    }

    private class JSONHandler implements ResponseHandler<myAdapter>{

        private static final String TRENDING = "getTrendingResponse";
        private static final String LOL = "trendingData";
        @Override
        public myAdapter handleResponse(HttpResponse httpResponse)
                throws ClientProtocolException, IOException {

            myAdapter adapter = new myAdapter(MainActivity.this);
            adapter.clear();
            ItemData itemData;

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

                    itemData = new ItemData(data.getInt("cat_id"),data.getInt("count"),tmp);
                    adapter.add(itemData);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return adapter;
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
            //Toast.makeText(MainActivity.this,"New post",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,showMyAds.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
