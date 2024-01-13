package org.lanier.gameserve2.controller

import org.lanier.gameserve2.base.BaseModel
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ServeController {

    @RequestMapping("/push/index")
    fun toPushMange() : BaseModel {
        println(">>>> kkk")
        return BaseModel()
    }
}