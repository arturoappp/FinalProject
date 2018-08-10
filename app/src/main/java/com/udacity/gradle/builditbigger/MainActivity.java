package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
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
import com.udacity.gradle.builditbigger.IdlingResource.SimpleIdlingResource;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MyListenerCallBack {
  ActivityMainBinding binding;
  @Nullable private SimpleIdlingResource mIdlingResource;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.progressbar.setVisibility(View.INVISIBLE);
  }

  @VisibleForTesting
  @NonNull
  public IdlingResource getIdlingResource() {
    if (mIdlingResource == null) {
      mIdlingResource = new SimpleIdlingResource();
    }
    return mIdlingResource;
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
    binding.progressbar.setVisibility(View.VISIBLE);
    if (mIdlingResource != null) {
      mIdlingResource.setIdleState(false);
    }
    new EndpointsAsyncTask(this).execute(NetWorkUtil.URL);
  }

  static class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
    private MyApi myApiService = null;
    private MyListenerCallBack mCallBack;

    public EndpointsAsyncTask(MyListenerCallBack callback) {
      mCallBack = callback;
    }

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
        return myApiService.getJoke().execute().getData();
      } catch (IOException e) {
        Log.e(this.getClass().getName(), e.getMessage());
        e.printStackTrace();
        return e.getMessage();
      }
    }

    @Override
    protected void onPostExecute(String joke) {
      mCallBack.onSuccess(joke);
    }
  }

  @Override
  public void onSuccess(String joke) {
    binding.progressbar.setVisibility(View.INVISIBLE);
    Intent intent = new Intent(MainActivity.this, MainActivityJoke.class);
    intent.putExtra(MainActivityJoke.JOKE, joke);
    startActivity(intent);
    if (mIdlingResource != null) {
      mIdlingResource.setIdleState(true);
    }
  }
}
