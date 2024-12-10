package com.example.theflyingfishgameapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {
    private lateinit var startGameAgainButton: Button
    private lateinit var displayScore: TextView
    private val score: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val score = intent.getStringExtra("score") ?: ""


        startGameAgainButton = findViewById(R.id.playAgain)


        startGameAgainButton.setOnClickListener { view ->
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }


}
