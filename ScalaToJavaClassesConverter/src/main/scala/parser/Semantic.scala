package parser

import parser.SemanticElement.{CaseClass, Variable}

object SemanticElement
{
    case class VariableType(variableType : String, variableInnerType : Option[String])
    
    case class Variable(variableName : String, variableType : VariableType)
    
    case class CaseClass(caseClassName : String, caseClassVariable : Seq[Variable])
}

object Semantic
{
    def semanticAnalysis(tokens : Seq[(String, Option[(String, (String, Option[String]), Seq[(String, (String, Option[String]))])])]) : Seq[CaseClass] =
    {
        tokens.map
        {
            case ((name, Some((firstVariableName, (firstVariableType, firstVariableInnerTypeOption), otherElements)))) =>
            {
                val firstVariable = SemanticElement.Variable(firstVariableName, SemanticElement.VariableType(firstVariableType, firstVariableInnerTypeOption))
                
                val otherVariable = otherElements.map
                {
                    case (variableName, (variableType, variableInnerTypeOption)) =>
                    {
                        SemanticElement.Variable(variableName, SemanticElement.VariableType(variableType, variableInnerTypeOption))
                    }
                }
                
                val combineVariable = firstVariable +: otherVariable
                
                SemanticElement.CaseClass(name, combineVariable)
            }

            case ((name, None)) =>
            {
                SemanticElement.CaseClass(name, Nil)
            }
        }
    }
}
