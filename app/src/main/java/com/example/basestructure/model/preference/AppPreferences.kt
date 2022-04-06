package com.example.basestructure.model.preference

import android.content.Context

class AppsPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("apps", Context.MODE_PRIVATE)

    fun setPort(port: Set<String>){
        with(prefs.edit()) {
            putStringSet("port", port)
            apply()
        }
    }

    fun getPort(): Set<String> {
        return prefs.getStringSet("port", setOf("1")) ?: setOf("1")
    }
}