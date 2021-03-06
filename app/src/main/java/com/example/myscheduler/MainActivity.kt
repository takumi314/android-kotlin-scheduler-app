package com.example.myscheduler

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where

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
        adapter.setOnItemClickListener { id ->
            val intent = Intent(this, EditActivity::class.java)
                .putExtra("schedule_id", id)
            startActivity(intent)
        }
        list.adapter = adapter


        fab.setOnClickListener { view ->
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    // アクティビティの終了時にコールされる
    override fun onDestroy() {
        super.onDestroy()
        // Realmのインスタンス破棄
        realm.close()
    }

}
