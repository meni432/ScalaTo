package parser

import parser.SemanticElement.{CaseClass, Variable}

object SemanticElement
{
    
    case class Variable(variableName : String, variableType : String)
    
    case class CaseClass(caseClassName : String, caseClassVariable : Seq[Variable])
    
}

object Semantic
{
    def semanticAnalysis(tokens : Seq[(String, Option[(String, String, Seq[(String, String)])])]) : Seq[CaseClass] =
    {
        tokens.map
        {
            case ((name, Some((firstVariableName, firstVariableType, otherElements)))) =>
            {
                val firstVariable = SemanticElement.Variable(firstVariableName, firstVariableType)
                
                val otherVariable = otherElements.map
                {
                    case (variableName, variableType) =>
                    {
                        SemanticElement.Variable(variableName, variableType)
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
