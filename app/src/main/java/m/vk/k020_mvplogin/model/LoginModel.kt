package m.vk.k020_mvplogin.model

import android.os.Parcel
import android.os.Parcelable

data class LoginModel(
    var status: Int,
    val massage: String?,
    val member_id: String?,
    val member_name: String?,
    val member_level: Int
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(status)
        parcel.writeString(massage)
        parcel.writeString(member_id)
        parcel.writeString(member_name)
        parcel.writeInt(member_level)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginModel> {
        override fun createFromParcel(parcel: Parcel): LoginModel {
            return LoginModel(parcel)
        }

        override fun newArray(size: Int): Array<LoginModel?> {
            return arrayOfNulls(size)
        }
    }
}