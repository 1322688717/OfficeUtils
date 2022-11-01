package com.example.officeutils.ui.fragment.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.databinding.FragmentNotificationsBinding


class MyFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var viewModel: MyViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myViewModel =
            ViewModelProvider(this).get(MyViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initView()
        initOnclick()

        return root
    }

    private fun initOnclick() {

    }

    private fun initView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}