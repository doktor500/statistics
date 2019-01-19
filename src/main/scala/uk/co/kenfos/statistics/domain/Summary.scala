package uk.co.kenfos.statistics.domain

case class Summary(sum: Double, avg: Double, max: Double, min: Double, count: Int)

object Summary {

  def empty = Summary(sum = 0, avg = 0, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE, count = 0)

  def calculate(transactions: List[Transaction]): Summary = {
    transactions match {
      case Nil => Summary.empty
      case _   =>
        transactions.foldLeft(Summary.empty)((summary: Summary, transaction: Transaction) => {
          val count = summary.count + 1
          val sum = summary.sum + transaction.amount
          val avg = sum / count
          val max = Math.max(summary.max, transaction.amount)
          val min = Math.min(summary.min, transaction.amount)
          Summary(sum, avg, max, min, count)
        })
    }
  }
}