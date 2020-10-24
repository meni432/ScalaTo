package eval

import parser.SemanticElement

object JavaCodeGenerator
{
    private val PRIMITIVE_OBJECT_NAME_MAPPER = Map("Boolen" -> "bool", "Int" -> "int", "Long" -> "long", "Double" -> "double", "Float" -> "float")
    private val SCALA_TYPE_TO_JAVA_TYPE = Map("Int" -> "Integer", "Seq" -> "List")
    
    val a : Int = 1;
    
    def generator(caseClasses : Seq[SemanticElement.CaseClass]) =
    {
        caseClasses.map(caseClassToString).mkString("\n\n")
    }
    
    private def typeToString(variableType : SemanticElement.VariableType) : String =
    {
        val SemanticElement.VariableType(scalaVariableType, scalaVariableInnerTypeOption) = variableType
        val javaVariableType = PRIMITIVE_OBJECT_NAME_MAPPER.getOrElse(scalaVariableType, scalaVariableType)
        val javaVariableInnerTypeOption = scalaVariableInnerTypeOption.map(scalaVariableInnerType => SCALA_TYPE_TO_JAVA_TYPE.getOrElse(scalaVariableInnerType, scalaVariableInnerType))
    
        javaVariableInnerTypeOption match
        {
            case Some(javaVariableInnerType) =>
            {
                s"$javaVariableType<$javaVariableInnerType>"
            }

            case None =>
            {
                javaVariableType
            }
        }
    }
    
    private def variableToString(variable : SemanticElement.Variable) : String =
    {
        val SemanticElement.Variable(variableName, variableType) = variable
        val javaVariableType = typeToString(variableType)
        s"$javaVariableType $variableName;"
    }
    
    private def variableToGetter(variable : SemanticElement.Variable) : String =
    {
        val SemanticElement.Variable(variableName, variableType) = variable
        val javaVariableType = typeToString(variableType)
        s"""public $javaVariableType get${variableName.capitalize} {
           |    return $variableName;
           |}""".stripMargin
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
        
        caseClassVariable.foreach
        {
            caseClassVariable =>
            {
                stringBuilder.append("\n")
                stringBuilder.append(variableToGetter(caseClassVariable))
                stringBuilder.append("\n")
            }
        }
        
        stringBuilder.append("}")
        
        stringBuilder.mkString
    }
}
