package com.indix.util

import akka.util.Timeout
import com.redis.RedisClient
import com.redis.serialization.Parse.Implicits


/**
  * Created by harsh on 02/06/17.
  */
case class SearchProcessing(client: RedisClient) {
  def searchWords(words: List[String]): List[Set[(String, String)]] = {
    words.map(word => {
      val optionMembers = client.smembers(word.toLowerCase)(com.redis.serialization.Format.default, Implicits.parseString)
      optionMembers match {
        case Some(setM) =>
          setM.flatten.map(v => {
            val fileNLineNum = v.split("\\|").toList
            client.get[String](v)(com.redis.serialization.Format.default, Implicits.parseString)
              .map(l => (word, s"${fileNLineNum.head} | $l")).get
          })
        case None =>  Set((word,"No match found"))
      }
    })
  }
}