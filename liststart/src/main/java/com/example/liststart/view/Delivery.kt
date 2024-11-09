package com.example.liststart.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liststart.R
import com.example.liststart.adapter.DispatchAdapter
import com.example.liststart.viewmodel.DispatchViewModel
import com.example.liststart.datasource.DataSourceProvider

import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class Delivery : AppCompatActivity() {


    // CameraX와 관련된 변수
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var imageAnalysis: ImageAnalysis

    // ViewModel 초기화
    private val dispatchViewModel: DispatchViewModel by viewModels {
        DataSourceProvider.dispatchViewModelFactory
    }

    // RecyclerView 및 어댑터 초기화
    private lateinit var dispatchAdapter: DispatchAdapter
    private lateinit var dispatchRecyclerView: RecyclerView
    private var warehouseNo: Int = 0 // 전달받을 창고 번호

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

// 인텐트에서 창고 번호 받기
        warehouseNo = intent.getIntExtra("warehouse_no", 0)
        // QR 스캐너 버튼 설정

        val qrScannerButton = findViewById<ImageView>(R.id.Qrscanner)
        qrScannerButton.setOnClickListener {
            checkCameraPermissionAndOpenScanner()
        }


        // RecyclerView 초기화 및 레이아웃 설정
        dispatchRecyclerView = findViewById(R.id.warehouseRecyclerView)
        dispatchRecyclerView.layoutManager = LinearLayoutManager(this)

        // ViewModel을 통해 Dispatch 리스트 데이터를 관찰하고 UI 업데이트
        observeDispatchList()
        dispatchViewModel.loadDispatchList(warehouseNo) // Dispatch 데이터 로드
    }

    // Dispatch 리스트 데이터 관찰하여 RecyclerView에 적용
    private fun observeDispatchList() {
        dispatchViewModel.dispatchList.observe(this) { dispatchList ->
            if (dispatchList != null) {
                dispatchAdapter.updateData(dispatchList) // 데이터 갱신 메서드 호출
            } else {
                Toast.makeText(this, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // 카메라 권한 확인 및 스캐너 열기

    private fun checkCameraPermissionAndOpenScanner() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }


    // 카메라 시작 함수

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindAnalyzer()
        }, ContextCompat.getMainExecutor(this))
    }


    // QR 코드 분석기 바인딩

    private fun bindAnalyzer() {
        imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this)) { imageProxy ->
            processImageProxy(imageProxy)
        }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // QR 코드 분석을 위한 함수
    @androidx.annotation.OptIn(ExperimentalGetImage::class)

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val qrCodeData = barcode.rawValue
                        Toast.makeText(this, "QR Code: $qrCodeData", Toast.LENGTH_SHORT).show()
                        cameraProvider.unbindAll() // QR 코드가 스캔되면 카메라 정지
                        break // 첫 번째 QR 코드만 처리하고 중지
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }


    // 카메라 권한 요청 결과 처리

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            Toast.makeText(this, "Camera permission is required to scan QR code", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 1001
    }
}
