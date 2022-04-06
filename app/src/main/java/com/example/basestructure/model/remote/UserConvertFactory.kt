package com.example.basestructure.model.remote

import android.util.Log
import com.example.basestructure.model.entities.StatusType
import com.example.basestructure.model.entities.toServiceType
import com.google.gson.*
import java.lang.reflect.Type

class UserConvertFactory : JsonDeserializer<StatusType> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): StatusType {
        return json?.asString?.toServiceType()?:StatusType.Offline
    }
}
