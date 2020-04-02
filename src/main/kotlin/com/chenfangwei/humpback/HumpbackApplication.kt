package com.chenfangwei.humpback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HumpbackApplication

fun main(args: Array<String>) {
	runApplication<HumpbackApplication>(*args)
}
