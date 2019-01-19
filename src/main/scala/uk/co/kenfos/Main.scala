package uk.co.kenfos

import akka.actor.{ActorSystem, Props}
import uk.co.kenfos.statistics.actorsystem.StatisticsSystem
import uk.co.kenfos.statistics.actorsystem.StatisticsSystem.{Refresh}

import scala.concurrent.duration._

object Main extends App {

  import system.dispatcher

  val system = ActorSystem("StatisticsSystem")
  val statisticsActor = system.actorOf(Props(classOf[StatisticsSystem]))

  system.scheduler.schedule(0 seconds, 1 second, statisticsActor, Refresh)

}
