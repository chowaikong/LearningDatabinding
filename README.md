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
2. Make 或 Build 项目，然后就可以使用了。