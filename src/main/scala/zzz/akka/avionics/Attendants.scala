package zzz.akka.avionics

import akka.actor.{Props, ActorRef, Actor}
import scala.concurrent.duration._

trait AttendantResponsiveness {
  val maxResponseTimeMS: Int

  def responseDuration =
    scala.util.Random.nextInt(maxResponseTimeMS).millis
}

object FlightAttendant {

  case class GetDrink(drinkname: String)

  case class Drink(drinkname: String)

  def apply() = new FlightAttendant
    with AttendantResponsiveness {
    val maxResponseTimeMS = 300000
  }

}

class FlightAttendant extends Actor {
  this: AttendantResponsiveness =>

  import FlightAttendant._

  implicit val ec = context.dispatcher

  def receive = {
    case GetDrink(drinkName) =>
      context.system.scheduler.scheduleOnce(
        responseDuration, sender, Drink(drinkName)
      )
  }
}

trait AttendantCreationPolicy {
  val numberOfAttendants: Int = 8

  def createAttendant: Actor = FlightAttendant()
}

trait LeadFlightAttendantProvider {
  def newLeadFlightAttendant: Actor = LeadFlightAttendant()
}


object LeadFlightAttendant {

  object GetFlightAttendant

  case class Attendant(a: ActorRef)

  def apply() = new LeadFlightAttendant with AttendantCreationPolicy
}

class LeadFlightAttendant extends Actor {
  this: AttendantCreationPolicy =>

  import LeadFlightAttendant._

  override def preStart() {
    import scala.collection.JavaConverters._
    val attendantNames =
      context.system.settings.config.getStringList("zzz.akka.avionics.flightcrew.attendantNames").asScala

    attendantNames take numberOfAttendants foreach { name =>
      context.actorOf(Props(createAttendant), name)
    }
  }

  def randomAttendant(): ActorRef = {
    context.children.take(scala.util.Random.nextInt(numberOfAttendants) + 1).last
  }

  def receive = {
    case GetFlightAttendant =>
      sender ! Attendant(randomAttendant())
    case m =>
      randomAttendant() forward m
  }


}

object FlightAttendnatPathChecker {
  def main(args: Array[String]) {
    val system = akka.actor.ActorSystem("PlaneSimulation")
    val lead = system.actorOf(Props(
      new LeadFlightAttendant with AttendantCreationPolicy),
      system.settings.config.getString("zzz.akka.avionics.flightcrew.leadAttendantName"))

    Thread.sleep(2000)
    system.shutdown()
  }
}