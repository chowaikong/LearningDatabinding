package me.knox.learningdatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import me.knox.learningdatabinding.databinding.ActivitySimpleBinding;

public class SimpleActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final ActivitySimpleBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_simple);

    binding.edtTitle.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void afterTextChanged(Editable editable) {
        String s = editable.toString();
        binding.tvTitle.setText(s);
      }
    });

    binding.setUser(new User("KNOX", "MALE", "24"));
  }
}
