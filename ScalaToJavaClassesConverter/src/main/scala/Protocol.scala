import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat2}

import spray.json._
import DefaultJsonProtocol._

case class RequestHolder(scalaCode : String)
case class AWSRequest(body : String)
case class ResultHolder(javaCode : String)
case class AWSResponse(statusCode : Int, body : String, headers : Map[String, String])

object Protocol
{
    implicit val RequestHolderFormat = jsonFormat1(RequestHolder)
    implicit val AWSRequestHolderFormat = jsonFormat1(AWSRequest)
    implicit val ResultHolderFormat = jsonFormat1(ResultHolder)
    implicit val AWSResponseBodyFormat = jsonFormat2(AWSResponse)
}
