package com.linkces.linshi;

import android.app.Activity;
import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <应用初始化> <功能详细描述>
 * 
 */
public class App extends Application
{
    /**
     * app实例
     */
    public static App app = null;
    
    /**
     * 本地activity栈
     */
    public static List<Activity> activitys = new ArrayList<>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        init();
    }

    private void init() {

        app = this;

        //初始化Leak内存泄露检测工具
        LeakCanary.install(this);
        //初始化Stetho调试工具
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }



    @Override
    public void onTerminate()
    {
        super.onTerminate();
        onDestory();
    }

    /**
     * 退出应用，清理内存
     */
    private void onDestory() {
        activitys.forEach(this::deleteActivity);
    }


    /**
     * 
     * <添加> <功能详细描述>
     * 
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public void addActivity(Activity activity)
    {
        activitys.add(activity);
    }
    
    /**
     * 
     * <删除>
     * <功能详细描述>
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public void deleteActivity(Activity activity)
    {
        if (activity != null)
        {
            activitys.remove(activity);
            activity.finish();
        }
    }
}
