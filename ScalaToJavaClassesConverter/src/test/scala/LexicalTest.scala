import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import parser.Lexical

class LexicalTest extends AnyFlatSpec with Matchers
{
    behavior of "LexicalTest"
    
    it should "parse empty case class" in
    {
        val input = "case class A()"
        val output = Lexical.lexicalAnalysis(input)
    
        output shouldBe Seq(("A", None))
    }
    
    it should "parse case class with one argument" in
    {
        val input = "case class A(a : Int)"
        val output = Lexical.lexicalAnalysis(input)
        
        output shouldBe Seq(("A",Some(("a",("Int", None),Nil))))
    }
    
    it should "parse multiple arguments case classes" in
    {
        val input = "case class A(a : Int, b : Int)"
        val output = Lexical.lexicalAnalysis(input)
    
        output shouldBe Seq(("A",Some(("a",("Int", None),Seq(("b", ("Int", None)))))))
    }
    
    it should "parse case class with generic type argument" in
    {
        val input = "case class A(a : Box[Inner])"
        val output = Lexical.lexicalAnalysis(input)
    
        output shouldBe Seq(("A",Some(("a",("Box",Some("Inner")),Seq()))))
    }
}
