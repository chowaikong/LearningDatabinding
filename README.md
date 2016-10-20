# Learning Data Binding
在这里将会呈现的是我从 [Android](https://developer.android.com/topic/libraries/data-binding/index.html) 官网、实际开发项目的实践及其他人的实践所学习的关于 Data Binding 的知识。 创建这个 repository 之时，我已经在O2O、B2C、股票投资等类型的上线项目或个人项目中使用了 Data Binding，并且在今后的商业项目中继续使用。

## 前言
Android 的 Data Binding(数据绑定) 在 Google 的 2015 I/O 上推出，目的在于将逻辑代码和 UI 布局代码更好地绑定在一起，减少 glue code，例如消灭 `findViewById()`，自动刷新数据等。Data Binding 支持 API 7+。在 2016 I/O 上 android 官方宣布支持双向绑定，因此可以也利用 Data Binding 在 android 项目中实现 MVVM 架构。

####优点：
1. 保证 xml 内的代码始终在 UI 线程执行，不必担心线程切换的问题。
2. 减少在业务逻辑中与 View 的交互，例如 setText(), setImageResource(), etc...
3. 性能佳，因为 Data Binding 的一切都发生在编译时，零反射。

####缺点
1. 因为是在编译时产生代码，所以会适当增加编译时间。
2. IDE 的智能提示有限，比如在自定义的 attribute 里面目前无法提示。
3. 增加调试难度，一个地方写错代码将导致其他 layout 的 binding 出错，出错信息比较隐晦，不过随着版本更新，这个问题在逐步解决。

## 准备
1. Android Plugin for Gradle 的版本需为 `1.5.0-alpha1` 或以上，以及相应版本的 Android Studio。
2. 在 Application module 的 `build.gradle` 文件加入一下代码：
<pre>android {
      dataBinding {
   	    enabled = true
   	 }
}
</pre>
3. 可以开始写代码了。

## 使用前
1. 将`<layout> </layout>` 包裹在布局文件中的根布局外。
2. 在 gradle plugin 2.2 版本之前需要 Make 或 Build 项目才能使用了，不过在 2.2 之后写了 `<layout></layout>` 即可使用。

## 如何使用  
1. 在 Activity 中初始化：  
```
@Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DataBindingUtil.setContentView(layoutId, this);
 }
```
 这就相当于 setContextView() ，这个方法返回的是相应的 xml 文件或动态生成的 java 文件，里面包含了所有 view 的实例，可供直接使用而不再需要 findViewById(), 这里说的 view 实例指的是在 xml 文件里面写了 id 的 view，不写 id 是不会拿到 view 实例的.  
 在 activity 中实例的方法还有:  
```
@Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View view = LayoutInflater.from(this).inflate(layoutId, null);  
    ActivityBinding.bind(view);
  }
```  
同样的，这个 bind() 方法也是相应的 xml 文件或返回 java 文件。  
一般来说，按照以上配置的 binding 是指向相应的 xml 文件，不过以 apt 的方式去编译资源文件就可以得到相应的 java 文件，即在 module 的 build 文件顶部加入`apply plugin: 'com.neenbedankt.android-apt'`和在 root 的 build 文件里面的 dependencies 加入 `classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'`, 这样生成的 binding 文件就是 java 文件。  
2. 在 Fragment 中初始化：  
```
@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
        FragmentBinding binding = FragmentBinding.inflate(inflater,container, false);  
        return binding.getRoot();
}
```  
同样的，该方法指向相应的 xml 文件或相应的 java 文件，同时，在 fragment 中初始化还有以下方法：  
```
@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
        View view = inflater.from(this).inflate(layoutId, null);
        FragmentBinding.bind(view);
        return view;
      }
```
Activity 的初始化使用到了 DataBindingUtil 的 setContentView 方法：
```
public static <T extends ViewDataBinding> T setContentView(Activity activity, int layoutId) {
        return setContentView(activity, layoutId, sDefaultComponent);
    }
    
public static <T extends ViewDataBinding> T setContentView(Activity activity, int layoutId,
            DataBindingComponent bindingComponent) {
        activity.setContentView(layoutId);
        View decorView = activity.getWindow().getDecorView();
        ViewGroup contentView = (ViewGroup) decorView.findViewById(android.R.id.content);
        return bindToAddedViews(bindingComponent, contentView, 0, layoutId);
    }
    
private static <T extends ViewDataBinding> T bindToAddedViews(DataBindingComponent component,
            ViewGroup parent, int startChildren, int layoutId) {
        final int endChildren = parent.getChildCount();
        final int childrenAdded = endChildren - startChildren;
        if (childrenAdded == 1) {
            final View childView = parent.getChildAt(endChildren - 1);
            return bind(component, childView, layoutId);
        } else {
            final View[] children = new View[childrenAdded];
            for (int i = 0; i < childrenAdded; i++) {
                children[i] = parent.getChildAt(i + startChildren);
            }
            return bind(component, children, layoutId);
        }
    }
```
当然还有 bind(), infalte(), bindTo() 等方法可以用来绑定布局，不局限在 Acitivty 或 Fragment 中使用，不过在我的实践中发现，Data Binding 还不能很好支持自定义控件的绑定，还是需要使用传统的方式，例如 findViewById, 不过这个情况应该会在以后得到更好的支持，Data Binding 还会带来更多惊喜。
## 单向绑定
### 向布局文件绑定一个对象  
   创建一个 User 对象，在 xml 中加入一下示例代码：  
   ```
   <data>
     <variable
         name="user"
         type="me.knox.learningdatabinding.User"
   </data>
   <TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@{user.name}"
      />
  <TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@{user.gender}"
      />
  <TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@{user.age}"
      />
   ```
   就这样，即使控件没有 id 可以引用，只要往 xml 赋值一个 User，这三个 TextView 就会自动显示设置的内容。  
   在 Activity 或 Fragment 中赋值。这时，binding 文件已经生成了 setUser() 方法，只要在适当的时候调用这个 setUser() 方法即可，例如 binding.setUser(user)。  
### 控件监听绑定
在 xml 中，加入如下代码：  
```
<EditText
      android:id="@+id/edt_title"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:hint="type in to see binding works"
      />
```
这样就可以在绑定的布局实例中找到这个 EditText 控件，一般为驼峰命名，如 edtTitle, 然后在 Activity 或 Fragment 中加入如下代码：  
```
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
```
在 Data Binding 中就是这样使用控件的实例，无需 findViewById()。
### 列表数据绑定  
这里使用 RecyclerView 来展示一个列表。  
首先可以继承 `ViewDataBinding` 来写一个 ViewHolder 通用父类，更方便在项目中使用，代码如下：  
```
public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
  private T binding;

  public DataBoundViewHolder(T binding) {
    super(binding.getRoot());
    this.binding = binding;
  }

  public T getBinding() {
    return binding;
  }
}
```
然后写一个 item_rv.xml:  
```
<layout>
  <data>
    <variable
        name="user"
        type="me.knox.learningdatabinding.User"/>
  </data>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="10dp">

  <TextView
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      android:layout_weight="1"
      android:text="@{user.name}"
      android:gravity="center"
      />
  <TextView
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      android:layout_weight="1"
      android:text="@{user.gender}"
      android:gravity="center"
      />
  <TextView
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      android:layout_weight="1"
      android:text="@{user.age}"
      android:gravity="center"
      />
</LinearLayout>
</layout>
```
再写一个 RecyclerViewAdapter：  
```
public class RecyclerViewAdapter extends RecyclerView.Adapter<DataBoundViewHolder<ItemRvBinding>> {

  private List<User> users;

  public RecyclerViewAdapter(List<User> users) {
    this.users = users;
  }

  @Override
  public DataBoundViewHolder<ItemRvBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
    ItemRvBinding binding = ItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new DataBoundViewHolder<>(binding);
  }

  @Override public void onBindViewHolder(DataBoundViewHolder<ItemRvBinding> holder, int position) {
    holder.getBinding().setUser(users.get(position));
    holder.getBinding().executePendingBindings();
  }

  @Override public int getItemCount() {
    return users == null ? 0 : users.size();
  }
}

```
然后在 Activity 或 Fragment 中生成的 RecyclerView 的实例传入这个 adapter，用法和没有使用 Data Binding 一样，这就是简单地把数据绑定到 RecyclerView 并显示出来。ListView 使用 Data Binding 也类似以上的写法，Data Binding 只是使 adapter 绑定 item view 更方便。  
## 表达式
* 数学运算 + - / * %  
* 字符串连接 +  
* 逻辑 && ||  
* 二元 & | ^  
* 一元 + - ! ~  
* 移位 >> >>> <<  
* 比较== > < >= <=  
* instanceof  
* Grouping ()  
* 字面 character, String, numeric, null  
* Cast  
* 调用方法  
* 访问 field  
* 访问数组  
* 三元 ?:  


示例：  
```
android:text="@{String.valueOf(index + 1)}"
android:visibility="@{age < 13 ? View.GONE : View.VISIBLE}"
android:transitionName='@{"image_" + id}'
```
## Attributes
在 xml 中我们会用到控件的很多 attribute(属性)，假如需要在 java 代码中写很多 setter，例如 setText(), setBackgroundColor() 等等，这会增加代码量，不好复用，比如 TextView 的 setText() 方法在调用前需要很多逻辑处理传入的字符串，而这个逻辑处理在两个完全不同的 Activity 中不同的 TextView 的 setText() 方法都用到了，虽然可以把这个逻辑写到一个共用的类中(例如 Utils)，但是我觉得这不够优雅。Data Binding 可以把种情况写得相对比较优雅一点，因为 `@BindingAdapter`, `@BindingConversion`, 有了这两个 annotation 之后将减少可观的代码量，也会使代码更容易维护。  
下面举例：  
User 这个类的 name 在赋值到一个 TextView 之前需要做一些处理，例如判空或其他复杂逻辑处理，然后又不想写一大块的 setText() 代码，虽然有了 Data Binding 也不可能直接把逻辑写到 xml 里面去，否则会造成 xml 可读性变差，也不好调试问题，这时候可以使用 `@BindingAdapter`。
```
@BindingAdapter("name")
  public static void setUserName(TextView tv, String name) {
    if(name == null) return;
    tv.setText(name);
  }
```
这就会生成一个 namespace 为 name 的 attribute 了，这里包含了一个判空逻辑，这样处理的好处我认为是，如果赋值为空就不需要调用 setText() 方法了，而且如果赋值为空 Data Binding 也会自动避免空指针，赋值 null，要知道 TextView 每次 setText() 都会触发 requestLayout(), 假如是在一个需要频繁处理文本的地方 setText(null)，这可能会造成多次绘制从而 overdraw。直接在 TextView 这样使用：  
```
<TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:name="@{user.name}"
      />
```
如果你想在 xml 里面做字符串格式处理，例如：  
```
<TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:name="@{@string/name(user.name)}"
      />
```
直接引用字符串资源就行了，遗憾的是，目前并未支持字符串资源智能提示，而且在 xml 里也不能直接跳转目标字符串，修改的话需要手动去 strings.xml 查找。

TextView 是不能直接显示一个 double 类型的值的，虽然可以先在 java 代码里面转换成字符串再赋值，但是如果要复用这个转换呢？而且如果需要对这个 double 值约小数位呢？这时候 `@BindingConversion` 派上用场了。  
```
@BindingConversion
  public static String displayMoney(double money) {
    return String.format("%.2f", money);
  }
```
这样， TextView 显示的就是约两位小数的字符串了，直接在 xml 这样写：  
```
<TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@{user.money}"
      />
```
无需特定的 attribute，直接赋值。
