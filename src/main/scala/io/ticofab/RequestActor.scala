package io.ticofab

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.{ask, pipe}
import akka.routing.FromConfig
import akka.stream.actor.ActorPublisherMessage.Request

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * digicaf
  * Created by fabiotiriticco on 18/04/2017.
  */
class RequestActor extends Actor with ActorLogging {
  val router = context.actorOf(FromConfig.props(ResponseActor()), "poolRouter")

  router ! GiveResponse("tell")
  router ! GiveResponse("tell")
  router ! GiveResponse("tell")
  router ! GiveResponse("tell")

  override def receive = {
    case g: GiveResponse => (router ? g) (1.second).mapTo[Request] pipeTo sender
  }
}

object RequestActor {
  def apply() = Props(new RequestActor())
}
