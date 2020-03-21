package com.example.myscheduler

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity: AppCompatActivity() {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        // Realmクラスのインスタンス取得
        realm = Realm.getDefaultInstance()
        // 項目を直列に並べるためのレイアウトマネージャーを設定する（セルの配置方法）
        list.layoutManager = LinearLayoutManager(this)
        // 全てのスケジュールを取得する
        val schedules = realm.where<Schedule>().findAll()
        // スケジュールを渡すためのアダプアーを設定する（セルの生成/更新）
        val adapter = ScheduleAdapter(schedules)
        list.adapter = adapter


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    // アクティビティの終了時にコールされる
    override fun onDestroy() {
        super.onDestroy()
        // Realmのインスタンス破棄
        realm.close()
    }

}
