package com.chenfangwei.humpback

import com.chenfangwei.humpback.space.Space
import com.chenfangwei.humpback.space.repository.SpaceRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.boot.CommandLineRunner

@SpringBootApplication
class HumpbackApplication

fun main(args: Array<String>) {
	runApplication<HumpbackApplication>(*args)
}
