package com.example.liststart.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.liststart.R

class WarehouseList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse_list)

        // 창고 버튼 TextView 찾기
        val headOfficeButton = findViewById<TextView>(R.id.headOfficeWarehouseTextView)
        val incheonButton = findViewById<TextView>(R.id.incheonWarehouseButton)
        val busanButton = findViewById<TextView>(R.id.busanWarehouseButton)
        val cheonanButton = findViewById<TextView>(R.id.cheonanWarehouseButton)

        // 클릭 리스너 설정하여 DeliveryActivity로 이동하면서 창고 이름 전달
        headOfficeButton.setOnClickListener {
            openDeliveryActivity("본사 창고")
        }

        incheonButton.setOnClickListener {
            openDeliveryActivity("인천 창고")
        }

        busanButton.setOnClickListener {
            openDeliveryActivity("부산 창고")
        }

        cheonanButton.setOnClickListener {
            openDeliveryActivity("천안 창고")
        }
    }

    private fun openDeliveryActivity(warehouseName: String) {
        // DeliveryActivity로 Intent 생성
        val intent = Intent(this, Delivery::class.java)
        // 창고 이름 데이터를 Intent에 추가
        intent.putExtra("warehouse_name", warehouseName)
        // DeliveryActivity 시작
        startActivity(intent)
    }
}
