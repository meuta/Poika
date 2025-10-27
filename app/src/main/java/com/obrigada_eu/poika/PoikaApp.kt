package com.obrigada_eu.poika

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PoikaApp : Application() {

//    lateinit var firebaseAnalytics: FirebaseAnalytics
//
//    override fun onCreate() {
//        super.onCreate()
//        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
//        firebaseAnalytics.logEvent("app_open", null)
//    }
}