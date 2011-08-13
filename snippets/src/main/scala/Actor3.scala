import scala.actors._
import Actor._

object Actor3
  {
    case class Speak(line : String);
    case class Gesture(bodyPart : String, action : String);
    case object NegotiateNewContract;
    case object ThatsAWrap;
  
    def main(args : Array[String]) =
    {
      def ct =
        "Thread " + Thread.currentThread().getName() + ": "
      val badActor =
        actor
        {
          var done = false
          while (! done)
          {
            receive
            {
              case NegotiateNewContract =>
                System.out.println(ct + "I won't do it for less than $1 million!")
              case Speak(line) =>
                System.out.println(ct + line)
              case Gesture(bodyPart, action) =>
                System.out.println(ct + "(" + action + "s " + bodyPart + ")")
              case ThatsAWrap =>
                System.out.println(ct + "Great cast party, everybody! See ya!")
                done = true
              case _ =>
                System.out.println(ct + "Huh? I'll be in my trailer.")
            }
          }
        }
      
      System.out.println(ct + "Negotiating...")
      badActor ! NegotiateNewContract
      System.out.println(ct + "Speaking...")
      badActor ! Speak("Do ya feel lucky, punk?")
      System.out.println(ct + "Gesturing...")
      badActor ! Gesture("face", "grimaces")
      System.out.println(ct + "Speaking again...")
      badActor ! Speak("Well, do ya?")
      System.out.println(ct + "Wrapping up")
      badActor ! ThatsAWrap
    }
  }