package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityHurufLevelTigaBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelNolActivity
import dev.abhishekkumar.canvasview.CanvasView

class HurufLevelTIgaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityHurufLevelTigaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelTigaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.levelTigaHurufBack.setOnClickListener {
            onBackPressed()
        }

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        drawOne()
        drawTwo()
        drawThree()
        drawFour()
        drawFive()
        drawSix()
        drawSeven()
        drawEight()
        drawNine()
    }

    private fun show(data: Soal){
        binding.soalHurufDipilih.setText(data.keterangan)
        binding.levelHurufKe.setText("Level ke ${data.id_level}")
    }

    private fun drawOne(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufone)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawTwo(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHuruftwo)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawThree(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufthree)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawFour(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHuruffour)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawFive(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHuruffive)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawSix(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufsix)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawSeven(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufseven)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawEight(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufeight)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawNine(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufnine)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }
}