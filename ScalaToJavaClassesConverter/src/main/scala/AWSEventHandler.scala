import java.io.{InputStream, OutputStream}
import spray.json._

object AWSEventHandler
{
    import Protocol._
    
    def eventHandler(input : InputStream, output : OutputStream)
    {
        val inputString = scala.io.Source.fromInputStream(input).mkString
        println(s"inputString: $inputString")
        val AWSRequest(body) = inputString.parseJson.convertTo[AWSRequest]
        val requestHolder = body.parseJson.convertTo[RequestHolder]
        val resultHolder = RequestHandler.requestHandler(requestHolder)
        val resultHolderJson = resultHolder.toJson.prettyPrint
        val awsResponse = AWSResponse(200, resultHolderJson)
        val awsResponseBodyJson = awsResponse.toJson.prettyPrint
        output.write(awsResponseBodyJson.getBytes("UTF-8"))
    }
}
