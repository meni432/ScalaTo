object LocalTestEventHandler extends App
{
    val scalaCode =
        """
          |case class A(a : Int, b : String)
          |case class B(a : String); // comment
          |case class C(a: String)
          |case class D(l : List[Int])
          |case class Test(numbers : Seq[Int], singleNumber : Int,  optionalNumber : Option[Int])
          |""".stripMargin
//      val scalaCode = "case class aba(a : A, b : B)"
    val requestHolder = RequestHolder(scalaCode)
    val resultHolder = RequestHandler.requestHandler(requestHolder)
    println(resultHolder)
}
