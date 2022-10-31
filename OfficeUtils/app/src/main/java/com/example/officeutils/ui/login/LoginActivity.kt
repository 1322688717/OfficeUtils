package com.example.officeutils.ui.login

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.base.BaseActivity
import com.example.officeutils.databinding.ActivityLoginBinding
import com.example.officeutils.utils.RouterUtil
import com.example.officeutils.utils.ToastUtil
import com.example.officeutils.viewmodel.LoginViewModel
import java.sql.Time
import java.util.*

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel : LoginViewModel
    var account:String = ""
    var passWord:String = ""
    var code : String = ""
    var uuid : String = ""
    var isLoginType : Boolean = false   //true为密码登录  false为短信登录
    var isCountDown : Boolean = false
    private var timer: TimerUnit? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initView()
        initOnclick()
//        getCode()  //获取验证码
//        initEdit()

    }

    @SuppressLint("SetTextI18n")
    private fun initOnclick() {
        binding.tvCodePassword.setOnClickListener {
            if (isLoginType == true){
                binding.tvCodePassword.text = "用短信验证码登录"
                binding.codeLogin.visibility = View.GONE
                binding.passwordLogin.visibility = View.VISIBLE
                isLoginType =false
            }else{
                binding.tvCodePassword.text = "用密码登录"
                binding.codeLogin.visibility = View.VISIBLE
                binding.passwordLogin.visibility = View.GONE
                isLoginType =true
            }
        }

        binding.tvRegister.setOnClickListener {
            RouterUtil().goRegisterActivity(this)
        }

        binding.btnLogin.setOnClickListener {
            account = binding.edtAccount.text.toString()
            passWord = binding.edtPassword.text.toString()
            code = binding.edtVerCode.toString()
            Log.e("TAG","account==$account")
            Log.e("TAG","passWord==$passWord")
            viewModel.login(account,passWord,code,this)

        }

        binding.getCode.setOnClickListener {
            ToastUtil().ToastLong(this,"发送成功")
            if (timer == null) {
                timer = TimerUnit( binding.getCode)
            }
            timer?.startTime()
        }

    }

    private fun initView() {
        binding.btnLogin.isEnabled = false
        binding.edtAccount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.btnLogin.isEnabled = !s!!.isEmpty()

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