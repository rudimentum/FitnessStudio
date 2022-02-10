package com.rudimentum.fitnessstudio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        btvUserId.text = "${R.string.user_id} $userId"
        btvEmailId.text = "${R.string.email_id} $emailId"

        btnSignOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about_us -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_trainers -> {
                textView.text = "Trainers!"
                return true
            }
            R.id.action_clubs -> {
                textView.text = "Clubs!"
                return true
            }
            R.id.action_login -> {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}