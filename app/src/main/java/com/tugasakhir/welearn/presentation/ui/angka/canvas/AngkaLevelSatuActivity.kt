package com.tugasakhir.welearn.presentation.ui.angka.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelSatuBinding
import com.tugasakhir.welearn.domain.model.Soal
import dev.abhishekkumar.canvasview.CanvasView

class AngkaLevelSatuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityAngkaLevelSatuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelSatuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.levelSatuAngkaBack.setOnClickListener {
            onBackPressed()
        }

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        drawOne()
    }

    private fun show(data: Soal){
        binding.soalAngkaDipilih.setText(data.keterangan)
        binding.levelAngkaKe.setText("Level ke ${data.id_level}")
        binding.tvSoalAngkaSatu.setText(data.soal)
    }

    private fun drawOne(){
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelSatuAngka)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }
}