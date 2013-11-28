package jax.scala.jaxdemo.eventhandler

trait EventHandler[T] {
  def applyEvent(event: T)
}