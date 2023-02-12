package com.mbt925.realestate.service.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.mbt925.realestate.service.local.models.ListingsResultDao
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

object ListingsResultDaoSerializer : Serializer<ListingsResultDao> {

    override val defaultValue = ListingsResultDao()

    override suspend fun readFrom(input: InputStream): ListingsResultDao {
        try {
            return Json.decodeFromString(
                ListingsResultDao.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read cache data store", serialization)
        }
    }

    override suspend fun writeTo(t: ListingsResultDao, output: OutputStream) {
        output.write(
            Json.encodeToString(ListingsResultDao.serializer(), t)
                .encodeToByteArray()
        )
    }
}
