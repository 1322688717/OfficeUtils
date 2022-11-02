package com.example.officeutils.ui


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.officeutils.R
import com.example.officeutils.databinding.ActivityBottomNavigationBinding
import com.example.officeutils.utils.StatusBarUtil
import com.google.android.material.bottomnavigation.BottomNavigationView


class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        StatusBarUtil.initStatusBarNight(this)

        //获取底部导航图标颜色，根据图标颜色设置文字颜色
        //获取底部导航图标颜色，根据图标颜色设置文字颜色
        val resource: Resources = resources
        @SuppressLint("ResourceType") val csl: ColorStateList =
            resource.getColorStateList(com.example.officeutils.R.drawable.navigation_bottom_btn_selected_font_color)

        binding.navView.setItemTextColor(csl)


    }
}