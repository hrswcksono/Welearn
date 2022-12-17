package com.tugasakhir.welearn.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import com.tugasakhir.welearn.ml.AngkaModel
import com.tugasakhir.welearn.ml.HurufModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Predict {
    fun PredictHuruf(context : Context, bitmap: Bitmap, answer: String) : Int {
        val byteBuffer = bitmapToByteBuffer(bitmap)
        val model = HurufModel.newInstance(context)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val result = resultLetter(outputFeature0.floatArray.asList().indexOf(outputFeature0.floatArray.max()))

        // Releases model resources if no longer used.
        model.close()
        if (result.toString() == answer) {
            return 10
        }
        return 0
    }

    fun PredictAngka(context : Context, bitmap: Bitmap, answer: String) : Int {
        val byteBuffer = bitmapToByteBuffer(inverseBitmapColors(bitmap))
        val model = AngkaModel.newInstance(context)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val result = outputFeature0.floatArray.asList().indexOf(outputFeature0.floatArray.max())

        // Releases model resources if no longer used.
        model.close()
        if (result.toString() == answer) {
            return 10
        }
        return 0
    }

    private fun bitmapToByteBuffer(bitmap: Bitmap) : ByteBuffer{
        val bitmap1 = Bitmap.createScaledBitmap((bitmap), 224, 224, true)
        val input = ByteBuffer.allocateDirect(224*224*3*4).order(ByteOrder.nativeOrder())
        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val px = bitmap1.getPixel(x, y)

                // Get channel values from the pixel value.
                val r = Color.red(px)
                val g = Color.green(px)
                val b = Color.blue(px)

                // Normalize channel values to [-1.0, 1.0]. This requirement depends on the model.
                // For example, some models might require values to be normalized to the range
                // [0.0, 1.0] instead.
                val rf = (r) / 255f
                val gf = (g) / 255f
                val bf = (b) / 255f

                input.putFloat(rf)
                input.putFloat(gf)
                input.putFloat(bf)
            }
        }
        return input
    }

    private fun inverseBitmapColors(bitmap: Bitmap) : Bitmap {
        for (i in 0 until bitmap.width) {
            for (j in 0 until bitmap.height) {
                bitmap.setPixel(i, j, bitmap.getPixel(i, j) xor 0x00ffffff)
            }
        }
        return bitmap
    }

    private fun resultLetter(index: Int) : Char {
        return when(index){
            0 -> 'a'
            1 -> 'b'
            2 -> 'c'
            3 -> 'd'
            4 -> 'e'
            5 -> 'f'
            6 -> 'g'
            7 -> 'h'
            8 -> 'i'
            9 -> 'j'
            10 -> 'k'
            11 -> 'l'
            12 -> 'm'
            13 -> 'n'
            14 -> 'o'
            15 -> 'p'
            16 -> 'q'
            17 -> 'r'
            18 -> 's'
            19 -> 't'
            20 -> 'u'
            21 -> 'v'
            22 -> 'w'
            23 -> 'x'
            24 -> 'y'
            25 -> 'z'
            else -> {
                '1'
            }
        }
    }
}