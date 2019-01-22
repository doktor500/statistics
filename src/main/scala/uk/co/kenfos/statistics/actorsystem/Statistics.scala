package uk.co.kenfos.statistics.actorsystem

import akka.actor.{ActorRef, ActorSystem, Props}
import uk.co.kenfos.statistics.actorsystem.StatisticsSystem.Refresh
import scala.concurrent.duration._

object Statistics {

  def init: ActorRef = {
    import statisticsSystem.dispatcher

    val statisticsSystem = ActorSystem("StatisticsSystem")
    val statisticsActor = statisticsSystem.actorOf(Props(classOf[StatisticsSystem]))
    statisticsSystem.scheduler.schedule(0.seconds, 1.second, statisticsActor, Refresh)
    statisticsActor
  }
}
