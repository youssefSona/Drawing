package com.example.drawing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class CustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val dotRadius = 5f // Radius of the dot
    private val dotPaint = Paint().apply {
        color = Color.BLACK // Color of the dot
        isAntiAlias = true // Smooth edges of the dot
    }

    private val dots = mutableListOf<Pair<Float, Float>>() // List to store the dots

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        dots.forEach {
            canvas?.drawCircle(
                it.first,
                it.second,
                dotRadius,
                dotPaint
            ) // Draw all the dots in the list
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {

                dots.add(
                    Pair(event.x, event.y)
                ) // Add the coordinates of the clicked point to the list
                invalidate() // Redraw the canvas to show the new dot

                if (dots.size > 3) {
                    val lastDot = dots[dots.size - 1]
                    val secondLastDot = dots[Random.nextInt(0, 3)]
                    val xMid = (lastDot.first + secondLastDot.first) / 2
                    val yMid = (lastDot.second + secondLastDot.second) / 2
                    dots.add(Pair(xMid, yMid)) // Add the middle point as a new dot to the list
                    invalidate() // Redraw the canvas to show the new dot

                    var i = 0
                    Thread {
                        while (i < 25000) {
                            val lastDot = dots[dots.size - 1]
                            val secondLastDot =
                                dots[Random.nextInt(0, 3)]
                            val xMid = (lastDot.first + secondLastDot.first) / 2
                            val yMid = (lastDot.second + secondLastDot.second) / 2
                            dots.add(
                                Pair(
                                    xMid,
                                    yMid
                                )
                            ) // Add the middle point as a new dot to the list
                            invalidate() // Redraw the canvas to show the new dot
                            Thread.sleep(40)
                            i++
                        }

                    }.start()
                }


            }
        }
        return true
    }
}
