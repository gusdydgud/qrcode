package com.example.liststart.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.liststart.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 카메라 권한 확인 및 요청
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        // 카메라 권한이 이미 허용된 경우 다음 액티비티로 이동
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            goToNextActivity()
        } else {
            // 카메라 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우
                goToNextActivity()
            } else {
                // 권한이 거부된 경우 메시지 표시 및 앱 종료
                Toast.makeText(this, "Camera permission is required to use this app.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun goToNextActivity() {
        // 권한을 받으면 다음 액티비티로 이동
        startActivity(Intent(this, WarehouseList::class.java))
        finish() // 스플래시 화면 종료
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 1001
    }
}
