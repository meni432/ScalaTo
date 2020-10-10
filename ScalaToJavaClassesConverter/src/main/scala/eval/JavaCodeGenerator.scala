package eval

import parser.SemanticElement

object JavaCodeGenerator
{
    def generator(caseClasses : Seq[SemanticElement.CaseClass]) =
    {
        caseClasses.map(caseClassToString).mkString("\n\n")
    }
    
    private def variableToString(variable : SemanticElement.Variable) : String =
    {
        val SemanticElement.Variable(variableName, variableType) = variable
        s"$variableType $variableName;"
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
