package uk.co.kenfos.statistics.actorsystem

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import uk.co.kenfos.statistics.actorsystem.StatisticsSystem._
import com.github.nscala_time.time.Imports._
import uk.co.kenfos.statistics.domain.{Summary, Transaction}

class StatisticsSystem extends Actor with LazyLogging {

  def receive: Receive = active(State.initial)

  def active(state: State): Receive = {
    case Add(transaction) => addTransaction(state, transaction)
    case Refresh          => refresh(state)
    case GetStatistics    => sender() ! state.summary
    case Shutdown         => shutdown(state)
  }

  private def addTransaction(state: State, transaction: Transaction): Unit = {
    context.become(active(State(state.transactions :+ transaction, state.summary)))
  }

  private def refresh(state: State): Unit = {
    val recentTransactions = state.transactions.filter(_.timestamp > (DateTime.now - 1.minutes).getMillis)
    context.become(active(State(recentTransactions, Summary.calculate(recentTransactions))))
  }

  private def shutdown(state: State): Unit = {
    context.system.terminate()
  }
}

object StatisticsSystem {
  case class Add(transaction: Transaction)
  case object GetStatistics
  case object Refresh
  case object Shutdown
}

