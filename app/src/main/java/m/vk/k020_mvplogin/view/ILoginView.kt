package m.vk.k020_mvplogin.view

import m.vk.k020_mvplogin.model.LoginModel

interface ILoginView {
    fun onClearText()
    fun onLoginSuccess(arrLoginModel: List<LoginModel>)
    fun onLoginFail(status: Int,msg: String)
    fun onSetStatusLoading(visibility: Int)
    fun onSetEnableButtonLogin(enable: Boolean)
    fun onNoConnectInternet()
}