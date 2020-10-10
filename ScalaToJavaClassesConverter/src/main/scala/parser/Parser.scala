package parser

import parser.SemanticElement.CaseClass

object Parser
{
    def parse(string : String) : Seq[SemanticElement.CaseClass] =
    {
        val lexical = Lexical.lexicalAnalysis(string)
        val semantic = Semantic.semanticAnalysis(lexical)
        semantic
    }
}
