package uk.co.kenfos.statistics.actorsystem

import uk.co.kenfos.statistics.domain.{Summary, Transaction}

case class State(transactions: List[Transaction], summary: Summary)

object State {
  def initial = State(transactions = List.empty, summary = Summary.empty)
}
