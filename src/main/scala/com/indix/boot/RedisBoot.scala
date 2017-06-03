package com.indix.boot


import com.redis.RedisClient
import com.typesafe.config.Config

import scala.concurrent.ExecutionContext

/**
  * Created by harsh on 18/03/17.
  */

//The framework to boot redis engine.
trait RedisBoot {

  /**
    * Method to return redis client.
    *
    * @param config configuration to aid various implicit and explicit values required in creation of client
    *               //@param actorRefFactory
    * @param executionContext
    * @return RedisClient instance
    */
  def getRedisClient(implicit config: Config,executionContext: ExecutionContext): RedisClient = {
    try {
      //implicit val timeout = Timeout(config.getInt("indix-app.redis.timeout") seconds)
      val host:String = config.getString("redis.host")
      val port:Int = config.getInt("redis.port")
      //new RedisClientPool("localhost", 6379)
      new RedisClient(host,port)
    } catch {
      case ex: Exception =>
        println(s"Exception occurred while instantiating Redis.")
        throw new ExceptionInInitializerError(s"Exception occured while instantiating Redis : ${ex.getMessage}")
    }
  }
}
