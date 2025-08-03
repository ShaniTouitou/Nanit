package com.example.nanit.network

import android.util.Log
import com.example.nanit.model.BirthdayData
import com.squareup.moshi.Moshi
import okhttp3.*
import okio.ByteString

/**
 * This class is to connect to the server by WebSocket client that connects to a given server IP and port.
 */
class WebSocketClient {

    // region Members

    private var webSocket: WebSocket? = null

    // endregion

    // region Public Methods

    fun connect(ip: String, port: String,
                onMessage: (BirthdayData) -> Unit, onError: (Throwable) -> Unit) {
        val url = "ws://$ip:$port/nanit"
        Log.d("WebSocketClient", "Connecting to $url")

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocketClient", "WebSocket Opened")
                webSocket.send("HappyBirthday")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WebSocketClient", "Received message: $text")
                try {
                    val moshi = Moshi.Builder()
                        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
                        .build()
                    val adapter = moshi.adapter(BirthdayData::class.java)
                    val data = adapter.fromJson(text)

                    if (data != null) {
                        onMessage(data)
                        webSocket.close(1000, null)
                    }
                    else {
                        onError(IllegalStateException("Invalid JSON structure"))
                    }
                } catch (e: Exception) {
                    onError(e)
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d("WebSocketClient", "Received bytes: ${bytes.hex()}")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WebSocketClient", "Closing: $code / $reason")
                webSocket.close(1000, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocketClient", "Error: ${t.message}", t)
                onError(t)
            }
        })

        Log.d("WebSocketClient", webSocket.toString())
    }

    // endregion

}
