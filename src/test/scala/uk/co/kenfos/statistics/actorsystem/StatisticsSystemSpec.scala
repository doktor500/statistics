package uk.co.kenfos.statistics.actorsystem

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.{TestActorRef, TestKit}
import akka.util.Timeout
import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import uk.co.kenfos.statistics.actorsystem.StatisticsSystem.{Add, GetStatistics, Refresh}
import uk.co.kenfos.statistics.domain.{Summary, Transaction}

class StatisticsSystemSpec extends TestKit(ActorSystem("StatisticsSystem"))
  with WordSpecLike
  with Matchers
  with ScalaFutures
  with BeforeAndAfterAll {

  implicit val timeout: Timeout = Timeout(1, TimeUnit.SECONDS)

  override def afterAll(): Unit = system.terminate

  "StatisticsSystem" should {
    "statistics are calculated when transactions are added" in {
      val statisticsSystem = TestActorRef.apply(new StatisticsSystem())

      val oldTransaction = Transaction(amount = 1, timestamp = (DateTime.now - 1.minutes).getMillis)
      val newTransaction = Transaction(amount = 2, timestamp = DateTime.now.getMillis)

      statisticsSystem ! new Add(oldTransaction)
      statisticsSystem ! new Add(newTransaction)
      statisticsSystem ! Refresh

      val actorState = (statisticsSystem ? GetStatistics).futureValue
      actorState.leftSideValue shouldBe Summary(sum = 2, avg = 2, max = 2, min = 2, count = 1)
    }
  }
}
