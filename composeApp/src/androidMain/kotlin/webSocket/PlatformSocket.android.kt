package webSocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket

internal actual class PlatformSocket actual constructor(
    url: String,
    private val headers: Map<String, String>
) {
    private val socketEndpoint = url
    private var webSocket: WebSocket? = null
    private var events: PlatformSocketEvents? = null

    actual fun init(events: PlatformSocketEvents) {
        this.events = events
    }

    actual fun openSocket() {
        val socketRequest = Request.Builder().url(socketEndpoint).apply {
            headers.forEach { addHeader(it.key, it.value) }
        }.build()
        val webClient = OkHttpClient().newBuilder().build()
        webSocket = webClient.newWebSocket(socketRequest, object : okhttp3.WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                events?.onOpen()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                events?.onFailure(t)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                events?.onMessage(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                events?.onClosed(code, reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                events?.onClosed(code, reason)
            }

        })
    }

    actual fun closeSocket(code: Int, reason: String) {
        webSocket?.close(code, reason)
        webSocket = null
    }

    actual fun sendMessage(msg: String) {
        webSocket?.send(msg)
    }
}