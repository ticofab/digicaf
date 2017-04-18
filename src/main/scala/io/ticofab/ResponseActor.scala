package io.ticofab

import akka.actor.{Actor, ActorLogging, Props}

/**
  * digicaf
  * Created by fabiotiriticco on 17/04/2017.
  */

class ResponseActor extends Actor with ActorLogging {

  override def receive = {
    case GiveResponse(name) =>
      log.debug("request from {}", name)
      sender ! Response("hello", 0)
  }
}

object ResponseActor {
  def apply() = Props(new ResponseActor)
}