package me.knox.learningdatabinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.knox.learningdatabinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.btnSimple.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(view.getContext(), SimpleActivity.class));
      }
    });
    binding.btnRv.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(view.getContext(), RecyclerViewActivity.class));
      }
    });
    binding.btnAttribute.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(view.getContext(), AttributeActivity.class));
      }
    });
    binding.btnConversions.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(view.getContext(), ConversionsActivity.class));
      }
    });
  }
}
