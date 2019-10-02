package com.myntelligence

import java.io._
import java.time.{Duration, Instant}
import java.util
import java.util.Properties

import Config._
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._

object App {

  def main(args: Array[String]): Unit = {

    consumeFromKafka(kafkaTopic, FileSystemHelper.fullPath)

//    writeFile(FileSystemHelper.fullPath, List("aaaa", "bbbb", "cccc"))
  }

  def consumeFromKafka(topic: String, fullPath: String): Unit = {
    val consumer = new KafkaConsumer[String, String](buildFromMap(kafkaParamsDev))
    consumer.subscribe(util.Arrays.asList(topic))

    val startTime = Instant.now()
    implicit val writer: BufferedWriter = new BufferedWriter(new FileWriter(new File(fullPath)))

    (1 to 3).foreach(x => {
        while(Instant.now().minusSeconds(startTime.getEpochSecond).getEpochSecond < x * 2) { // change to 60
          val records = consumer.poll(Duration.ofMillis(500)).asScala
          records.map(x => x.value()).writeToSource
          println(records.size)
        }
        consumer.commitSync()
      }
    )

    writer.close()
    consumer.close()
  }

//  def writeFile(fullPath: String, lines: Iterable[String]): Unit = {
//    val file = new File(fullPath)
//    file.createNewFile()
//
//    val bw = new BufferedWriter(new FileWriter(file))
//
//    for (line <- lines) {
//      bw.write(line + "2")
//      bw.write("\n")
//    }
//    bw.close()
//  }

  def buildFromMap(properties: Map[String, Object]): Properties =
    (new Properties /: properties) {
      case (a, (k, v)) =>
        a.put(k,v)
        a
    }

  implicit class Wrapper(values: Iterable[String]) {

    def writeToSource(implicit writer: Writer): Unit = {
      for (line <- values) {
        writer.write(line)
        writer.write("\n")
      }
      writer.flush()
    }
  }
}




