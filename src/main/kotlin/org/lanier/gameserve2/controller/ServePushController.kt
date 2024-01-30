package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.lanier.gameserve2.websocket.ClientManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ServePushController {
    
    internal data class Message(
        val from: String,
        val text: String
    ) {
        companion object {

            const val SYSTEM = "system"
            const val SIMPLE = "simple"
        }
    }

    @PostMapping("/push/send/type")
    fun pushSystemMsgToClients(@RequestParam("type") type: String) : BaseModel<Any> {
        handleMessage(Message(from = Message.SYSTEM, text = type))
        return BaseModel.success(data = null)
    }

    @PostMapping("/push/send")
    fun pushSimpleMsgToClients(@RequestParam("msg") msg: String) : BaseModel<Any> {
        handleMessage(Message(from = Message.SIMPLE, text = msg))
        return BaseModel.success(data = null)
    }
    
    private fun handleMessage(message: Message) {
        println(message)
//        ClientManager.sendMessage(message.toString())
    }
}