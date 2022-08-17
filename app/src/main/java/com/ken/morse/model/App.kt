package com.ken.morse.model

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import java.util.stream.Collectors

class App(private val context: Context, private val appInfo: ResolveInfo, prefs: SharedPreferences) {
    companion object {
        private val defaultEnabledApps: MutableSet<String> = HashSet()
        @JvmStatic
        fun loadApps(context: Context, prefs: SharedPreferences): List<App> {
            val apps: MutableList<App> = ArrayList()
            val mainIntent = Intent(Intent.ACTION_MAIN, null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            val pkgAppsList = context.packageManager.queryIntentActivities(mainIntent, 0)
            for (pkgApp in pkgAppsList) {
                apps.add(App(context, pkgApp, prefs))
            }
            return apps.stream().sorted { app: App, other: App -> app.appName().compareTo(other.appName()) }.collect(Collectors.toList())
        }

        init {
            // Twitter
            defaultEnabledApps.add("com.twitter.android")
            // Hangouts
            defaultEnabledApps.add("com.google.android.talk")
            // Facebook messenger
            defaultEnabledApps.add("com.facebook.orca")
            // Line
            defaultEnabledApps.add("jp.naver.line.android")
            // Gmail
            defaultEnabledApps.add("com.google.android.gm")
        }
    }

    private val prefs: SharedPreferences
    @JvmField
    val appID: String
    private var appName: String? = null
    private var drawable: Drawable? = null
    private val defaultEnablement: Boolean
    fun appName(): String {
        if (appName == null) {
            appName = appInfo.loadLabel(context.packageManager).toString()
        }
        return appName as String
    }

    fun drawable(): Drawable? {
        if (drawable == null) {
            drawable = appInfo.loadIcon(context.packageManager)
        }
        return drawable
    }

    private fun notificationEnabledKey(): String {
        return "notification.enabled.$appID"
    }

    var isNotificationEnabled: Boolean
        get() = prefs.getBoolean(notificationEnabledKey(), defaultEnablement)
        set(enabled) {
            prefs.edit().putBoolean(notificationEnabledKey(), enabled).commit()
        }

    init {
        appID = appInfo.activityInfo.packageName
        this.prefs = prefs
        defaultEnablement = defaultEnabledApps.contains(appID)
    }
}