package jax.scala.jaxdemo.events

import org.joda.time.DateTime

sealed case class PersonRecordEvent(firstName: String, middleInitial: String, lastName: String, ssn: String, dob: DateTime) {
  override def toString = s"PersonRecordEvent [firstName=$firstName, lastName=$lastName, middleInitial=$middleInitial, ssn=$ssn, dob=${dob.toLocalDate}]"
}

sealed case class QueryDataEvent(queryDate: DateTime = null, querySsn: String = null, queryStartTime: Long = System.currentTimeMillis) {
  override def toString = s"QueryDataEvent [queryStartTime=$queryStartTime, queryDate=$queryDate, querySsn=$querySsn]"
}

sealed case class UploadDataEvent(dataFileName: String)