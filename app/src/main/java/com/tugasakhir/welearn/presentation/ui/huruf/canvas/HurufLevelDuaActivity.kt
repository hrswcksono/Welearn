package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityHurufLevelDuaBinding
import com.tugasakhir.welearn.domain.model.Soal
import dev.abhishekkumar.canvasview.CanvasView

class HurufLevelDuaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityHurufLevelDuaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelDuaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.levelDuaHurufBack.setOnClickListener {
            onBackPressed()
        }

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        drawOne()
        drawTwo()
        drawThree()
        drawFour()
        drawFive()
    }

    private fun show(data: Soal){
        binding.soalHurufDipilih.setText(data.keterangan)
        binding.levelHurufKe.setText("Level ke ${data.id_level}")
    }

    private fun drawOne(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelDuaHurufone)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawTwo(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelDuaHuruftwo)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawThree(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelDuaHurufthree)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawFour(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelDuaHuruffour)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawFive(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelDuaHuruffive)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }
}