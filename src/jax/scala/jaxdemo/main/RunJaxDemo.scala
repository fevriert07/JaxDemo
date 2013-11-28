package jax.scala.jaxdemo.main

import jax.scala.jaxdemo.eventbus.EventBusAssembly
import org.joda.time.DateTime
import jax.scala.jaxdemo.events.UploadDataEvent
import jax.scala.jaxdemo.events.QueryDataEvent

object RunJaxDemo {
  val eventBus = new EventBusAssembly().eventBus  
  
  def main(args: Array[String]) {
    eventBus.post(new UploadDataEvent("insertDataFile"))
    
    val ssnChoicePattern = "2 (\\d{3}-\\d{2}-\\d{4})".r
    val dateChoicePattern = "3 (\\d{4}-\\d{2}-\\d{2})".r
    print(menu)
    for (choice <- io.Source.stdin.getLines){
      choice.toUpperCase match {
        case "I" => eventBus.post(new UploadDataEvent("insertDataFile"))
        case "U" => eventBus.post(new UploadDataEvent("updateDataFile"))
        case "0" => eventBus.post(new QueryDataEvent)
        case "1" => queryDateRange()
        case ssnChoicePattern(choice) => eventBus.post(new QueryDataEvent(querySsn=choice))  
        case dateChoicePattern(choice) => eventBus.post(new QueryDataEvent(DateTime.parse(choice)))          
        case _ => println("Invalid Choice - try again")
      }
      print(menu)
    }

  }

  def menu = {
    "**************************************\n" +
      "Menu\n" +
      "I: Load insert data file\n" +
      "U: Load update data file\n" +
      "0: Query All\n" +
      "1: Query all dates in 1990\n" +
      "2 <xxx-xx-xxxx>: Query by SSN\n" +
      "3 <YYYY-MM-DD>: Query by Date\n" +
      "Choose --->"
  }
  
  def queryDateRange(startDate:DateTime=DateTime.parse("1990-01-01"))  {
		val endDate = DateTime.parse("1990-12-31")
		eventBus.post(new QueryDataEvent(startDate))
		if( startDate.compareTo(endDate) <= 0)
			queryDateRange( startDate.plusDays(1))		
  }

}