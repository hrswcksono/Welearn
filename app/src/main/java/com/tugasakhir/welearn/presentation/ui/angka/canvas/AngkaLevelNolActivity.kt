package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelNolBinding
import com.tugasakhir.welearn.domain.model.Soal
import dev.abhishekkumar.canvasview.CanvasView

class AngkaLevelNolActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityAngkaLevelNolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelNolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        binding.levelNolAngkaBack.setOnClickListener {
            onBackPressed()
        }

        drawOne()
        drawTwo()
    }

    private fun show(data: Soal){
        binding.soalAngkaDipilih.setText(data.keterangan)
        binding.levelAngkaKe.setText("Level ke ${data.id_level}")
    }

    private fun drawOne(){
        val canvasView = findViewById<CanvasView>(R.id.canvasView)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawTwo(){
        val canvasView = findViewById<CanvasView>(R.id.canvasView1)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }
}