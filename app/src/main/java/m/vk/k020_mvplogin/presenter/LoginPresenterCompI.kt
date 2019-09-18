package m.vk.k020_mvplogin.presenter

import android.os.AsyncTask
import android.util.Log
import m.vk.k020_mvplogin.BuildConfig
import m.vk.k020_mvplogin.manager.ConServer
import m.vk.k020_mvplogin.model.LoginModel
import m.vk.k020_mvplogin.view.ILoginView
import okhttp3.RequestBody
import org.json.JSONObject

class LoginPresenterCompI : ILoginPresenter{

    private var iLoginView: ILoginView
    private var conServer: ConServer

    constructor(iLoginView: ILoginView){
        this.iLoginView = iLoginView
        this.conServer = ConServer()
    }

    override fun clear() {
        iLoginView.onClearText()
    }

    override fun checkLogin(isPost: Boolean, user: String, pass: String) {
        if (conServer.isConnectNetwork()){
            //Select Send POST or GET Method
            if (isPost){
                val parameters = HashMap<String, String>()
                /*Key = Value*/
                parameters["user"] = user
                parameters["pass"] = pass
                postOkHttp("http://192.168.1.51/kotlin/check_login.php",conServer.onReqBodyPostHttp(parameters))//change url for you
            }else{
                getOkHttp("http://192.168.1.51/kotlin/check_login.php?user=$user&pass=$pass")//change url for you
            }
        }else{
            iLoginView.onNoConnectInternet()
            iLoginView.onSetEnableButtonLogin(true)
        }
    }

    override fun setStatusLoading(visibility: Int) {
        iLoginView.onSetStatusLoading(visibility)
    }

    override fun setEnableButtonLogin(enable: Boolean) {
        iLoginView.onSetEnableButtonLogin(enable)
    }

    private fun getOkHttp(url: String){
        object : AsyncTask<String, Void, String>(){
            override fun doInBackground(vararg p0: String?): String {
                try {
                    return conServer.onGetOkHttp(url)
                }catch (e:Exception){
                    onShowLogCat("Error","getOkHttp ${e.message}")
                }
                return ""
            }

            override fun onPostExecute(result: String?) {
                onShowLogCat("result get",conServer.onSubStringJsonObject(result.toString()))
                addLoginToList(conServer.onSubStringJsonObject(result.toString()))
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
    private fun postOkHttp(url: String,formBody: RequestBody){
        object : AsyncTask<String,Void,String>(){
            override fun doInBackground(vararg p0: String?): String {
                try {
                    return conServer.onPostOkHttp(url,formBody)
                }catch (e:Exception){
                    onShowLogCat("Error","postOkHttp ${e.message}")
                }
                return ""
            }

            override fun onPostExecute(result: String?) {
                onShowLogCat("result post",conServer.onSubStringJsonObject(result.toString()))
                addLoginToList(conServer.onSubStringJsonObject(result.toString()))
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
    private fun addLoginToList(dataLogin: String){
        var listLogin: ArrayList<LoginModel> = ArrayList()
        var jObj = JSONObject(dataLogin)

        /*listLogin.add(LoginModel(
            jObj.getInt("status"),
            jObj.getString("massage"),
            if (jObj.isNull("member_id")) "" else jObj.getString("member_id"),
            if (jObj.isNull("member_name")) "" else jObj.getString("member_name"),
            if (jObj.isNull("member_level")) 0 else jObj.getInt("member_level")))*/


        if (jObj.getInt("status") == 1){//สำเร็จ
            listLogin.add(LoginModel(
                jObj.getInt("status"),
                jObj.getString("massage"),
                jObj.getString("member_id"),
                jObj.getString("member_name"),
                jObj.getInt("member_level")))
            iLoginView.onLoginSuccess(listLogin)
        }else if (jObj.getInt("status") == 2){//ผิดพลาด
            iLoginView.onLoginFail(jObj.getInt("status"),jObj.getString("massage"))
        }
    }

    fun onShowLogCat(tag: String,msg: String){
        if (BuildConfig.DEBUG){
            Log.e("***LoginPresenter***","Tag : $tag ==> Msg : $msg")
        }
    }
}