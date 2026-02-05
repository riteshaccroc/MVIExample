package com.example.mviexample.data.dataStore.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.mviexample.proto.AppParams
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object AppParamsSerializer : Serializer<AppParams> {
    override val defaultValue: AppParams = AppParams.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppParams {
        try {
            return AppParams.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", cause = exception)
        }
    }

    override suspend fun writeTo(t: AppParams, output: OutputStream) = t.writeTo(output)
}

/*object AppParamsSerializer : Serializer<AppParams> {
    override val defaultValue: AppParams = AppParams.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppParams = withContext(IO) {
        try {
            return@withContext AppParams.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", cause = exception)
        }
    }

    override suspend fun writeTo(t: AppParams, output: OutputStream) =
        withContext(IO) { t.writeTo(output) }
}*/

//Creating dataStore using extension function
//The dataStore delegate ensures that we have a single instance of DataStore with that name in our application.
//In a production application, the DataStore instance should be injected in the classes that need it, by using DataStoreFactory
/*val Context.appParamDataStore: DataStore<AppParams> by dataStore(
    fileName = "app_params.pb",
    serializer = AppParamsSerializer
)*/
