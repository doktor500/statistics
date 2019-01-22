package uk.co.kenfos.statistics

import akka.actor._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpEntity, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import uk.co.kenfos.statistics.actorsystem.Statistics
import uk.co.kenfos.statistics.actorsystem.StatisticsSystem.{Add, GetStatistics}
import uk.co.kenfos.statistics.domain.{Summary, Transaction}

import scala.concurrent.Await
import scala.concurrent.duration._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val summaryFormat: RootJsonFormat[Summary] = jsonFormat5(Summary.apply)
  implicit val transactionFormat: RootJsonFormat[Transaction] = jsonFormat2(Transaction)
}

trait RestService extends Directives with JsonSupport {
  val statisticsActor: ActorRef = Statistics.init

  val routes: Route =
    get {
      pathSingleSlash {
        implicit val timeout: Timeout = Timeout(1.seconds)
        val future = statisticsActor ? GetStatistics
        val summary = Await.result(future, timeout.duration).asInstanceOf[Summary]
        complete(StatusCodes.Accepted, summary)
      }
    } ~
    post {
      pathSingleSlash {
        entity(as[Transaction]) { transaction =>
          statisticsActor ! Add(transaction)
          complete(StatusCodes.Created, HttpEntity.Empty)
        }
      }
    }
}
