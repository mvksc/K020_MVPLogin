package m.vk.k020_mvplogin.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import m.vk.k020_mvplogin.R
import m.vk.k020_mvplogin.databinding.ActivityLoginBinding
import m.vk.k020_mvplogin.model.LoginModel
import m.vk.k020_mvplogin.presenter.ILoginPresenter
import m.vk.k020_mvplogin.presenter.LoginPresenterCompI

class LoginActivity : AppCompatActivity(), ILoginView , View.OnClickListener{

    companion object{
        lateinit var binding: ActivityLoginBinding
        lateinit var loginPresenter: ILoginPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        loginPresenter = LoginPresenterCompI(this)
        loginPresenter.setStatusLoading(View.INVISIBLE)

        binding.btnLogin.setOnClickListener(this)
        binding.btnClear.setOnClickListener(this)
    }

    override fun onClearText() {
        loginPresenter.setEnableButtonLogin(true)
        binding.edUsername.setText("")
        binding.edPassword.setText("")
        binding.tvStatusLogin.text = ""
    }

    override fun onLoginSuccess(arrLoginModel: List<LoginModel>) {
        loginPresenter.setEnableButtonLogin(true)
        loginPresenter.setStatusLoading(View.INVISIBLE)
        binding.tvStatusLogin.text = "สวัสดี คุณ${arrLoginModel[0].member_name}"//I want to show name from array index 0 only
        binding.tvStatusLogin.setTextColor(Color.parseColor("#00574B"))
    }

    override fun onLoginFail(status: Int, msg: String) {
        loginPresenter.setEnableButtonLogin(true)
        loginPresenter.setStatusLoading(View.INVISIBLE)
        binding.tvStatusLogin.text = "เสียใจด้วย $msg"
        binding.tvStatusLogin.setTextColor(Color.parseColor("#FF0000"))
    }

    override fun onSetStatusLoading(visibility: Int) {
        binding.pbLoading.visibility = visibility
    }

    override fun onSetEnableButtonLogin(enable: Boolean) {
        binding.btnLogin.isEnabled = enable
        binding.btnClear.isEnabled = enable
    }

    override fun onNoConnectInternet() {
        binding.tvStatusLogin.text = "กรุณาเชื่อมต่ออินเดอร์เน็ต"
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnLogin ->{
                var user = binding.edUsername.text.toString()
                var pass = binding.edPassword.text.toString()
                if (user.isNotEmpty() && pass.isNotEmpty()){
                    loginPresenter.setEnableButtonLogin(false)
                    loginPresenter.setStatusLoading(View.VISIBLE)
                    loginPresenter.checkLogin(false,user,pass)
                }
            }
            R.id.btnClear ->{
                loginPresenter.setEnableButtonLogin(false)
                loginPresenter.clear()
            }
        }
    }
}
