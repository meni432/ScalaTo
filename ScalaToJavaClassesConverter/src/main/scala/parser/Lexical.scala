package parser


object Lexical extends App
{
    
    import fastparse._
    // TODO :: MS - remove ScalaWhitespace and support also comment converter
//    import MultiLineWhitespace._
    import ScalaWhitespace._
    
    private def alpha[_: P] = P(CharPred(_.isLetter))
    
    private def digit[_: P] = P(CharPred(_.isDigit))
    
    private def legalName[_: P] = P(alpha ~ (alpha | digit).rep)
    
    private def whitespacePrefix[_ : P] = P((" " | "\t" | "\r" | "\n" | ";").rep)
    
    private def caseClassSymbol[_: P] = P(whitespacePrefix ~ "case class")
    
    private def genericType[_: P] = P(legalName.! ~ ("[" ~ legalName.! ~ "]").?)
    
    private def variable[_: P] = P(legalName.! ~ ":" ~ genericType)
    
    private def variablesList[_: P] = P(variable ~ ("," ~ variable).rep)
    
    private def caseClass[_: P] = P(caseClassSymbol ~ legalName.! ~ "(" ~ variablesList.? ~ ")")
    
    private def parseCaseClasses[_: P] = P(caseClass.rep(1))
    
    val s @ Parsed.Success(value, successIndex) = parse("case class aba(a : A, b : B) \n case class xyz()", parseCaseClasses(_))
    println(s)
    
    
    def lexicalAnalysis(string : String) : Seq[(String, Option[(String, (String, Option[String]), Seq[(String, (String, Option[String]))])])] =
    {
        val Parsed.Success(value, successIndex) =  parse(string, parseCaseClasses(_))
        value
    }
}
