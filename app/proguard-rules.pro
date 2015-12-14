# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhangxiaang/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses

-assumenosideeffects class android.util.Log {
public static *** e(...); #forbidden all Log.e()method
}
# 不要去混淆 dataType里面的model fastJson是反射来获取到的吗?

-keep class com.uprint.android_pack.cloudprint4androidmanager.CPBaseActivity {* ;}
-keep class com.uprint.android_pack.cloudprint4androidmanager.dataType.** {* ;}

-keep class com.uprint.android_pack.cloudprint4androidmanager.dataType.** {*;}
-dontwarn com.uprint.android_pack.cloudprint4androidmanager.dataType.**

# for butter knife   这个如果被混淆了直接导致crash，因为视图无法被找到
# 参考butter knife官网给出来的建议即可
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
# 但是还是有部分的视图被过渡混淆了导致程序crash  继续优化
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# 保护第三方的jar包里面的类不要被混淆了
-keep class com.hedgehog.ratingbar.** {* ;}
-keep class com.github.dmytrodanylyk.android-process-button.** {* ;}
-keep class com.rengwuxian.materialedittext.** {* ;}
-keep class com.mcxiaoke.volley.** {* ;}
-keep class de.greenrobot.** {* ;}
-keep class com.android.support.** {* ;}

#-keep class cn.leancloud.** {* ;}
#-keep class com.avos.avoscloud.** {* ;}
#-dontwarn com.avos.avoscloud.**
# 保护两个fragment不要被混淆
-keep class com.uprint.android_pack.cloudprint4androidmanager.fragment.** {* ;}
-keep class com.uprint.android_pack.cloudprint4androidmanager.widgets.** {* ;}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 参考leancloud官网的混淆文件,不要乱改下面的玩意儿
-keepattributes Signature
-dontwarn com.jcraft.jzlib.**
-keep class com.jcraft.jzlib.**  { *;}

-dontwarn sun.misc.**
-keep class sun.misc.** { *;}

-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *;}

-dontwarn sun.security.**
-keep class sun.security.** { *; }

-dontwarn com.google.**
-keep class com.google.** { *;}

-dontwarn com.avos.**
-keep class com.avos.** { *;}

-dontwarn com.avos.avoscloud.**
-keep class com.avos.avoscloud.** {* ;}

-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient

-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient

-dontwarn android.support.**

-dontwarn org.apache.**
-keep class org.apache.** { *;}

-dontwarn org.jivesoftware.smack.**
-keep class org.jivesoftware.smack.** { *;}

-dontwarn com.loopj.**
-keep class com.loopj.** { *;}

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-keep interface com.squareup.okhttp.** { *; }

-dontwarn okio.**

-dontwarn org.xbill.**
-keep class org.xbill.** { *;}

-keepattributes *Annotation*