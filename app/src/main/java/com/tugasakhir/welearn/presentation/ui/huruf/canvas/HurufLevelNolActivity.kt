package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityHurufLevelNolBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelNolActivity
import dev.abhishekkumar.canvasview.CanvasView

class HurufLevelNolActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityHurufLevelNolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelNolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.levelNolHurufBack.setOnClickListener {
            onBackPressed()
        }

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        drawOne()
    }


    private fun show(data: Soal){
        binding.soalHurufDipilih.setText(data.keterangan)
        binding.levelHurufKe.setText("Level ke ${data.id_level}")
    }

    private fun drawOne(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelNolHuruf)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }
}