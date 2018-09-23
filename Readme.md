# Lexer for Scala language (Homework 2)
This lexer implemented according specifications from [offical site](https://www.scala-lang.org/files/archive/spec/2.11/) of Scala language.
We choose Java language for implementation.

### Our notation for Tokens
Types of Tokens:  
  * **Delimiters** \
   [;,.]{}():
  * **Operators (with syntax noise operators)** \
  "+", "-", "*", "/", "%",
      "==", "<=", ">=", "!=", ">", "<", "!",
      "||", "&&", "&", "|", "~", "^", ">>>",
       <<", ">>", "=", "+=", "-=", "*=", "/=",
       "%=", "<<=", ">>=", "^=", "|=", "&="
       ":+", "::", "+:", "->", "<-", "=>", "<%"
  * **KeyWords** \
  "case", "catch", "class",
       "def", "do", "else", "extends", "false", "final", "if",
       "print", "printf", "println",
       "to", "true", "try", "until", "var", "while", "with",
       "abstract", "finally", "import", "null", "protected", "throw",
       "val", "case", "for", "lazy", "object", "return", "trait",
       "catch", "forSome", "macro", "override",
       "sealed", "class", "match", "package",
       "super", "implicit", "new", "private",
       "this", "type", "yield"
  * **Identifiers**
        Identifier can start with a letter which can be followed by
  an arbitrary sequence of letters and digits.
  This may be followed by underscore ‘_‘ characters
  and another string composed of either letters and digits or of operator characters.
  
  The ‘$’ character is reserved for compiler-synthesized identifiers.
  User programs should not define identifiers which contain ‘$’ characters.
  * **Literals**
    - Line string
    - Multiline string
    - Numeric
    - Character
    
  _Format of output_: file with name [source file name]_tokenized.txt
  
  This file contains tokens in format: __(Type of token, Body of token)__ \
  For example: \
  >>> (String literal,"string")(Delimiter,\n) \
  >>> (Numeric literal, 123)
  
 ### How to run it
  1. For building this project you need to install [Maven](https://maven.apache.org/). 
  2. For running it with sample code in input.txt just run build_and_run.sh 
  3. Standalone function **getNextToken()** presented in class Tokenizer. and returns string that presents token in format described above.
  
 

  
