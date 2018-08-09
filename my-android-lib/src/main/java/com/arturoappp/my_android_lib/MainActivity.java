package com.arturoappp.my_android_lib;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arturoappp.my_android_lib.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String JOKE = "joke";
    private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    if (getIntent() != null) {
      String joke = getIntent().getStringExtra(JOKE);
      if (joke != null && !joke.isEmpty()) {
        binding.tvJoke.setText(joke);
      }
    }
  }
}
