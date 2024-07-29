package com.sally.mysololife.board

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sally.mysololife.R
import com.sally.mysololife.databinding.ActivityBoardInsideBinding
import com.sally.mysololife.utils.FBRef

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    private  lateinit var binding : ActivityBoardInsideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_board_inside)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        // 첫번째 방법
   /*     val title = intent.getStringExtra("title").toString()
        val content = intent.getStringExtra("content").toString()
        val time = intent.getStringExtra("time").toString()

        binding.titleArea.text = title
        binding.contentArea.text = content
        binding.timeArea.text = time*/


        // 두번째 방법
        val key = intent.getStringExtra("key")
        Toast.makeText(this, key, Toast.LENGTH_LONG).show()
        getBoardData(key.toString())
    }

    private fun getBoardData(key: String) {
        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, key)
                val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                if(dataModel != null){
                    Log.d(TAG, dataModel!!.title)
                    Log.d(TAG, dataModel!!.content)
                    Log.d(TAG, dataModel!!.time)
                }
                binding.titleArea.text = dataModel!!.title

            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }
        }
        FBRef.boardRef.addValueEventListener(postListener)


    }

}