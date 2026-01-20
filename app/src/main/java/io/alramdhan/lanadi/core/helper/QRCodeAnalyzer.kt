package io.alramdhan.lanadi.core.helper

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class QRCodeAnalyzer(private val onCodeScanned: (String) -> Unit): ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image ?: return image.close()
        val img = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

        scanner.process(img)
            .addOnSuccessListener { barcodes ->
                barcodes.forEach { barcode ->
                    barcode.rawValue?.let { onCodeScanned(it) }
                }
            }
            .addOnCompleteListener { image.close() }
    }
}