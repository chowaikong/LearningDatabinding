package me.knox.learningdatabinding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.widget.TextView;

/**
 * Created by KNOX on 7/31/16.
 */
public class User {
  private String name;
  private String gender;
  private String age;
  private double money;

  public User(String name, String gender, String age) {
    this.name = name;
    this.gender = gender;
    this.age = age;
  }

  public User(double money) {
    this.money = money;
  }

  /**
   生成 Getter 来填充 xml 的数据
   **/

  public String getName() {
    return name;
  }

  public String getGender() {
    return gender;
  }

  public String getAge() {
    return age;
  }

  public double getMoney() {
    return money;
  }

  @BindingAdapter("name")
  public static void setUserName(TextView tv, String name) {
    if(name == null) return;
    tv.setText(name);
  }

  @BindingConversion
  public static String displayMoney(double money) {
    return String.format("%.2f", money);
  }
}
