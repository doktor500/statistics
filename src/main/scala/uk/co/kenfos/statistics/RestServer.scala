package uk.co.kenfos.statistics

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object RestServer extends RestService {
  val actorSystemName = "StatisticsApi"
  val host = "localhost"
  val port = 8080

  def main(args: Array[String]) {

    implicit val actorSystem: ActorSystem = ActorSystem(actorSystemName)
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(routes, host, port)
  }
}
