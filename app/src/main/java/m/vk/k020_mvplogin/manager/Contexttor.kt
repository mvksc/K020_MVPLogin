package m.vk.k020_mvplogin.manager

import android.content.Context

class Contexttor {

    var context: Context? = null
    fun init(context: Context) {
        this.context = context
    }

    fun clear() {
        this.context = null
    }
    companion object {
        private var instance: Contexttor? = null
        fun getInstance(): Contexttor {
            if (instance == null)
                instance = Contexttor()
            return instance as Contexttor
        }
    }
}
