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

        val headOfficeButton = findViewById<TextView>(R.id.headOfficeWarehouseTextView)
        val incheonButton = findViewById<TextView>(R.id.incheonWarehouseButton)
        val busanButton = findViewById<TextView>(R.id.busanWarehouseButton)
        val cheonanButton = findViewById<TextView>(R.id.cheonanWarehouseButton)


        // 창고 선택 시 창고 번호 전달
        headOfficeButton.setOnClickListener { openDeliveryActivity(3) }
        incheonButton.setOnClickListener { openDeliveryActivity(2) }
        busanButton.setOnClickListener { openDeliveryActivity(1) }
        cheonanButton.setOnClickListener { openDeliveryActivity(4) }
    }

    private fun openDeliveryActivity(warehouseNo: Int) {
        val intent = Intent(this, Delivery::class.java)
        intent.putExtra("warehouse_no", warehouseNo)
        startActivity(intent)
    }
}
