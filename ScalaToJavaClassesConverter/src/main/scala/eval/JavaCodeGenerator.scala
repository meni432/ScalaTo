package eval

import parser.SemanticElement

object JavaCodeGenerator
{
    private val PRIMITIVE_OBJECT_NAME_MAPPER = Map("Boolen" -> "bool", "Int" -> "int", "Long" -> "long", "Double" -> "double", "Float" -> "float")
    private val SCALA_TYPE_TO_JAVA_TYPE = Map("Int" -> "Integer")
    
    val a : Int = 1;
    
    def generator(caseClasses : Seq[SemanticElement.CaseClass]) =
    {
        caseClasses.map(caseClassToString).mkString("\n\n")
    }
    
    private def variableToString(variable : SemanticElement.Variable) : String =
    {
        val SemanticElement.Variable(variableName, scalaVariableType, scalaVariableInnerTypeOption) = variable
        val javaVariableType = PRIMITIVE_OBJECT_NAME_MAPPER.getOrElse(scalaVariableType, scalaVariableType)
        scalaVariableInnerTypeOption match
        {
            case Some(scalaVariableInnerType) =>
            {
                s"$javaVariableType<${SCALA_TYPE_TO_JAVA_TYPE.getOrElse(scalaVariableInnerType, scalaVariableInnerType)}> $variableName;"
            }

            case None =>
            {
                s"$javaVariableType $variableName;"
            }
        }
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
