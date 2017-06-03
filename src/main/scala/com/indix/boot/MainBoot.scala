package com.indix.boot

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.typesafe.config.ConfigFactory
import akka.pattern.ask

import scala.concurrent.duration._
import akka.util.Timeout
import com.indix.service.RestServiceActor
import com.indix.util.FileProcessing
import spray.can.Http

import scala.util.{Failure, Success}

/**
  * Created by harsh on 18/03/17.
  */

//Main entry point of the application
object MainBoot extends App with RedisBoot {
  implicit val config = ConfigFactory.load().getConfig("indix-app")
  implicit val timeout = Timeout(config.getInt("redis.timeout") seconds)
  implicit val actorSystem = ActorSystem("indix-system")
  implicit val executionContext = actorSystem.dispatcher
  implicit val client = getRedisClient(config,executionContext)
  client.flushall
  FileProcessing(config,client)
  val PORT = config.getInt("app-port")
  val DOMAIN = config.getString("app-host")
  val handler = actorSystem.actorOf(Props(new RestServiceActor()) , name = "handler")
  println(s"Starting server")
  //Bind spray http server to host and port with handler.
  IO(Http) ! Http.Bind(handler, interface = DOMAIN, port = PORT)
}
