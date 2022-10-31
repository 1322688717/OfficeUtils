package com.example.officeutils.ui.fragment.home

import android.R.attr.banner
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.officeutils.databinding.FragmentHomeBinding
import com.example.officeutils.utils.RouterUtil
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
    }

    private fun initBanner() {
//        binding.banner.setAdapter(object : BannerImageAdapter<DataBean?>(DataBean.getTestData3()) {
//            override fun onBindView(
//                holder: BannerImageHolder,
//                data: DataBean,
//                position: Int,
//                size: Int
//            ) {
//                //图片加载自己实现
//                Glide.with(holder.itemView)
//                    .load(data.imageUrl)
//                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
//                    .into(holder.imageView)
//            }
//        })
//            .addBannerLifecycleObserver(this) //添加生命周期观察者
//            .setIndicator(CircleIndicator(this))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}