import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class StringLiteralUtilsTest {

    @Test
    void isBeginningStringLiteral() {
        assertTrue(StringLiteralUtils.isBeginningStringLiteral("\""));
        assertFalse(StringLiteralUtils.isBeginningStringLiteral("fsa\""));

    }

    @Test
    void processStringLiteral() throws Exception {
        assertEquals(StringLiteralUtils.processStringLiteral("\"a\""), new Token("\"a\"",Token.LITERAL_STRING));
        Executable closureContainingCodeToTest = () -> { StringLiteralUtils.processStringLiteral("\"asd");};
        assertThrows(Exception.class,closureContainingCodeToTest,"Unclosed string literal: \"asd");
    }

    @Test
    void isMultilineStringLiteral() {
        assertTrue(StringLiteralUtils.isMultilineStringLiteral("\"\"\""));
        assertFalse(StringLiteralUtils.isMultilineStringLiteral("\"\"fsa\""));
    }

    @Test
    void processMultilineString() throws Exception {
        Scanner sc = new Scanner("");
        assertEquals(StringLiteralUtils.processMultilineString("\"\"\"a\"\"\"",sc), new Token("\"\"\"a\"\"\"",Token.LITERAL_MULTILINE_STRING));
        Scanner sc1 = new Scanner("");
        Executable closureContainingCodeToTest = () -> { StringLiteralUtils.processMultilineString("\"\"\"asd", sc1);};
        assertThrows(Exception.class,closureContainingCodeToTest,"Unnclosed multiline string: : \"\"\"asd");

    }
}