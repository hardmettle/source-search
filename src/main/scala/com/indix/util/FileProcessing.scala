package com.indix.util

import java.io.File

import akka.util.Timeout
import com.redis.RedisClient
import com.typesafe.config.Config

/**
  * Created by harsh on 29/05/17.
  */
object FileProcessing {

  def apply(implicit config: Config, client: RedisClient): Unit = {
    val paths = config.getStringList("source-files")
    println(s"Indexing \n ${paths.toArray.toList.mkString("\n")}")
    paths.toArray.toList.foreach(p => {
      val path = p.toString
      val file = new File(path)
      val fileName = file.getName

      val lines = scala.io.Source.fromFile(file).getLines().toList
      val linesWithIndex = lines.zipWithIndex
      //Point filename and line number to line in the file.
      val numberToLine = linesWithIndex.map(line => (s"$fileName|${line._2}", line._1))
      val x = numberToLine.map(nl => {
        client.set(nl._1, nl._2)
      })

      val wordToFileName = linesWithIndex.flatMap(line => {
        val words = line._1.split("[^\\w']+")
        words.map(word => {
          (s"$word", s"$fileName|${line._2}")
        })
      })
      //Point each word to the corresponding source file and line number at which it occurs.
      wordToFileName.map(wf => client.sadd(wf._1.toLowerCase, wf._2))
    })
  }
}
