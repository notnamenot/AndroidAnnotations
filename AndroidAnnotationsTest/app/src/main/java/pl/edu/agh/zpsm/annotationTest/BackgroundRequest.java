package pl.edu.agh.zpsm.annotationTest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

// onPreExecute(), onPostExecute(Result), are invoked on the UI thread. So you can display toast here.
// onProgressUpdate(Progress...), invoked on the UI thread after a call to publishProgress(Progress...) can be used to animate a progress bar or show logs in a text field.
public class BackgroundRequest extends AsyncTask<String, Integer, Long> {

    Context context;
    public BackgroundRequest(Context context){
        this.context = context;
    }


    @Override
    protected Long doInBackground(String... strings) {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpResponse response = httpclient.execute(new HttpGet("https://gorest.co.in/public/v1/posts/1358"));
            publishProgress(response.getStatusLine().getStatusCode());
            return 1L;
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Toast.makeText(context, "HTTP status " + Integer.toString(values[0]), Toast.LENGTH_SHORT).show();
    }

}
