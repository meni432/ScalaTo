object LocalTestEventHandler extends App
{
    val scalaCode =
        """
          |case class A(a : String, b : String)
          |case class B(a : String); // comment
          |case class C(a: String)
          |""".stripMargin
//      val scalaCode = "case class aba(a : A, b : B)"
    val requestHolder = RequestHolder(scalaCode)
    val resultHolder = RequestHandler.requestHandler(requestHolder)
    println(resultHolder)
}
