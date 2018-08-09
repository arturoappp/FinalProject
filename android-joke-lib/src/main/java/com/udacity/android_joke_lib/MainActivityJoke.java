package com.udacity.android_joke_lib;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.android_joke_lib.databinding.ActivityMainJokeBinding;

public class MainActivityJoke extends AppCompatActivity {

  public static final String JOKE = "joke";
  private ActivityMainJokeBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // setContentView(R.layout.activity_main_joke);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main_joke);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    if (getIntent() != null) {
      String joke = getIntent().getStringExtra(JOKE);
      if (joke != null && !joke.isEmpty()) {
        binding.tvJoke.setText(joke);
        Toast.makeText(this, "joke in lib:" + joke, Toast.LENGTH_SHORT).show();
      }
    }
  }
}
