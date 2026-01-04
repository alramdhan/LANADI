package io.alramdhan.lanadi.ui.home.feature

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController

@Composable
fun CameraQRScanner(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CameraPreview()
        ScannerOverlay()
//            AndroidView(
//                factory = { androidViewContext ->
//                    PreviewView(androidViewContext).apply {
//                        this.scaleType = PreviewView.ScaleType.FILL_START
//                        layoutParams = ViewGroup.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT
//                        )
//                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
//                    }
//                },
//                modifier = Modifier.fillMaxSize(),
//                update = { previewView ->
//                    val cameraSelector = CameraSelector.Builder()
//                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
//                        .build()
//                    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
//                    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(context)
//
//                    cameraProviderFuture.addListener({
//                        preview = Preview.Builder().build().also {
//                            it.surfaceProvider = previewView.surfaceProvider
//                        }
//
//                        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//                        val barcodeAnalyzer = BarCodeAnalyzer()
//                        val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
//                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                            .build()
//                            .also {
//                                it.setAnalyzer(cameraExecutor, barcodeAnalyzer)
//                            }
//                        try {
//                            cameraProvider.unbindAll()
//                            cameraProvider.bindToLifecycle(
//                                lifecycleOwner,
//                                cameraSelector,
//                                preview,
//                                imageAnalysis
//                            )
//                        } catch(e: Exception) {
//                            Log.d("CameraScanner", "CameraPreview: ${e.localizedMessage}")
//                        }
//                    }, ContextCompat.getMainExecutor(context))
//                }
//            )
    }
}

@Composable
private fun CameraPreview() {
    val lifecycleOwner = LocalLifecycleOwner.current
    var scannedCode by remember { mutableStateOf<String?>(null) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().apply {
                    surfaceProvider = previewView.surfaceProvider
                }
                val analyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build().also {
                        it.setAnalyzer(
                            ContextCompat.getMainExecutor(ctx),
                            QRCodeAnalyzer { qrCode ->
                                Log.d("Scanned", qrCode)
                                scannedCode = qrCode
                            }
                        )
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_FRONT_CAMERA,
                        preview,
                        analyzer
                    )
                } catch(e: Exception) {
                    Log.d("Camera Scanner", "Use case binding failed ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(ctx))
            previewView
        },
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun ScannerOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "scanner_anim")
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_position"
    )

    Box(
        modifier = Modifier.fillMaxSize()
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier.size(300.dp)
                .border(2.dp, Color.White, RoundedCornerShape(12.dp))
        ) {
            Canvas(Modifier.fillMaxSize()) {
                val lasserHeight = 4.dp.toPx()
                val currentY = size.height * animatedProgress

                val brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Red.copy(alpha = .7f),
                        Color.Transparent
                    ),
                    startY = currentY - 20f,
                    endY = currentY + 20f
                )

                drawRect(
                    brush = brush,
                    topLeft = Offset(0f, currentY),
                    size = Size(size.width, lasserHeight)
                )
            }
        }

        Text(
            "Arahkan kamera ke QR Code",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
        )
    }
}