package jax.scala.jaxdemo.view

import org.joda.time.DateTime

sealed case class PersonRecord(id: String = null, firstName: String = null, middleInitial: String = null, lastName: String = null, ssn: String = null,
  dob: DateTime = null) {
  override def toString = s"PersonRecord [id=$id, firstName=$firstName, lastName=$lastName, middleInitial=$middleInitial, ssn=$ssn, dob=${dob.toLocalDate}]"

}