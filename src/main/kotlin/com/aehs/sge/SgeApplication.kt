package com.aehs.sge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SgeApplication

fun main(args: Array<String>) {
	runApplication<SgeApplication>(*args)
}
