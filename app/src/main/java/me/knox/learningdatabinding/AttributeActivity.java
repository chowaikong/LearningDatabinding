package me.knox.learningdatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import me.knox.learningdatabinding.databinding.ActivityAttributeBinding;

public class AttributeActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityAttributeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_attribute);
    binding.setUser(new User("KNOX", "MALE", "24"));
  }
}
