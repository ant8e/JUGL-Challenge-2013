/*
 * Copyright [2013] [Antoine comte]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.antoinecomte.jugl.challenge2013

import spray.json.DefaultJsonProtocol
import spray.routing._
import spray.http.MediaTypes._
import spray.routing.directives.LoggingMagnet._

import akka.util.Duration
import java.util.concurrent.TimeUnit

/** Our Server */
object Main extends App with SimpleRoutingApp with spray.httpx.SprayJsonSupport {

  /** Predefined response for "OUI" */
  def OUI = complete {
    "OUI"
  }

  /** Predefined response for "NON" */
  def NON = complete {
    "NON"
  }

  /** route for the various questions */
  def questions = path("") {
    get {
      parameter('q.as[String] ?) {
        question =>
          question match {
            case None => respondWithMediaType(`text/html`) {
              complete {
                <html>
                  <body>
                    <h1>JUGL Challenge 2013 server</h1>
                  </body>
                </html>
              }
            }
            case Some("Quelle est ton adresse email") => complete("antoine.comte@gmail.com")
            case Some("Es tu abonne a la mailing list(OUI/NON)") => OUI
            case Some("Es tu heureux de participer(OUI/NON)") => OUI
            case Some("Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)") => OUI
            case Some("As tu bien recu le premier enonce(OUI/NON)") => OUI
            case Some("As tu trouve le dernier exercice difficile(OUI/NON)") => NON
            case Some("As tu bien recu le deuxieme enonce(OUI/NON)") => OUI
            case Some("Veux tu tenter ta chance pour gagner un des prix(reserve aux membres du JUGL)(OUI/NON)") => OUI
            case _ => complete {
              ""
            }
          }

      }
    }
  }

  /** basic storage of the problems text */
  var enoncesMap: Map[Int, String] = Map()

  /** Route for the problem texts */
  def enonce = path("enonce" / IntNumber) {
    id =>
      post {
        entity(as[String]) {
          s =>
            enoncesMap = enoncesMap + (id -> s)
            complete {
              "Got It Bro!"
            }

        }
      } ~ get {
        ctx =>
          val ennonce: Option[String] = enoncesMap.get(id)
          ennonce match {
            case Some(s) => ctx.complete(s)
            case None => ctx.complete(302, "")
          }
      }
  }

  /** Route minesweeper problem  */
  def minesweeper = path("minesweeper/resolve") {
    post {
      entity(as[String]) {
        s =>
          complete {
            MineSweeper.countMines(s)
          }
      }
    }
  }

  /** Json serialization definition for the diet problem */
  object ActivityJsonProtocol extends DefaultJsonProtocol {
    implicit val ActivityFormat = jsonFormat(Diet.Activity, "name", "value")
  }


  /** Route diet problem  */
  def diet = path("diet/resolve") {
    post {
      import ActivityJsonProtocol._
      entity(as[List[Diet.Activity]]) {
        activities =>
          complete {
            Diet.resolve(activities)
          }
      }
    }
  }

  /** All routes combined */
  def juglRoutes = dynamic {
    import akka.event.Logging._
    logRequest("jugl" -> InfoLevel) {
      questions ~ enonce ~ minesweeper ~ diet
    }
  }

  /** get address and port from Cloudfoundry env. properties, default to localhost:8080 */
  val interface: String = Option(System.getenv("VCAP_APP_HOST")).getOrElse("localhost")
  val port: Int = Option(System.getenv("VCAP_APP_PORT")).getOrElse("8080").toInt

  /** Let's get this shit done ! */
  startServer(interface, port = port)(juglRoutes)


}
