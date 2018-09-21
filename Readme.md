#Lexer for Scala language (Homework 2)
This lexer implemented according specifications from [offical site](https://www.scala-lang.org/files/archive/spec/2.11/) of Scala language.
We choose Java language for implementation.

###Our notation for Tokens
Types of Tokens:  
  * **Delimiters** \
   [;|,|.]{}(): 
  * **Operators** \
  "+", "-", "*", "/", "%",
                      "==", "<=", ">=", "!=", ">", "<", "!",
                      "||", "&&", "&", "|", "~", "^", ">>>",
                      "<<", ">>", "=", "+=", "-=", "*=", "/=",
                      "%=", "<<=", ">>=", "^=", "|=", "&="
  * **KeyWords** \
  case", "catch", "class",
              "def", "do", "else", "extends", "false", "final", "for", "if",
              "match", "new", "null", "print", "printf", "println", "throw",
              "to", "trait", "true", "try", "until", "val", "var", "while", "with"
  * **Identifiers**
  * **Literals**
    - Line
    - Multiline
    - Numeric
    - Character
    
  _Format of output_: file with name [source file name]_tokenized.txt
  
  This file contains