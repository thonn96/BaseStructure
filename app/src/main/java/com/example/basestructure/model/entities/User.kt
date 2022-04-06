package com.example.basestructure.model.entities

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users",indices = [(Index(value = ["name"], unique = true))])
@TypeConverters(ServiceTypeConverter::class)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    var name: String,
    @SerializedName("status")
    var status: StatusType = StatusType.Offline
) : Parcelable {
    override fun toString(): String {
        return name
    }
}

sealed class StatusType : Parcelable {
    @Parcelize
    object Online : StatusType() {
        override fun toString(): String {
            return "online"
        }
    }

    @Parcelize
    object Offline : StatusType() {
        override fun toString(): String {
            return "offline"
        }
    }

    @Parcelize
    object Busy : StatusType() {
        override fun toString(): String {
            return "Busy"
        }
    }


}

class ServiceTypeConverter {
    @TypeConverter
    fun fromString(value: String): StatusType {
        return value.toServiceType()
    }

    @TypeConverter
    fun fromServiceType(value: StatusType): String {
        return value.toString()
    }
}

fun String.toServiceType(): StatusType {
    return when (this) {
        "offline" -> StatusType.Offline
        "online"->  StatusType.Online
        "busy" -> StatusType.Busy
        else -> {
            StatusType.Offline
        }
    }
}