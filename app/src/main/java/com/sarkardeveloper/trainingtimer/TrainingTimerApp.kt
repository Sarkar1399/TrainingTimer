package com.sarkardeveloper.trainingtimer

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle

class TrainingTimerApp : Application(), Application.ActivityLifecycleCallbacks {

    companion object {
        var isActionMediaButton = false
        lateinit var appContext: Context
            private set
        lateinit var curActivity: Activity
        fun getString(id: Int): String {
            return appContext.getString(id)
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        curActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

}