package com.example.officeutils.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.R
import com.example.officeutils.base.BaseActivity
import com.example.officeutils.bean.RequestRegiseter
import com.example.officeutils.databinding.ActivityLoginBinding
import com.example.officeutils.databinding.ActivityRegisterBinding
import com.example.officeutils.utils.RouterUtil
import com.example.officeutils.utils.ToastUtil
import com.example.officeutils.viewmodel.LoginViewModel
import com.example.officeutils.viewmodel.RegisterViewModel

class RegisterActivity : BaseActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var viewModel: RegisterViewModel
    private var timer: LoginActivity.TimerUnit? = null
    var phoneNumber : String = ""
    var code : String = ""
    var password : String = ""
    var passwordAgagin : String = ""
    var mapRegiseter: MutableMap<String, String>? = null
    var rr = RequestRegiseter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        setContentView(binding.root)

        initView()
        onclick()

    }

    private fun onclick() {
        binding.getCode.setOnClickListener {
            if (phoneNumber.equals("")){
                ToastUtil().ToastLong(this,"请填写手机号")
            }
            ToastUtil().ToastLong(this,"发送成功")
            if (timer == null) {
                timer = LoginActivity.TimerUnit(binding.getCode)
            }
            timer?.startTime()
        }

        binding.btnRegister.setOnClickListener {
            phoneNumber = binding.edtAccount.text.toString()
            code = binding.edtCode.text.toString()
            password = binding.edtPassword.text.toString()
            passwordAgagin = binding.edtPasswordAgain.text.toString()

            if (phoneNumber != "" && code != "" && password != "" && passwordAgagin != ""){
                ToastUtil().ToastShort(this,"注册成功")
                RouterUtil().goLoginActivity(this)
                finish()
            }else{
                ToastUtil().ToastShort(this,"请输入手机号或密码")
            }
        }

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            phoneNumber = binding.edtAccount.text.toString()
            code = binding.edtCode.text.toString()
            password = binding.edtPassword.text.toString()
            passwordAgagin = binding.edtPasswordAgain.text.toString()
            Log.e("TAG","$phoneNumber")
            Log.e("TAG","$password")
            Log.e("TAG","$passwordAgagin")
            if (phoneNumber.isNotEmpty()&&code.isNotEmpty()&&password.isNotEmpty()&&passwordAgagin.isNotEmpty()){
                rr.phone = phoneNumber
                rr.password = password
                rr.username = phoneNumber
                Log.e("TAG","rr==$rr")
                viewModel.register(rr,RegisterActivity.this)
            }
        }
    }

    private fun initView() {

        binding.getCode.isEnabled = false

        binding.edtAccount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.getCode.isEnabled = !s!!.isEmpty()
            }
        })

    }

    class TimerUnit(private val textView: TextView) : Handler() {
        private var defaultTime = 60
        private var time = defaultTime
        private var isShowEndText = true

        private var timeEndListener: OnTimeEndListener? = null

        private var runnable: Runnable = object : Runnable {
            override fun run() {
                time--
                if (time == 0) {
                    endtTime()
                    return
                }
                textView.text = String.format("%ds", time)
                postDelayed(this, 1000)
            }
        }

        fun setTimeEndListener(timeEndListener: OnTimeEndListener) {
            this.timeEndListener = timeEndListener
        }

        fun setShowEndText(showEndText: Boolean) {
            isShowEndText = showEndText
        }


        fun setTime(time: Int) {
            this.defaultTime = time
            this.time = defaultTime
        }

        fun startTime() {
            post(runnable)
            textView.isEnabled = false
        }


        fun pauseTime() {
            removeCallbacks(runnable)
            time = defaultTime
        }

        fun endtTime() {
            if (isShowEndText) {
                textView.text = "获取验证码"
            }
            textView.isEnabled = true
            removeCallbacks(runnable)
            time = defaultTime
            if (timeEndListener != null) {
                timeEndListener!!.timeEnd()
            }
        }

        interface OnTimeEndListener {
            fun timeEnd()
        }

    }

}