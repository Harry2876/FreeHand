package com.example.freehand

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
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

        mBrushSize = 20.toFloat()
    }


    internal inner class CustomPath(var color : Int, var brushThickness : Float) : Path() {

    }


}