package com.example.freehand

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class DrawingView(context : Context, attributes : AttributeSet) : View(context,attributes) {

    //creating variables for functionality

    //for drawing path
    private var mDrawPath : CustomPath? = null
    private var mCanvasBitmap : Bitmap? = null

    //for paint
    private var mDrawPaint : Paint? = null
    private var mCanvasPaint : Paint? = null

    //for brush size
    private var mBrushSize : Float = 0.toFloat()

    //for default color
    private var color = Color.BLACK

    //for creating canvas to draw
    private var canvas : Canvas? = null

    init {
        setUpDrawing()
    }

    //creating a function for drawing
    private fun setUpDrawing(){
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND

        //dither - flag that applies an underline decoration to drawn text
        mCanvasPaint = Paint(Paint.DITHER_FLAG)

        mBrushSize = 10.toFloat()
    }

    //we need to display bitmap on different screen sizes on which canvas will be there
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)

        //setting up canvas
        canvas = Canvas(mCanvasBitmap!!)

    }

    //if we want to draw
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)

        if (!mDrawPath!!.isEmpty){
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawPaint!!.color = mDrawPath!!.color
        //for drawing path
        canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize

                mDrawPath!!.reset()
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.moveTo(touchX,touchY)
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (touchY != null) {
                    if (touchX != null) {
                        mDrawPath!!.lineTo(touchX,touchY)
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }


        invalidate()

        return true
    }



    internal inner class CustomPath(var color : Int, var brushThickness : Float) : Path() {

    }


}