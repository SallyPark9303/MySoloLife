package com.sally.mysololife.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sally.mysololife.MainActivity
import com.sally.mysololife.R
import com.sally.mysololife.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth

     //   setContentView(R.layout.activity_join)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.joinBtn.setOnClickListener{
             var isGoToJoin  =true

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 =binding.passwordArea2.text.toString()

            // 저기 값이 비어있는지 확인
            if(email.isEmpty()){
                Toast.makeText(this, "이메일을 입력 해 주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin  =false
            }
            if(password1.isEmpty()){
                Toast.makeText(this, "비밀번호를 입력 해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin  =false
            }
            if(password2.isEmpty()){
                Toast.makeText(this, "비밀번호 확인을 입력 해 주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin  =false
            }
            if(password1.length<6){
                Toast.makeText(this, "비밀번호를 6자리 이상으로 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin  =false
            }

            // 비밀번호 2개가 같은지 확인
            if(!password1.equals(password2)){
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin  =false
            }


            if(isGoToJoin){
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            // 메인 화면 이동후 뒤로가기 하면  앱 꺼지게
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP 
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()

                        }
                    }
            }
        }
    }
}