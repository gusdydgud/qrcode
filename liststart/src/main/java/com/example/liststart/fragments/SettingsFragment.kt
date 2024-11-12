package com.example.liststart.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liststart.R
import com.example.liststart.adapter.DoneDispatchAdapter
import com.example.liststart.viewmodel.DispatchViewModel
import com.example.liststart.datasource.DataSourceProvider

class SettingsFragment(private val warehouseNo: Int) : Fragment() {

    private val dispatchViewModel: DispatchViewModel by viewModels {
        DataSourceProvider.dispatchViewModelFactory
    }

    private lateinit var doneDispatchAdapter1: DoneDispatchAdapter
    private lateinit var doneDispatchAdapter2: DoneDispatchAdapter
    private lateinit var doneDispatchAdapter3: DoneDispatchAdapter

    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView

    private lateinit var loadMoreTextView1: TextView
    private lateinit var loadMoreTextView2: TextView
    private lateinit var loadMoreTextView3: TextView

    private lateinit var emptyTextView1: TextView
    private lateinit var emptyTextView2: TextView
    private lateinit var emptyTextView3: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_delivery_end, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.navImage1).setOnClickListener {
            parentFragmentManager.popBackStack()
            requireActivity().findViewById<View>(R.id.fragment_container).visibility = View.GONE
        }

        // RecyclerView 및 어댑터 초기화
        recyclerView1 = view.findViewById(R.id.dispatchRecyclerView1)
        recyclerView2 = view.findViewById(R.id.dispatchRecyclerView2)
        recyclerView3 = view.findViewById(R.id.dispatchRecyclerView3)

        loadMoreTextView1 = view.findViewById(R.id.loadMoreTextView1)
        loadMoreTextView2 = view.findViewById(R.id.loadMoreTextView2)
        loadMoreTextView3 = view.findViewById(R.id.loadMoreTextView3)

        // "출고 기록이 없습니다." 메시지를 위한 TextView 초기화
        emptyTextView1 = view.findViewById(R.id.emptyTextView1)
        emptyTextView2 = view.findViewById(R.id.emptyTextView2)
        emptyTextView3 = view.findViewById(R.id.emptyTextView3)

        doneDispatchAdapter1 = DoneDispatchAdapter(emptyList())
        doneDispatchAdapter2 = DoneDispatchAdapter(emptyList())
        doneDispatchAdapter3 = DoneDispatchAdapter(emptyList())

        setupRecyclerView(recyclerView1, doneDispatchAdapter1)
        setupRecyclerView(recyclerView2, doneDispatchAdapter2)
        setupRecyclerView(recyclerView3, doneDispatchAdapter3)

        setupLoadMoreFunctionality()

        // ViewModel의 데이터 관찰
        observeData()

        // 데이터 로드
        dispatchViewModel.loadDoneDispatchList(warehouseNo) // 전달받은 창고 번호 사용
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: DoneDispatchAdapter) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun setupLoadMoreFunctionality() {
        loadMoreTextView1.setOnClickListener {
            doneDispatchAdapter1.showAllItems()
            recyclerView1.layoutParams = recyclerView1.layoutParams.apply {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            loadMoreTextView1.visibility = View.GONE
        }

        loadMoreTextView2.setOnClickListener {
            doneDispatchAdapter2.showAllItems()
            recyclerView2.layoutParams = recyclerView2.layoutParams.apply {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            loadMoreTextView2.visibility = View.GONE
        }

        loadMoreTextView3.setOnClickListener {
            doneDispatchAdapter3.showAllItems()
            recyclerView3.layoutParams = recyclerView3.layoutParams.apply {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            loadMoreTextView3.visibility = View.GONE
        }
    }

    private fun observeData() {
        // 1일 전 데이터 관찰
        dispatchViewModel.doneDispatchList1Day.observe(viewLifecycleOwner) { doneDispatchList1 ->
            doneDispatchAdapter1.updateData(doneDispatchList1)
            loadMoreTextView1.visibility = if (doneDispatchList1.size > 4) View.VISIBLE else View.GONE
            emptyTextView1.visibility = if (doneDispatchList1.isEmpty()) View.VISIBLE else View.GONE
        }

        // 2일 전 데이터 관찰
        dispatchViewModel.doneDispatchList2Days.observe(viewLifecycleOwner) { doneDispatchList2 ->
            doneDispatchAdapter2.updateData(doneDispatchList2)
            loadMoreTextView2.visibility = if (doneDispatchList2.size > 4) View.VISIBLE else View.GONE
            emptyTextView2.visibility = if (doneDispatchList2.isEmpty()) View.VISIBLE else View.GONE
        }

        // 3일 전 데이터 관찰
        dispatchViewModel.doneDispatchList3Days.observe(viewLifecycleOwner) { doneDispatchList3 ->
            doneDispatchAdapter3.updateData(doneDispatchList3)
            loadMoreTextView3.visibility = if (doneDispatchList3.size > 4) View.VISIBLE else View.GONE
            emptyTextView3.visibility = if (doneDispatchList3.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}
