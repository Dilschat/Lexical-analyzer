import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.*;

class IdentifierKeywordUtilsTest {

    @Test
    void isIdentifierStart() {
        assertTrue(IdentifierKeywordUtils.isIdentifierStart("sdfausfa"));
        assertTrue(IdentifierKeywordUtils.isIdentifierStart("_sdfausfa"));
        assertTrue(IdentifierKeywordUtils.isIdentifierStart("@sdfausfa"));
        assertFalse(IdentifierKeywordUtils.isIdentifierStart("123sdfausfa"));
        assertTrue(IdentifierKeywordUtils.isIdentifierStart("+123sdfausfa"));
        assertTrue(IdentifierKeywordUtils.isIdentifierStart("/123sdfausfa"));
    }

    @Test
    void processIdentifier() {
        assertEquals(IdentifierKeywordUtils.processIdentifier("qw"),new Token("qw",Token.IDENTIFIER));
        assertEquals(IdentifierKeywordUtils.processIdentifier("for1"),new Token("qw",Token.IDENTIFIER));
        assertEquals(IdentifierKeywordUtils.processIdentifier("iff"),new Token("qw",Token.IDENTIFIER));
        assertEquals(IdentifierKeywordUtils.processIdentifier("if"),new Token("if",Token.KEYWORD));
        assertEquals(IdentifierKeywordUtils.processIdentifier("for"),new Token("if",Token.KEYWORD));
        assertEquals(IdentifierKeywordUtils.processIdentifier("catch"),new Token("if",Token.KEYWORD));
        assertEquals(IdentifierKeywordUtils.processIdentifier("else"),new Token("if",Token.KEYWORD));
    }

    @Test
    void isStartOfStrangeIdentifier() {
        assertTrue(IdentifierKeywordUtils.isStartOfStrangeIdentifier('`'));
        assertTrue(IdentifierKeywordUtils.isStartOfStrangeIdentifier('d'));
    }

    @Test
    void processStrangeIdentifier() {
        assertEquals(IdentifierKeywordUtils.processIdentifier("`qw`"),new Token("`qw`",Token.IDENTIFIER));
        Executable closureContainingCodeToTest = () -> { IdentifierKeywordUtils.processStrangeIdentifier("`");};
        assertThrows(Exception.class,closureContainingCodeToTest,"Incorrect strange identifier: `");

    }
}