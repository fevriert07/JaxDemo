package jax.scala.jaxdemo.view

import jax.scala.jaxdemo.eventhandler.EventHandler
import jax.scala.jaxdemo.events.PersonRecordEvent
import scala.collection.mutable.HashMap
import org.joda.time.DateTime
import java.util.concurrent.atomic.AtomicInteger
import scala.collection.mutable.ListBuffer
import org.apache.logging.log4j.LogManager
case class PersonRecordView extends EventHandler[PersonRecordEvent] {

  val log = LogManager.getLogger(this)
  val viewStore = new HashMap[String, PersonRecord]
  val ssnIndex = new HashMap[String, PersonRecord]
  val dateIndex = new HashMap[DateTime, ListBuffer[PersonRecord]]
  val idMap = new HashMap[String, String]
  val currentId: AtomicInteger = new AtomicInteger(0)
  val eventCount: AtomicInteger = new AtomicInteger(0)

  override def applyEvent(personRecordEvent: PersonRecordEvent) {
    val ssn = personRecordEvent.ssn
    val id = getId(ssn)
    val dob = personRecordEvent.dob

    val personRecord = new PersonRecord(id, personRecordEvent.firstName, personRecordEvent.middleInitial, personRecordEvent.lastName, ssn, dob);
    val previousPersonRecord = viewStore.get(id);
    removePreviousPersonFromDateIndex(previousPersonRecord)
    viewStore.put(id, personRecord);
    ssnIndex.put(ssn, personRecord);
    addToDateIndex(dob, personRecord)
    logEvent(personRecordEvent)

  }

  private def getId(ssn: String) = {
    idMap.getOrElseUpdate(ssn, String.valueOf(currentId.incrementAndGet()))
  }

  private def removePreviousPersonFromDateIndex(previousPersonRecordOption: Option[PersonRecord]) {
    val previousPersonRecord = previousPersonRecordOption.getOrElse[PersonRecord](PersonRecord())
    val personRecordByDate = dateIndex.getOrElse(previousPersonRecord.dob, new ListBuffer[PersonRecord])
    personRecordByDate -= previousPersonRecord
  }

  private def addToDateIndex(dob: DateTime, personRecord: PersonRecord) {
    val personRecordByDate = dateIndex.getOrElseUpdate(dob, new ListBuffer[PersonRecord])
    personRecordByDate += personRecord
  }

  private def logEvent(personRecordEvent: PersonRecordEvent) {
    val currentEventCount = eventCount.incrementAndGet()
    if (currentEventCount % 250000 == 0) {
      log.info(s"Processed event $currentEventCount: $personRecordEvent")
    }
  }

}