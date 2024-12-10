package com.example.theflyingfishgameapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class FlyingFishView(context: Context?) : View(context) {
    private val fish: Array<Bitmap?> = arrayOfNulls(2)
    private val background: Bitmap
    private val scorePaint: Paint
    private val life: Array<Bitmap?> = arrayOfNulls(2)
    private var fishX: Int = 10
    private var fishY: Int = 0
    private var fishSpeed: Int = 0
    private var yellowX: Int = 0
    private var yellowY: Int = 0
    private var yellowSpeed: Int = 16
    private val yellowPaint = Paint()

    private var greenX: Int = 0
    private var greenY: Int = 0
    private var greenSpeed: Int = 20
    private val greenPaint = Paint()

    private var redX: Int = 0
    private var redY: Int = 0
    private var redSpeed: Int = 25
    private val redPaint = Paint()
    private var score:Int=0
    private var lifeCounter:Int=3

    private var touch: Boolean = false


    private var canvasWidth: Int = 0
    private var canvasHeight: Int = 0

    init {
        fish[0] = BitmapFactory.decodeResource(resources, R.drawable.fish1)
        fish[1] = BitmapFactory.decodeResource(resources, R.drawable.fish2)
        background = BitmapFactory.decodeResource(resources, R.drawable.background)
        yellowPaint.color = Color.YELLOW
        yellowPaint.isAntiAlias = true

        greenPaint.color = Color.GREEN
        greenPaint.isAntiAlias = true

        redPaint.color = Color.RED
        redPaint.isAntiAlias = true

        scorePaint = Paint()
        scorePaint.color = Color.WHITE
        scorePaint.textSize = 70f
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD)
        scorePaint.isAntiAlias = true

        life[0] = BitmapFactory.decodeResource(resources, R.drawable.hearts)
        life[1] = BitmapFactory.decodeResource(resources, R.drawable.heart_grey)
        fishY=500;
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val canvasWidth = width
        val canvasHeight = height

        canvas.drawBitmap(background, 0f, 0f, null)
        val fishBitmap = fish[0]
        if (fishBitmap != null) {
            val minFishY = fishBitmap.height
            val maxFishY = canvasHeight - fishBitmap.height * 3
            fishY += fishSpeed
            fishY = Math.max(minFishY, Math.min(fishY, maxFishY))
        }
        fishSpeed += 2;
        if (touch) {
            fish[1]?.let { canvas.drawBitmap(it, fishX.toFloat(), fishY.toFloat(), null) }
            touch = false
        }else{
            canvas.drawBitmap(fish[0]!!, fishX.toFloat(), fishY.toFloat(), null)

        }


        yellowX -= yellowSpeed
        if (hitBallChecker(yellowX, yellowY)) {
            score += 10
            yellowX =- 100
        }

        if (yellowX < 0) {
            if (fishBitmap != null) {
                val minFishY = fishBitmap.height
                val maxFishY = canvasHeight - fishBitmap.height * 3
                yellowX = canvasWidth + 21
                yellowY = (Math.random() * (maxFishY - minFishY) + minFishY).toInt()
            }
        }

        canvas.drawCircle(yellowX.toFloat(), yellowY.toFloat(), 25f, yellowPaint)

        greenX -= greenSpeed
        if (hitBallChecker(greenX, greenY)) {
            score += 20
            greenX =- 100
        }

        if (greenX < 0) {
            if (fishBitmap != null) {
                val minFishY = fishBitmap.height
                val maxFishY = canvasHeight - fishBitmap.height * 3
                greenX = canvasWidth + 21
                greenY = (Math.random() * (maxFishY - minFishY) + minFishY).toInt()
            }
        }

        canvas.drawCircle(greenX.toFloat(), greenY.toFloat(), 25f, greenPaint)

        redX -= redSpeed
        if (hitBallChecker(redX, redY)) {

            redX =- 100
            lifeCounter--
            if (lifeCounter == 0) {

                val gameOverIntent = Intent(context, GameOverActivity::class.java)
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                gameOverIntent.putExtra("score", score)
                context.startActivity(gameOverIntent)
            }

        }

        if (redX < 0) {
            if (fishBitmap != null) {
                val minFishY = fishBitmap.height
                val maxFishY = canvasHeight - fishBitmap.height * 3
                redX = canvasWidth + 21
                redY = (Math.random() * (maxFishY - minFishY) + minFishY).toInt()
            }
        }

        canvas.drawCircle(redX.toFloat(), redY.toFloat(), 30f, redPaint)
        canvas.drawText("Score:"+score, 20f, 60f, scorePaint)
        for (i in 0 until 3) {
            val x = (480 + life[0]!!.width *1.5 * i).toInt()
            val y = 30
            if (i < lifeCounter) {
                canvas.drawBitmap(life[0]!!, x.toFloat(), y.toFloat(), null)
            } else {
                canvas.drawBitmap(life[1]!!, x.toFloat(), y.toFloat(), null)
            }
        }



    }
    fun hitBallChecker(x: Int, y: Int): Boolean {
        return ((fishX < x && x < ((fishX + fish[0]?.width!!)
            ?: 0) &&
                fishY < y && y < ((fishY + fish[0]?.height!!) ?: 0)))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            touch = true
            fishSpeed = -22
        }
        return true
    }
}




