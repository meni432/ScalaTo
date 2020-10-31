package eval

import parser.SemanticElement
import scala.annotation.tailrec

object JavaCodeGenerator
{
    private val PRIMITIVE_OBJECT_NAME_MAPPER = Map("Boolen" -> "bool", "Int" -> "Integer", "Long" -> "long", "Double" -> "double", "Float" -> "float", "Seq" -> "List", "Option" -> "Optional")
    
    def generator(caseClasses : Seq[SemanticElement.CaseClass]) =
    {
        caseClasses.map(caseClassToString).mkString("\n\n")
    }
    
    private def toJavaSting(stringType : String) : String =
    {
        val javaVariableType = PRIMITIVE_OBJECT_NAME_MAPPER.getOrElse(stringType, stringType)
        javaVariableType
    }
    
    @tailrec
    private def typeToString(variableType : SemanticElement.VariableType, rightAcc : String = "", leftAcc : String = "") : String =
    {
        variableType match
        {
            case variableTypeString :: Nil =>
            {
                s"$rightAcc${toJavaSting(variableTypeString)}$leftAcc"
            }
            
            case variableTypeString :: tail =>
            {
                typeToString(tail, s"$rightAcc${toJavaSting(variableTypeString)}<", s">$leftAcc")
            }
        }
    }
    
    private def variableToString(variable : SemanticElement.Variable) : String =
    {
        val SemanticElement.Variable(variableName, variableType) = variable
        val javaVariableType = typeToString(variableType)
        s"$javaVariableType $variableName;"
    }
    
    private def caseClassToString(caseClass : SemanticElement.CaseClass) : String =
    {
        val SemanticElement.CaseClass(caseClassName, caseClassVariable) = caseClass
        val stringBuilder = new StringBuilder()
        
        stringBuilder.append(s"class $caseClassName")
        stringBuilder.append("\n")
        stringBuilder.append("{")
        stringBuilder.append("\n")
        caseClassVariable.foreach
        {
            caseClassVariable =>
            {
                stringBuilder.append("\t")
                stringBuilder.append(variableToString(caseClassVariable))
                stringBuilder.append("\n")
            }
        }
        
        stringBuilder.append("}")
        
        stringBuilder.mkString
    }
}
