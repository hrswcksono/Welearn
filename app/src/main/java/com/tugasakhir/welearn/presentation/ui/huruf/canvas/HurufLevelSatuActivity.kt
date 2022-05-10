package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityHurufLevelSatuBinding
import com.tugasakhir.welearn.domain.model.Soal
import dev.abhishekkumar.canvasview.CanvasView

class HurufLevelSatuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityHurufLevelSatuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelSatuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.levelSatuHurufBack.setOnClickListener {
            onBackPressed()
        }

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        drawOne()
        drawTwo()
        drawThree()
    }

    private fun show(data: Soal){
        binding.soalHurufDipilih.setText(data.keterangan)
        binding.levelHurufKe.setText("Level ke ${data.id_level}")
    }

    private fun drawOne(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelSatuHurufone)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawTwo(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelSatuHuruftwo)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawThree(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelSatuHurufthree)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }
}