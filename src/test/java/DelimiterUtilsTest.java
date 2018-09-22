import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DelimiterUtilsTest {

    @Test
    void processDelimiter() {
        assertEquals(DelimiterUtils.processDelimiter(';'),new Token(";",Token.DELIMITER));
    }

    @Test
    void isDelimiter() {
        assertTrue(DelimiterUtils.isDelimiter('['));
        assertTrue(DelimiterUtils.isDelimiter(';'));
        assertTrue(DelimiterUtils.isDelimiter(','));
        assertTrue(DelimiterUtils.isDelimiter('.'));
        assertTrue(DelimiterUtils.isDelimiter(']'));
        assertTrue(DelimiterUtils.isDelimiter('{'));
        assertTrue(DelimiterUtils.isDelimiter('}'));
        assertTrue(DelimiterUtils.isDelimiter('('));
        assertTrue(DelimiterUtils.isDelimiter(')'));
        assertTrue(DelimiterUtils.isDelimiter(':'));
        assertFalse(DelimiterUtils.isDelimiter('a'));
    }

    @Test
    void isDelimiterFromString() {
        assertTrue(DelimiterUtils.isDelimiter("[ewre"));
        assertTrue(DelimiterUtils.isDelimiter(";dfdsf"));
        assertTrue(DelimiterUtils.isDelimiter(",sadd"));
        assertTrue(DelimiterUtils.isDelimiter(".sad"));
        assertTrue(DelimiterUtils.isDelimiter("]dsa"));
        assertTrue(DelimiterUtils.isDelimiter("{dsa"));
        assertTrue(DelimiterUtils.isDelimiter("}s"));
        assertTrue(DelimiterUtils.isDelimiter("(asdwedew"));
        assertTrue(DelimiterUtils.isDelimiter(")s"));
        assertTrue(DelimiterUtils.isDelimiter(":sadasdadw"));
        assertFalse(DelimiterUtils.isDelimiter("asadadsa"));
    }
}