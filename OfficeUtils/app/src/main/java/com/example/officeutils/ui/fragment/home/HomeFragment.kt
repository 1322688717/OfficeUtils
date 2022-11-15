package com.example.officeutils.ui.fragment.home

import android.R.attr.banner
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.R
import com.example.officeutils.adapter.AdapterBanner
import com.example.officeutils.databinding.FragmentHomeBinding
import com.example.officeutils.utils.RouterUtil
import com.youth.banner.indicator.CircleIndicator
import java.util.ArrayList


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

     var listBanner = ArrayList<Int>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initView()


        return root
    }

    private fun initView() {
        initBanner()
        onclickInit()

    }

    private fun onclickInit() {
        binding.imgPdfToWord.setOnClickListener {
            RouterUtil().goFileConversionActivity(requireActivity())
        }

        binding.imgWordToPdf.setOnClickListener {
            RouterUtil().goFileActivity(requireActivity())
        }
    }

    private fun initBanner() {
        listBanner.add(R.drawable.banner1)
        listBanner.add(R.drawable.banner2)
        listBanner.add(R.drawable.banner3)
        binding.banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter( AdapterBanner(listBanner))
            .setIndicator( CircleIndicator(requireActivity()));
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}