package jax.scala.jaxdemo.eventhandler

import com.google.common.eventbus.EventBus
import jax.scala.jaxdemo.events.UploadDataEvent
import scala.collection.mutable.HashMap
import org.joda.time.DateTime
import org.apache.logging.log4j.LogManager
import jax.scala.jaxdemo.events.PersonRecordEvent

case class UploadDataEventHandler(eventBus: EventBus) extends EventHandler[UploadDataEvent] {

  val memoryReducingStringMap = new HashMap[String, String]
  val memoryReducingDateMap = new HashMap[String, DateTime]
  val log = LogManager.getLogger(this)

  override def applyEvent(event: UploadDataEvent) {
    publishPersonRecordEvents(event.dataFileName)
  }

  private def publishPersonRecordEvents(dataFilename: String) {
    val file = io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(dataFilename))
    var count = 0
    for (line <- file.getLines; if line.length() > 0) {
      count = count + 1
      val f = line.split(",")
      val firstName = getStringValue(f(0))
      val middleInitial = getStringValue(f(1))
      val lastName = getStringValue(f(2))
      val ssn = getStringValue(f(3))
      val dob = getDateValue(f(4))

      val personRecordEvent = new PersonRecordEvent(firstName, middleInitial, lastName, ssn, dob)
      if (count % 250000 == 0)
        log.info(s"Publishing event ($count): $personRecordEvent")
      eventBus.post(personRecordEvent)

    }
  }

  private def getStringValue(value: String) = memoryReducingStringMap.getOrElseUpdate(value, value)
  private def getDateValue(value: String) = memoryReducingDateMap.getOrElseUpdate(value, DateTime.parse(value))

}