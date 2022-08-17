package com.ken.morse.model

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import com.ken.morse.model.App.Companion.loadApps

class Notifier(private val context: Context, private val prefs: SharedPreferences) {
    private val apps: List<App>
    private val appForId: MutableMap<String, App> = HashMap()
    fun apps(): List<App> {
        return apps
    }

    fun appForId(id: String?): App? {
        return appForId[id]
    }

    var isGlobalNotificationEnabled: Boolean
        get() = prefs.getBoolean(KEY_GLOBAL_ENABLE_NOTIFICATION, true)
        set(enabled) {
            prefs.edit().putBoolean(KEY_GLOBAL_ENABLE_NOTIFICATION, enabled).commit()
        }

    companion object {
        private const val KEY_GLOBAL_ENABLE_NOTIFICATION = "global.notification.enabled"
    }

    init {
        apps = loadApps(context, prefs)
        for (app in apps) {
            appForId[app.appID] = app
        }

        // Hacky async task to pre-load the expensive names and icons in the background...
        val task: AsyncTask<Void?, Void?, Void?> = object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg voids: Void?): Void? {
                for (app in apps) {
                    app.appName()
                    app.drawable()
                }
                return null
            }
        }
        task.execute()
    }
}