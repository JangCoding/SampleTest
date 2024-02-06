package com.jansparta.sampletest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleTestApplication

fun main(args: Array<String>) {
    runApplication<SampleTestApplication>(*args)
}
