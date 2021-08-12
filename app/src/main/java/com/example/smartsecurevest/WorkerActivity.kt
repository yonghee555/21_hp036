package com.example.smartsecurevest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartsecurevest.databinding.ActivityWorkerBinding

class WorkerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerBinding
    private lateinit var localDB: LocalDB
    val DATABASE_VERSION = 1
    val DATABASE_NAME = "LocalDB.db"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerBinding.inflate(layoutInflater)    // 뷰 바인딩
        val view = binding.root
        setContentView(view)

        localDB= LocalDB(this, DATABASE_NAME,null, DATABASE_VERSION) // SQLite 모듈 생성

        var db = localDB.readableDatabase
        val projection = arrayOf(LocalDatas.userData.COLUMN_NAME_NAME)

        val cursor = db.query(
            LocalDatas.userData.TABLE_NAME,   // 테이블
            projection,             // 리턴 받고자 하는 컬럼
            null,              // where 조건
            null,          // where 조건에 해당하는 값의 배열
            null,                   // 그룹 조건
            null,                   // having 조건
            null               // orderby 조건 지정
        )

        val list = ArrayList<WorkerItem>()

        while(cursor.moveToNext()){
            list.add(WorkerItem(cursor.getString(cursor.getColumnIndex(LocalDatas.userData.COLUMN_NAME_NAME)),"010-4545-7878"))
        }

        val adapter = RecyclerAdapter(list)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
    }
}