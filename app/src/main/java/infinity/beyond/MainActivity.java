package infinity.beyond;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

    import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

public class MainActivity extends Activity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        new HttpsGet().execute();
    }

    private class HttpsGet extends AsyncTask<Void,Void,String>{

        private static final String URL = "https://api.quikr.com/public/trending";
        AndroidHttpClient client = AndroidHttpClient.newInstance("beyond");

        @Override
        protected String doInBackground(Void... voids) {
            HttpGet get = new HttpGet(URL);
            get.setHeader("X-Quikr-App-Id","520");
            get.setHeader("X-Quikr-Token-Id","2865896");
            get.setHeader("X-Quikr-Signature","a8e02a923ef6aa3d3ae8d37975638ca7ed45fc63");
            get.setHeader("Content-Type","application/json");

            try {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                return client.execute(get,responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
