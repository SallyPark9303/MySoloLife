package com.sally.mysololife.contentsList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sally.mysololife.R
import com.sally.mysololife.utils.FBRef

class ContentListActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter: ContentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content_list)

        // Write a message to the database
        val database = Firebase.database
        val category = intent.getStringExtra("category")

        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()

        val rv : RecyclerView = findViewById(R.id.rv)
        rvAdapter = ContentRVAdapter(baseContext, items, itemKeyList, bookmarkIdList)

        if(category == "category1"){
            myRef = database.getReference("contents")
        }else if(category == "category2"){
            myRef = database.getReference("contents2")
        }

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children){
                    Log.d("ContentListActivity", dataModel.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                // 비동기 형태로 데이터를 받아옴-> 동기화시켜줌
                rvAdapter.notifyDataSetChanged()
                Log.d("ContentListActivity", items.toString())
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)
        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this,2)
        val myRef2 = database.getReference("contents2")
        myRef2.push().setValue(
            ContentModel("title6","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fl2KC3%2Fbtq64lkUJIN%2FeSwUPyQOddzcj6OAkPKZuk%2Fimg.png","https://philosopher-chan.tistory.com/1243")
        )
        myRef2.push().setValue(
            ContentModel("title7","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmBh5u%2Fbtq651yYxop%2FX3idRXeJ0VQEoT1d6Hln30%2Fimg.png","https://philosopher-chan.tistory.com/1243")       )
        myRef2.push().setValue(
            ContentModel("title8","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlOnja%2Fbtq69Tmp7X4%2FoUvdIEteFbq4Z0ZtgCd4p0%2Fimg.png","https://philosopher-chan.tistory.com/1243") )

        getBookmarkData()

    }


    private fun getBookmarkData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookmarkIdList.clear()

                for (dataModel in dataSnapshot.children){
                    bookmarkIdList.add(dataModel.key.toString())

                }
                Log.d("Bookmark : ", bookmarkIdList.toString())
                rvAdapter.notifyDataSetChanged()

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookmarkRef.addValueEventListener(postListener)
    }




}