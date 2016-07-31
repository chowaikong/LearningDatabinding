package me.knox.learningdatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import me.knox.learningdatabinding.databinding.ActivityRvBinding;

public class RecyclerViewActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityRvBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_rv);

    List<User> users = new ArrayList<>();
    for(int i = 0; i < 30; i++) {
      users.add(new User("KNOX", "MALE", "24"));
    }
    binding.rv.setAdapter(new RecyclerViewAdapter(users));
    binding.rv.getAdapter().notifyDataSetChanged();
  }
}
