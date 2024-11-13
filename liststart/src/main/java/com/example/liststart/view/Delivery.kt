package com.example.liststart.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liststart.R
import com.example.liststart.adapter.DispatchAdapter
import com.example.liststart.viewmodel.DispatchViewModel
import com.example.liststart.datasource.DataSourceProvider
import com.example.liststart.fragments.SettingsFragment
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class Delivery : AppCompatActivity() {

    // CameraX 관련 변수 초기화
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var imageAnalysis: ImageAnalysis
    private lateinit var previewView: PreviewView // 프리뷰 표시할 뷰

    // ViewModel 초기화 (DataSourceProvider에서 ViewModelFactory 사용)
    private val dispatchViewModel: DispatchViewModel by viewModels {
        DataSourceProvider.dispatchViewModelFactory
    }

    // RecyclerView 및 어댑터 초기화
    private lateinit var dispatchAdapter: DispatchAdapter
    private lateinit var dispatchRecyclerView: RecyclerView
    private var warehouseNo: Int = 0 // 전달받을 창고 번호

    // 출고를 담당할 텍스트뷰
    private lateinit var selectWarehouseTextView: TextView
    private lateinit var warehouseIcon: ImageView
    private lateinit var navImage2: ImageView // 프래그먼트 전환 버튼

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        // 인텐트에서 창고 번호 받기
        warehouseNo = intent.getIntExtra("warehouse_no", 0)
        Log.d("myLog", "Received warehouseNo: $warehouseNo")  // 받은 창고 번호 로그

        val warehouseNameTextView = findViewById<TextView>(R.id.warehouseName)
        warehouseNameTextView.text = getWarehouseTitle(warehouseNo)

        // PreviewView 초기화 및 숨김 설정
        previewView = findViewById(R.id.cameraPreviewView)
        previewView.visibility = View.GONE // 기본적으로 숨겨둠

        // QR 스캐너 버튼 설정
        val qrScannerButton = findViewById<ImageView>(R.id.Qrscanner)
        qrScannerButton.setOnClickListener {
            checkCameraPermissionAndOpenScanner()
        }

        // RecyclerView 초기화 및 레이아웃 설정
        dispatchRecyclerView = findViewById(R.id.warehouseRecyclerView)
        dispatchRecyclerView.layoutManager = LinearLayoutManager(this)
        dispatchAdapter = DispatchAdapter(emptyList())
        dispatchRecyclerView.adapter = dispatchAdapter

        // selectWarehouseTextView 초기화
        selectWarehouseTextView = findViewById(R.id.selectWarehouseTextView)
        selectWarehouseTextView.isEnabled = false // 초기에는 비활성화
        selectWarehouseTextView.isClickable = false // 클릭 불가능 상태
        warehouseIcon = findViewById(R.id.warehouseIcon)
        warehouseIcon.isEnabled = false
        warehouseIcon.isClickable = false

        // navImage2 버튼 초기화 및 클릭 리스너 설정
        navImage2 = findViewById(R.id.navImage2)
        navImage2.setOnClickListener {
            openSettingsFragment()
        }


        // selectWarehouseTextView 클릭 이벤트 처리
        selectWarehouseTextView.setOnClickListener {
            if (dispatchAdapter.allItemsChecked()) {
                // 모든 아이템이 체크된 경우
                val dispatchNos = dispatchAdapter.getDispatchNos()
                dispatchViewModel.updateDispatch(dispatchNos)
            } else {
                // 체크되지 않은 아이템이 있는 경우
                Toast.makeText(this, "모든 상품을 스캔해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // ViewModel을 통해 Dispatch 리스트 데이터를 관찰하고 UI 업데이트
        observeDispatchList()
        dispatchViewModel.loadDispatchList(warehouseNo) // 서버에서 Dispatch 데이터

        // 아이템 체크 상태 변경 시 selectWarehouseTextView 상태 업데이트
        dispatchAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                updateSelectWarehouseTextViewState()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                updateSelectWarehouseTextViewState()
            }
        })

        // 출고 업데이트 결과 관찰
        dispatchViewModel.updateResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "출고 완료되었습니다.", Toast.LENGTH_SHORT).show()
                finish() // 액티비티 종료 또는 데이터 갱신
            } else {
                Toast.makeText(this, "출고 처리에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 프래그먼트 전환 메서드
    private fun openSettingsFragment() {
        val settingsFragment = SettingsFragment(warehouseNo)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, settingsFragment)
            .addToBackStack(null)
            .commit()

        findViewById<View>(R.id.fragment_container).visibility = View.VISIBLE
    }
    override fun onBackPressed() {
        // 백스택에 프래그먼트가 있는지 확인
        if (supportFragmentManager.backStackEntryCount > 0) {
            // 프래그먼트가 있을 경우 뒤로 가기
            supportFragmentManager.popBackStack()
            // 프래그먼트가 제거된 후 컨테이너를 숨김
            findViewById<View>(R.id.fragment_container).visibility = View.GONE
        } else {
            // 백스택에 프래그먼트가 없으면 기본 동작 (액티비티 종료 또는 이전 화면)
            super.onBackPressed()
        }
    }


    // Dispatch 리스트 데이터 관찰하여 RecyclerView에 적용
    private fun observeDispatchList() {
        val emptyTextView = findViewById<TextView>(R.id.emptyTextView1) // emptyTextView1 연결

        dispatchViewModel.dispatchList.observe(this) { dispatchList ->
            if (dispatchList != null && dispatchList.isNotEmpty()) {
                // 상품명을 기준으로 오름차순 정렬
                val sortedList = dispatchList.sortedBy { it.productNm }
                dispatchAdapter.updateData(sortedList) // 정렬된 데이터를 RecyclerView에 갱신
                Log.d("myLog", "Dispatch list updated with ${sortedList.size} items")

                // 데이터가 있을 때는 emptyTextView를 숨김
                emptyTextView.visibility = View.GONE
            } else {
                Log.e("myLog", "No data found or data is empty")

                // 데이터가 없을 때 emptyTextView를 보이게 설정
                emptyTextView.visibility = View.VISIBLE
            }
        }
    }



    // selectWarehouseTextView의 상태를 업데이트하는 함수
    private fun updateSelectWarehouseTextViewState() {
        val allChecked = dispatchAdapter.allItemsChecked()
        selectWarehouseTextView.isEnabled = allChecked
        selectWarehouseTextView.isClickable = allChecked
        selectWarehouseTextView.alpha = if (allChecked) 1.0f else 0.5f // 시각적으로 비활성화 상태 표현
        warehouseIcon.alpha = if (allChecked) 1.0f else 0.5f
    }

    // 창고 번호에 따라 창고 이름 반환
    private fun getWarehouseTitle(warehouseNo: Int): String {
        return when (warehouseNo) {
            1 -> "※ 금일 출고 목록(부산 창고) ※"
            2 -> "※ 금일 출고 목록(인천 창고) ※"
            3 -> "※ 금일 출고 목록(본사 창고) ※"
            4 -> "※ 금일 출고 목록(천안 창고) ※"
            else -> "※ 금일 출고 목록 ※"
        }
    }

    // 카메라 권한 확인 및 스캐너 열기
    private fun checkCameraPermissionAndOpenScanner() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            toggleCameraPreviewVisibility() // 카메라 프리뷰 표시
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    // 카메라 프리뷰의 표시 상태를 토글 (보이기/숨기기)
    private fun toggleCameraPreviewVisibility() {
        if (previewView.visibility == View.GONE) {
            previewView.visibility = View.VISIBLE
            startCamera() // 카메라 시작
        } else {
            previewView.visibility = View.GONE
            cameraProvider.unbindAll() // 카메라 정지
        }
    }

    // 카메라 시작 함수
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            // Preview 객체를 생성합니다.
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // PreviewView와 연결합니다.
            preview.setSurfaceProvider(previewView.surfaceProvider)

            // Analyzer 바인딩
            bindAnalyzer()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
                Log.d("myLog", "Preview and Analyzer bound successfully")
            } catch (e: Exception) {
                Log.e("myLog", "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    // QR 코드 분석기 바인딩
    private fun bindAnalyzer() {
        imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this)) { imageProxy ->
            processImageProxy(imageProxy) // QR 코드 분석 처리
        }
    }

    // QR 코드 분석을 위한 함수
    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val qrCodeData = barcode.rawValue
                        if (qrCodeData != null) {
                            handleQRCodeData(qrCodeData) // QR 코드 데이터 처리
                            // QR 코드가 감지되면 카메라를 숨기고 중지
                            previewView.visibility = View.GONE
                            cameraProvider.unbindAll()
                        }
                        break // 첫 번째 QR 코드만 처리하고 중지
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
                .addOnCompleteListener {
                    imageProxy.close() // 처리 완료 후 리소스 해제
                }
        } else {
            imageProxy.close()
        }
    }

    // QR 코드 데이터를 처리하여 RecyclerView 아이템의 체크 상태 업데이트
    private fun handleQRCodeData(productName: String) {
        dispatchAdapter.updateItemCheckStatus(productName) // 어댑터의 체크 상태 업데이트 메서드 호출
        Toast.makeText(this, "QR Code: $productName 확인됨", Toast.LENGTH_SHORT).show()
        Log.d("myLog", "QR Code matched with item: $productName")
    }

    // 카메라 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            toggleCameraPreviewVisibility() // 카메라 권한 허용 시 카메라 프리뷰 표시
        } else {
            Toast.makeText(this, "Camera permission is required to scan QR code", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 1001 // 카메라 권한 요청 코드 상수
    }
}
