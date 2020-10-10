import eval.JavaCodeGenerator
import parser.Parser
import scala.concurrent.Future

object RequestHandler
{
    
    def requestHandler(requestHolder : RequestHolder) : ResultHolder =
    {
        val RequestHolder(scalaCode) = requestHolder
        val parsedCode = Parser.parse(scalaCode);
        val javaCode = JavaCodeGenerator.generator(parsedCode)
        val result = javaCode
        val resultHolder = ResultHolder(result)
        resultHolder
    }
}
