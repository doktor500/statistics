package uk.co.kenfos.statistics

import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.github.nscala_time.time.Imports.DateTime
import org.scalatest.concurrent.Eventually
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration._

class RestServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with Eventually with RestService {

  "The API" should {
    "return a created response when a transaction is sent" in {
      val timestamp = DateTime.now.getMillis
      val payload = ByteString(
        s"""
           |{
           |    "amount": 1,
           |    "timestamp": $timestamp
           |}
        """.stripMargin)

      val entity = HttpEntity(MediaTypes.`application/json`, payload)
      val request = HttpRequest(HttpMethods.POST, uri = "/", entity = entity)
      request ~> routes ~> check { status.isSuccess() shouldEqual true }
    }

    "return statistics when a request is made" in {
      val request = HttpRequest(HttpMethods.GET, uri = "/")
      val expectedResponse =
        s"""
           |{
           |    "avg": 1.0,
           |    "count": 1,
           |    "max": 1.0,
           |    "min": 1.0,
           |    "sum": 1.0
           |}
        """.stripMargin.split("\\s+").mkString

      eventually(timeout(1 second), interval(100 milliseconds)) {
        request ~> routes ~> check {
          response.entity shouldEqual HttpEntity(MediaTypes.`application/json`, expectedResponse)
          status.isSuccess() shouldEqual true
        }
      }
    }
  }
}
