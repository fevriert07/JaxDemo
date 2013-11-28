package jax.scala.jaxdemo.eventbus

import com.google.common.eventbus.{Subscribe, EventBus}

import jax.scala.jaxdemo.eventhandler.{QueryEventHandler, UploadDataEventHandler}
import jax.scala.jaxdemo.events.{PersonRecordEvent, QueryDataEvent, UploadDataEvent}
import jax.scala.jaxdemo.view.PersonRecordView

class EventBusAssembly {
	val eventBus = new EventBus
	val personRecordView = PersonRecordView()
	val uploadDataEventHandler = UploadDataEventHandler(eventBus)
	val queryEventHandler = QueryEventHandler(personRecordView)	
	eventBus.register(this)
	
			
	@Subscribe
	def handlePersonRecordEvent( event: PersonRecordEvent){
		personRecordView.applyEvent(event)
	}
	
	@Subscribe
	def handleUploadDateEvent( event:UploadDataEvent){
		uploadDataEventHandler.applyEvent(event)
	}
	
	@Subscribe
	def handleQueryDataEvent(event: QueryDataEvent ){
		queryEventHandler.applyEvent(event)		
	}

}
