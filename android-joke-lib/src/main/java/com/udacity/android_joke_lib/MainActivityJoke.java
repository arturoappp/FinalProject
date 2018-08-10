package com.udacity.android_joke_lib;

import android.content.Intent;
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
    Intent intent = getIntent();
    if (intent != null) {
      if (intent.hasExtra(JOKE)) {
        String joke = intent.getStringExtra(JOKE);
        if (joke != null && !joke.isEmpty()) {
          binding.tvJoke.setText(joke);
          Toast.makeText(this, "joke in lib:" + joke, Toast.LENGTH_SHORT).show();
        }
      } else {
        binding.tvJoke.setText(R.string.no_extra_joke);
      }
    }
  }
}
