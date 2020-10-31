package parser

import parser.SemanticElement.{CaseClass, Variable, VariableType}
import parser.TypeElement.HierarchyType
import scala.annotation.tailrec

object TypeElement
{
    type TypeDefinition = String
    type HierarchyType = Seq[TypeDefinition]
    val EMPTY_TYPE = Seq.empty[TypeDefinition]
    
    @tailrec
    def convertToType(tokens : Lexical.TypeBox, acc : HierarchyType = EMPTY_TYPE) : HierarchyType =
    {
        tokens match
        {
            case (typeName, None) =>
            {
                acc :+ typeName
            }

            case (typeName, Some(innerType : Lexical.TypeBox)) =>
            {
                convertToType(innerType, acc :+ typeName)
            }
        }
    }
}

object SemanticElement
{
    type VariableType = Seq[String]
    
    case class Variable(variableName : String, variableType : VariableType)
    
    case class CaseClass(caseClassName : String, caseClassVariable : Seq[Variable])
}

object Semantic
{
    def semanticAnalysis(tokens : Seq[(String, Option[(String, Lexical.TypeBox, Seq[(String, Lexical.TypeBox)])])]) : Seq[CaseClass] =
    {
        tokens.map
        {
            case ((name, Some((firstVariableName, typeBox, otherElements)))) =>
            {
                val hierarchyType = TypeElement.convertToType(typeBox)
                val firstVariable = SemanticElement.Variable(firstVariableName, hierarchyType)
                
                val otherVariable = otherElements.map
                {
                    case (variableName, innerTypeBox) =>
                    {
                        val hierarchyType = TypeElement.convertToType(innerTypeBox)
                        SemanticElement.Variable(variableName, hierarchyType)
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
