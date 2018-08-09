package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.android_joke_lib.MainActivityJoke;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void tellJoke(View view) {
    new EndpointsAsyncTask().execute(NetWorkUtil.URL);
  }

  class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
    private MyApi myApiService = null;

    @Override
    protected String doInBackground(String... params) {
      if (myApiService == null) { // Only do this once
        MyApi.Builder builder =
            new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl(params[0])
                .setGoogleClientRequestInitializer(
                    new GoogleClientRequestInitializer() {
                      @Override
                      public void initialize(
                          AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                          throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                      }
                    });
        // end options for devappserver

        myApiService = builder.build();
      }

      try {
        //return myApiService.getJoke().execute().getData();
        return myApiService.sayHi("arturo").execute().getData();
      } catch (IOException e) {
        Log.e(MainActivity.this.getClass().getName(), e.getMessage());
        e.printStackTrace();
        return e.getMessage();
      }
    }

    @Override
    protected void onPostExecute(String joke) {
      Intent intent = new Intent(MainActivity.this, MainActivityJoke.class);
      intent.putExtra(MainActivityJoke.JOKE, joke);
      startActivity(intent);
    }
  }
}
