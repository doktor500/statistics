package uk.co.kenfos.statistics.domain

import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpecLike}

class SummarySpec extends WordSpecLike with Matchers {

  "Summary" should {
    "generate an empty summary when the list of transactions is empty" in {
      Summary.calculate(List.empty) shouldBe Summary.empty
    }

    "generate a summary when there is one transaction" in {
      val transactions = List(Transaction(amount = 1, timestamp = DateTime.now.getMillis))
      Summary.calculate(transactions) shouldBe Summary(count = 1, sum = 1, max = 1, min = 1, avg = 1)
    }

    "generate a summary when there are multiple transactions" in {
      val transactions = List(
        Transaction(amount = 1, timestamp = DateTime.now.getMillis),
        Transaction(amount = 2, timestamp = DateTime.now.getMillis),
        Transaction(amount = 3, timestamp = DateTime.now.getMillis),
        Transaction(amount = 5, timestamp = DateTime.now.getMillis)
      )
      Summary.calculate(transactions) shouldBe Summary(count = 4, sum = 11, max = 5, min = 1, avg = 2.75)
    }
  }
}

