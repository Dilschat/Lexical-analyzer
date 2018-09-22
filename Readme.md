# Lexer for Scala language (Homework 2)
This lexer implemented according specifications from [offical site](https://www.scala-lang.org/files/archive/spec/2.11/) of Scala language.
We choose Java language for implementation.

### Our notation for Tokens
Types of Tokens:  
  * **Delimiters** \
   [;|,|.]{}(): 
  * **Operators(including syntax noise)** \
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
  
  This file contains tokens in format: __(Type of token, Body of token)__ \
  For example: \
  >>> (String literal,"string") \
  >>> (Numeric literal, 123)
  
 ### How to run it
  1. For building this project you need to install [Maven](https://maven.apache.org/). 
  2. For running it with sample code in input.txt just run build_and_run.sh 
  3. Standalone function **getNextToken()** presented in class Tokenizer. and returns string that presents token in format described above.
  
 

  
