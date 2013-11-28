package jax.scala.jaxdemo.eventhandler

import jax.scala.jaxdemo.view.PersonRecordView
import org.apache.logging.log4j.LogManager
import jax.scala.jaxdemo.view.PersonRecord
import scala.collection.mutable.ListBuffer
import jax.scala.jaxdemo.events.QueryDataEvent

case class QueryEventHandler(personRecordView: PersonRecordView) extends EventHandler[QueryDataEvent] {

  val log = LogManager.getLogger(this)
  val MAX_PRINT_RECORDS = 5;

  override def applyEvent(event: QueryDataEvent) {
    val results: List[PersonRecord] =
      if (event.querySsn != null && personRecordView.ssnIndex.contains(event.querySsn))
        personRecordView.ssnIndex.get(event.querySsn).get :: Nil
      else if (event.queryDate != null)
        personRecordView.dateIndex.getOrElse(event.queryDate, new ListBuffer[PersonRecord]).toList
      else
        personRecordView.viewStore.values.toList

    val queryTime = System.currentTimeMillis() - event.queryStartTime
    log.info(s"Completed Query ssn=${event.querySsn} dob=${event.queryDate} in $queryTime ms (${results.size} records)");
    logResults(results)

  }

  private def logResults(results: List[PersonRecord]) {

    for (i <- 0 until MAX_PRINT_RECORDS; if i < results.size) {
      log.info(results(i));
    }
    if (MAX_PRINT_RECORDS < results.size)
      log.info((results.size - MAX_PRINT_RECORDS) + " More Records...")
  }
}