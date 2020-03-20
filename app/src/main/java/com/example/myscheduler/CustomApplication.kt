package com.example.myscheduler

import android.app.Application
import io.realm.Realm

class CustomApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Realmライブラリを初期化し、
        // すぐに使用できるデフォルト構成を作成する
        Realm.init(this)
    }


}