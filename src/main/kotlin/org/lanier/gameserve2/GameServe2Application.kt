package org.lanier.gameserve2

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@MapperScan(basePackages = ["org.lanier.gameserve2.mapper"])
@EnableTransactionManagement
class GameServe2Application

fun main(args: Array<String>) {
    runApplication<GameServe2Application>(*args)
}
