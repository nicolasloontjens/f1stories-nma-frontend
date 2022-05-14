package be.howest.nicolas.loontjens.f1stories.addrace

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import be.howest.nicolas.loontjens.f1stories.MainActivity
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.AddRace
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.*
import java.lang.Exception

class CameraInputAnalyzer(contxt: Context) : ImageAnalysis.Analyzer {

    val con = contxt;


    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val img = image.image
        if(img != null){
            val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE, Barcode.FORMAT_CODE_128)
                .build()

            val scanner = BarcodeScanning.getClient(options)

            scanner.process(inputImage)
                .addOnSuccessListener { codes ->
                    handleResult(codes[0])
                }
                .addOnFailureListener {

                }
        }
        image.close()
    }

    private fun handleResult(code: Barcode){
        var uid: Int? = 0
        var token: String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            uid = UserRoomDatabase.INSTANCE?.UserDao()?.getUid()
            token = UserRoomDatabase.INSTANCE?.UserDao()?.getToken()
        }
        Thread.sleep(1000)
        try{
            CoroutineScope(Dispatchers.IO).launch {
                if (uid != null) {
                    if (token != null) {
                        code.rawValue?.let { AddRace(it) }?.let {
                            F1StoriesApi.retrofitService.addRace(uid!!,
                                it, token!!
                            )
                        }
                    }
                }
            }

            MaterialAlertDialogBuilder(con)
                .setMessage("Added race!")
                .setPositiveButton("Continue"){_,_ ->
                    val intent = Intent (con, MainActivity::class.java)
                    con.startActivity(intent)
                }
                .show()
        }catch (ex: Exception){
            //this is not catching the 401 exception, i dont know why
            ex.printStackTrace()
        }
    }
}