package indix.util
import com.indix.util.{FileProcessing, SearchProcessing}
import com.redis.RedisClient
import com.typesafe.config.ConfigFactory
import org.scalatest.FunSuite


/**
  * Created by harsh on 02/06/17.
  */
class SearchProcessingSpec extends FunSuite {
  val config = ConfigFactory.load().getConfig("indix-app")
  val host:String = config.getString("redis.host")
  val port:Int = config.getInt("redis.port")
  val client = new RedisClient(host,port)
  FileProcessing(config , client )
  test("Validate search is working"){
    val (word,fileNameNLine) = SearchProcessing(client).searchWords(List("Hello")).head.head
    //println(fileNameNLine)
    //Validate filename and the line found in search
    assert(fileNameNLine.split("\\|").toList.head.trim == "TestScript.scala")
    assert(fileNameNLine.split("\\|").toList.tail.head.trim == """println("Hello World!")""")
    //assert(1 == 1)
  }
}