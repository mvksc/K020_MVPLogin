package m.vk.k020_mvplogin.manager

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import m.vk.k020_mvplogin.BuildConfig
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import android.net.NetworkCapabilities

class ConServer {
    fun onPostOkHttp(url: String, formBody: RequestBody): String {
        onShowLogCat("*** POST_URL ***", url)
        try {
            var client = OkHttpClient()
            var request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
            var response = client.newCall(request).execute()
            if(response.isSuccessful){
                return response.body!!.string()
            }
        }catch (e:Exception){
            onShowLogCat("Error","postOkHttp ${e.message}")
        }
        return ""
    }
    fun onGetOkHttp(url: String): String{
        onShowLogCat("*** GET_URL ***", url)
        try {
            var client = OkHttpClient()
            var request = Request.Builder()
                .url(url)
                .build()
            var response = client.newCall(request).execute()
            if(response.isSuccessful){
                return response.body!!.string()
            }
        }catch (e:Exception){
            onShowLogCat("Error","getOkHttp ${e.message}")
        }
        return ""
    }
    fun onReqBodyPostHttp(parameters : HashMap<String,String>) : RequestBody{
        var builders = FormBody.Builder()
        var it = parameters.entries.iterator()
        while (it.hasNext()){
            var pair = it.next() as Map.Entry<*,*>
            onShowLogCat("POST","Key = " + pair.key.toString() + " Value = " + pair.value.toString())
            builders.add(pair.key.toString(),pair.value.toString())
        }
        return builders.build()
    }
    fun onSubStringJsonObject(data: String): String {
        return try {
            data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1)
        } catch (e: Exception) {
            ""
        }
    }
    fun onSubStringJsonArray(data: String): String {
        return try {
            data.substring(data.indexOf("["), data.lastIndexOf("]") + 1)
        } catch (e: Exception) {
            ""
        }
    }
    fun isConnectNetwork(): Boolean {
        //var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        var isConnect = false
        val cm = Contexttor.getInstance().context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        //result = 2
                        isConnect = true
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        //result = 1
                        isConnect = true
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        //result = 2
                        isConnect = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        //result = 1
                        isConnect = true
                    }
                }
            }
        }
        //return result
        return isConnect
    }
    private fun onShowLogCat(tag: String, msg: String){
        if (BuildConfig.DEBUG){
            Log.e("***ConServer***","Tag : $tag ==> Msg : $msg")
        }
    }
}