package com.example.officeutils.ui.fragment.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MyFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var viewModel: MyViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         viewModel =
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
        GlobalScope.launch {
            viewModel.getUserInfo(requireActivity())
        }


        viewModel.userInfo.observe(viewLifecycleOwner){ it->
            binding.tvNickName.text = it.data.toString()

            binding.tvNickName.text = it?.data?.records?.get(0)?.username.toString()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}