package com.sally.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sally.mysololife.R
import com.sally.mysololife.R.layout.fragment_talk
import com.sally.mysololife.board.BoardInsideActivity
import com.sally.mysololife.board.BoardListLVAdapter
import com.sally.mysololife.board.BoardModel
import com.sally.mysololife.board.BoardWriteActivity
import com.sally.mysololife.databinding.FragmentTalkBinding
import com.sally.mysololife.utils.FBRef


class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    private  val TAG = TalkFragment::class.java.simpleName

    private  lateinit var boardRVAdapter : BoardListLVAdapter

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)


//        val boardList = mutableListOf<BoardModel>()
//        boardList.add(BoardModel("a","b","c","d"))
            boardRVAdapter = BoardListLVAdapter(boardDataList)
            binding.boardListView.adapter = boardRVAdapter

        // item 클릭시 이벤트
            binding.boardListView.setOnItemClickListener { adapterView, view, i, l ->
                //첫번째 방법으로는 listview에 있는 데이터 title , content, time 다 다른 엑티비티로 전달해줘서 만들기
            /*    val intent = Intent(context, BoardInsideActivity::class.java)
                intent.putExtra("title", boardDataList[i].title)
                intent.putExtra("content", boardDataList[i].content)
                intent.putExtra("time", boardDataList[i].time)
                startActivity(intent)*/
                // 두번째 방법으로는 Firebase에 있는 board에 대한 데이터의 id를 기반으로 다시 데이터를 받아오는 방법
                val intent = Intent(context, BoardInsideActivity::class.java)
                intent.putExtra("key",boardKeyList[i])
                startActivity(intent)
            }




        


        binding.writeBtn.setOnClickListener{

            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }
        getFBBoardData()
        // Inflate the layout for this fragment
        return binding.root
    }

    // 게시글 불러오기
    private fun getFBBoardData(){
        
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                boardDataList.clear()

                for (dataModel in dataSnapshot.children){
                    Log.d(TAG, dataModel.toString())

                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())

                }

                boardDataList.reverse()
                boardKeyList.reverse()
                boardRVAdapter.notifyDataSetChanged()

                Log.d(TAG,boardDataList.toString())

            }
            override fun onCancelled(databaseError: DatabaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }
        }
        FBRef.boardRef.addValueEventListener(postListener)
    }
}