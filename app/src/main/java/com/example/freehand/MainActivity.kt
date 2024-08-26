package com.example.freehand

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {


    private var drawingView : DrawingView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawingView = findViewById(R.id.drawingView)
        drawingView?.setSizeForBrush(30.toFloat())

        val ibBrush : ImageButton = findViewById(R.id.ib_brush)
        ibBrush.setOnClickListener {
            showBrushSizeChooserDialog()
        }






    }

    //method for brushsize chooser dialog
    private fun showBrushSizeChooserDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Set Brush Size : ")

        //setting small button
        val smallBtn : ImageButton = brushDialog.findViewById(R.id.ib_small_brush)
        smallBtn.setOnClickListener {
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }

        val medBtn : ImageButton = brushDialog.findViewById(R.id.ib_medium_brush)
        medBtn.setOnClickListener {
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }

        val larBtn : ImageButton = brushDialog.findViewById(R.id.ib_large_brush)
        larBtn.setOnClickListener {
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }

        //setting up slider and preview
        val slider : Slider = brushDialog.findViewById(R.id.brushSizeSlider)
        val preview : ImageView = brushDialog.findViewById(R.id.brushSizePreview)

        // Method to update the brush size preview
        fun updateBrushSizePreview(size: Int) {
            // Convert size from dp to pixels
            val displayMetrics = resources.displayMetrics
            val sizeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), displayMetrics).toInt()

            // Update ImageView layout parameters to adjust its size
            val layoutParams = preview.layoutParams
            layoutParams.width = sizeInPx
            layoutParams.height = sizeInPx
            preview.layoutParams = layoutParams
        }

        // Set the initial brush size from Slider's default value
        updateBrushSizePreview(slider.value.toInt())
        drawingView?.setSizeForBrush(slider.value)

        // Listen for Slider changes
        slider.addOnChangeListener { slider, value, fromUser ->
            // Adjust the brush size based on Slider value
            updateBrushSizePreview(value.toInt())
            drawingView?.setSizeForBrush(value)
        }

        brushDialog.show()
    }



}