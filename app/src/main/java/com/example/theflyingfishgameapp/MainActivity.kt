package com.example.theflyingfishgameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private lateinit var gameView: FlyingFishView // Declare gameView
    private val handler = Handler()
    private val interval = 30L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = FlyingFishView(this) // Initialize gameView
        setContentView(gameView) // Set content view to gameView

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    gameView.invalidate()
                }
            }
        }, 0, interval.toLong())
    }
}
