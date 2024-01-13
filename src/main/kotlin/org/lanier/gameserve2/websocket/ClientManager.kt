package org.lanier.gameserve2.websocket

import java.util.concurrent.ConcurrentHashMap

object ClientManager {

    private val map = ConcurrentHashMap<String, MxWebSocket>()

    fun put(key: String, value: MxWebSocket) : Boolean {
        if (map.contains(key)) {
            return true
        }
        map[key] = value
        return true
    }

    fun get(key: String?) : MxWebSocket? {
        if (key == null) {
            return null
        }
        if (map.contains(key)) {
            return map[key]
        }
        return null
    }

    fun delete(key: String? = null) : MxWebSocket? {
        if (key.isNullOrEmpty()) {
            return null
        }
        return map.remove(key)
    }

    fun sendMessage(text: String, receiverKey: String? = null) {
        if (text.isEmpty()) {
            return
        }
        if (receiverKey == null) {
            map.forEach { (_, wb) ->
                wb.sendMessage(text)
            }
            return
        }
        val wb = get(receiverKey)
        wb?.sendMessage(text)
    }
}