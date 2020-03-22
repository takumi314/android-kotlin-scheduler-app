package com.example.myscheduler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

// データベースから取得した結果をRecylerViewに表示するための専用アダプタークラス
class ScheduleAdapter(data: OrderedRealmCollection<Schedule>):
    RealmRecyclerViewAdapter<Schedule, ScheduleAdapter.ViewHolder>(data, true) {

    private var listener: ((Long?) -> Unit)? = null

    fun setOnItemClickListener(listener: (Long?) -> Unit) {
        this.listener = listener
    }

    // Initializer

    init {
        // データ内の１つの項目を指し示すために
        // 固有のID（キー）を使うことを宣言する
        setHasStableIds(true)
    }

    // RecyclerView.ViewHolderクラスの継承クラス
    //  - セルに使用するビューを保持する
    class ViewHolder(cell: View): RecyclerView.ViewHolder(cell) {
        val date: TextView = cell.findViewById(android.R.id.text1)
        val title: TextView = cell.findViewById(android.R.id.text2)
    }

    // Override - RecyclerView.ViewHolder

    // RecyclerViewが新しいViewHolderを必要する時にコールされる
    //  - parent:   新しいViewの追加先ViewGroup
    //  - viewType: セルに複数のデザインを適用する場合には、使い分けるための仕組みとなる
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        // LayoutInflaterクラス: XMLファイルからビューを生成する機能を適用している

        // 指定したコンテキストからLayoutInflaterクラスのインスタンスを取得する
        val inflater = LayoutInflater.from(parent.context)

        // レイアウトXMLからビューを作成する
        // ビューにレイアウトXMlを適用する
        //  - resource:     適用するレイアウトXMLリソースID
        //  - root:         第3引数がfalseの場合、作成するビューをアタッチすうビューを指定する
        //  - attachToRoot: XMLファイルから作成したビューを返したい場合は false を指定する
        val view = inflater.inflate(android.R.layout.simple_list_item_2, parent,false)

        return ViewHolder(view)
    }

    // 指定された位置（セル）にデータを表示するために RecyclerView によってコールされる
    //  - hodler: 更新する対象のViewHolder
    //  - position: 更新に使用するデータの、アダプターのデータセット内における位置
    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        // RealmRecyclerViewAdapter.getItemメソッド
        // ScheduleAdapterのジェネリクス（総称型）にScheduleクラスが指定されているため、 戻り値として Schedule型 を返す
        val schedule = getItem(position)
        holder.date.text = android.text.format.DateFormat.format("yyy/MM/dd", schedule?.date)
        holder.title.text = schedule?.title
        // セルがクリックされた時のイベント
        holder.itemView.setOnClickListener {
            listener?.invoke(schedule?.id)
        }
    }

    // 指定された位置のアイテムを返す
    //  - index: アイテムの位置
    override fun getItemId(position: Int): Long {
        // 描画の高速化処理を有効化するため、データ項目に対応した固有ID（キー）を返すようにする
        // キーを使うことでデータが更新された時に変更のあったセルだけを更新することができる.
        return getItem(position)?.id ?: 0
    }

}