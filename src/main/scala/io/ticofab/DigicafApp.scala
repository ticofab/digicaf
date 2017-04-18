package io.ticofab

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.io.StdIn

/**
  * digicaf
  * Created by fabiotiriticco on 17/04/2017.
  */

case class GiveResponse(name: String)

case class Response(text: String, amount: Int)

object DigicafApp extends App {
  implicit val as = ActorSystem()
  implicit val am = ActorMaterializer()
  implicit val responseFormat = Json.format[Response]

  val requestActor = as.actorOf(RequestActor(), "requestActor")
  val failResponse = Response("fail", 0)

  def route: Route = get {
    val response = (requestActor ? GiveResponse("ask")) (500.millis).mapTo[Response]
    onComplete(response) {
      resp => complete(resp.getOrElse[Response](failResponse))
    }
  }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8008)
  println(s"Server online at http://localhost:8008. Press RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => as.terminate()) // and shutdown when done
}
