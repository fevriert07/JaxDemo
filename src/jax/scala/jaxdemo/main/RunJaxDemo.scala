package jax.scala.jaxdemo.main

import jax.java.jaxdemo.eventbus.EventBusAssembly
import jax.java.jaxdemo.events.UploadDataEvent

object RunJaxDemo {

  def main(args: Array[String]) {
    val eventBus = new EventBusAssembly().getEventBus
    eventBus.post(new UploadDataEvent("insertDataFile"))

    print(menu)
    for (choice <- io.Source.stdin.getLines){
      choice.toUpperCase match {
        case "I" => eventBus.post(new UploadDataEvent("insertDataFile"))
        case "U" => eventBus.post(new UploadDataEvent("updateDataFile"))
        
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
      "2 <SSN>: Query by SSN\n" +
      "3 <YYYY-MM-DD>: Query by Date\n" +
      "C: Combo insert, update and query\n" +
      "Choose --->"
  }

}