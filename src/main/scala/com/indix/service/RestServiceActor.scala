package com.indix.service

import com.indix.util.SearchProcessing
import com.redis.RedisClient
import com.typesafe.config.Config
import spray.routing.HttpServiceActor

import scala.util.{Failure, Success}

/**
  * Created by harsh on 01/06/17.
  */



//Service actor serves user request.
class RestServiceActor(implicit conf:Config,client:RedisClient) extends  HttpServiceActor {
  def receive = runRoute {
    path("search") {
      get {
        parameter("words") { query =>
          val result = RestServiceActor.validateQuery(query)
          if(result.isLeft){
            complete(List(s"Search query was : $query",s"Search failed due to : ${result.left.get}").mkString("\n"))
          }else{
            val searchProcessor = SearchProcessing(client)
            val searchResult = searchProcessor.searchWords(result.right.get)
            val finalResult = searchResult.map(s => s.map(ss => s"${ss._1} | ${ss._2}" ).mkString("\n")).mkString("\n")
            complete(List(s"Search query was : $query","Result for the query is: ",
              "Word  ------ Source File ------ At Line",finalResult).mkString("\n\n"))
          }
        }
      }
    }
  }
}
//Helper object to validate search string.
object RestServiceActor{
  def validateQuery(words:String):Either[String,List[String]] = {
    if(words.replaceAll("""\s+""","").exists(!_.isLetter))
      Left("Search only supported for alphabetical characters from a-z|A-Z")
    else
      Right(words.split(" ").toList)

  }
}

