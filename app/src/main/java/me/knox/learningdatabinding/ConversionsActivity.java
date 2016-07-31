package me.knox.learningdatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import me.knox.learningdatabinding.databinding.ActivityConversionsBinding;

public class ConversionsActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityConversionsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_conversions);
    binding.setUser(new User(123.123));
  }
}
