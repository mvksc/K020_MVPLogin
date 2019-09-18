package m.vk.k020_mvplogin.presenter

interface ILoginPresenter {
    fun clear()
    fun checkLogin(isPost: Boolean,user: String,pass: String)
    fun setStatusLoading(visibility: Int)
    fun setEnableButtonLogin(enable: Boolean)
}