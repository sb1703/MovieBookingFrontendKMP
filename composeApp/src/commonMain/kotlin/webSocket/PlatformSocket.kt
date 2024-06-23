package webSocket

internal expect class PlatformSocket(url: String, headers: Map<String, String>) {
    fun init(events: PlatformSocketEvents)
    fun openSocket()
    fun closeSocket(code: Int, reason: String)
    fun sendMessage(msg: String)
}

interface PlatformSocketEvents {
    fun onOpen()
    fun onFailure(t: Throwable)
    fun onMessage(msg: String)
    fun onClosed(code: Int, reason: String)
}