package org.lanier.gameserve2.websocket

import jakarta.websocket.*
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint
import org.springframework.stereotype.Component

@Component
@ServerEndpoint("/wbs/{uid}")
class MxWebSocket {

    private var uid = ""
    private var session: Session? = null

    @OnOpen
    fun onOpen(session: Session, @PathParam("uid") uid: String) {
        println(">>>> open $uid")
        this.uid = uid
        this.session = session
        ClientManager.put(uid, this)
    }

    @OnClose
    fun onClose() {
        println(">>>> close")
        ClientManager.delete(uid)
        this.uid = ""
        this.session = null
    }

    @OnMessage
    fun onMessage(msg: String, session: Session) {
        println(">>>> $msg")
        ClientManager.sendMessage("okok")
    }

    @OnError
    fun onErr(session: Session, err: Throwable) {
        println(">>>> ${err.message}")
    }

    fun sendMessage(text: String) {
        session?.basicRemote?.sendText(text)
    }
}