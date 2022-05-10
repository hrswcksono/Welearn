package com.tugasakhir.welearn.presentation.ui.angka.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelDuaBinding
import com.tugasakhir.welearn.domain.model.Soal
import dev.abhishekkumar.canvasview.CanvasView

class AngkaLevelDuaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityAngkaLevelDuaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelDuaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.levelDuaAngkaBack.setOnClickListener {
            onBackPressed()
        }

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        drawOne()
    }

    private fun show(data: Soal){
        binding.soalAngkaDipilih.setText(data.keterangan)
        binding.levelAngkaKe.setText("Level ke ${data.id_level}")
        binding.tvSoalAngkaDua.setText(data.soal)
    }

    private fun drawOne(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelSatuHuruf)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }
}