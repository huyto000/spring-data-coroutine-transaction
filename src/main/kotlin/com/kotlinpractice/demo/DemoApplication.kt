package com.kotlinpractice.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.AdviceMode
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@SpringBootApplication
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
class DemoApplication

    @Bean
//    fun dataSource(): DataSource {
//        val dataSourceBuilder = DataSourceBuilder.create()
//        dataSourceBuilder.driverClassName("org.h2.Driver")
//        dataSourceBuilder.url("jdbc:h2:file:./data/employee;LOCK_MODE=3;DB_CLOSE_ON_EXIT=FALSE")
//        dataSourceBuilder.username("sa")
//        dataSourceBuilder.password("")
//        return dataSourceBuilder.build()
//    }
//
//    @Bean
//    fun txManager(): PlatformTransactionManager {
//    return DataSourceTransactionManager(dataSource())
//    }

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

//suspend fun sleep(): String {
//    println("Start sleep")
//    delay(1000)
//    println("Done sleep")
//    return "Sleep";
//}
//
//suspend fun chargeIphone(): String {
//    println("Start charge")
//    delay(2000)
//    println("Done charging")
//    return "Charge"
//}
//
//suspend fun main(args: Array<String>) {
//    var listStep = listOf<String>();
//	coroutineScope {
//        coroutineScope {
//            val sleepJob = async { sleep() }
//            val chargeJob = async { chargeIphone() }
//			val x = sleepJob.await()
//			val y = chargeJob.await()
//			listStep = listOf(x, y)
//        }
//        launch { chargeIphone() }
//		println(listStep)
//    }




