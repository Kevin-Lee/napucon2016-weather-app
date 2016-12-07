package controllers

import javax.inject.{Inject, Singleton}

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.stream.Materializer
import play.api.Configuration
import play.api.libs.streams.ActorFlow
import play.api.libs.ws.WSClient
import play.api.mvc.{Controller, WebSocket}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author Kevin Lee
  * @since 2016-11-25
  */
@Singleton
class Napucon2016Controller @Inject() (implicit config: Configuration,
                                       wsClient: WSClient,
                                       system: ActorSystem,
                                       materializer: Materializer) extends Controller {

  private val apiKey = config.getString("weather.api.key")
    .getOrElse(throw new IllegalStateException("No weather.api.key is set."))

  def ws: WebSocket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => WeatherActor.props(apiKey, wsClient, out))
  }
}

object WeatherActor {
  def props(apiKey: String, wsClient: WSClient, out: ActorRef): Props =
    Props(new WeatherActor(apiKey, wsClient, out))
}

class WeatherActor(val apiKey: String,
                   val wsClient: WSClient,
                   val out: ActorRef) extends Actor
                                         with ActorLogging {

  private final val LocationPattern = """([^,]+),([^,]+)""".r

  override def receive: Receive = {
    case msg: String => msg match {
      case LocationPattern(city, country) =>
        wsClient.url(s"http://api.openweathermap.org/data/2.5/weather?q=$city,$country&appid=$apiKey")
                .get()
                .map { response =>
                  out ! response.body
                }

      case _ =>
        sys.error("Unknown message") // replace it with proper response to the user.

    }
  }
}