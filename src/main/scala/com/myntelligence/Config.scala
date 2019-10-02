package com.myntelligence

import com.typesafe.config.ConfigFactory
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

object Config {

    val rootConfig = ConfigFactory.load().getConfig("application")

    val kafkaParamsDev: Map[String, Object] = Map[String, Object](
    "bootstrap.servers" -> "kf1:9092,kf2:9092,kf3:9092",
    "key.serializer" -> classOf[StringSerializer],
    "value.serializer" -> classOf[StringSerializer],
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "security.protocol" -> "SASL_PLAINTEXT",
    "sasl.mechanism" -> "PLAIN",
    "group.id" -> rootConfig.getString("kafka.group"),
    "auto.offset.reset" -> "earliest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

   val kafkaTopic: String = rootConfig.getString("kafka.topic")
   val outFile: String = rootConfig.getString("outFileName")

}
