package io.ticofab

import akka.actor.{Actor, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * digicaf
  * Created by fabiotiriticco on 17/04/2017.
  */
case object Yo

final case class Response(text: String, amount: Int)

class ResponseActor extends Actor {

  override def receive = {
    case Yo =>
      val originalSender = sender
      context.system.scheduler.scheduleOnce(1.second, originalSender, Response("hello", 4))
  }
}

object ResponseActor {
  def apply() = Props(new ResponseActor)
}