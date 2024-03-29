package com.nullpointer.dogedex.core.states

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.InetSocketAddress
import java.net.Socket

object InternetCheck {
    suspend fun isNetworkAvailable() = coroutineScope {
        return@coroutineScope withContext(Dispatchers.IO) {
            try {
                val sock = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)
                sock.connect(socketAddress, 1500)
                sock.close()
                true
            } catch (e: Exception) {
                Timber.e("socke error $e")
                false
            }
        }
    }

}