import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperatorUtilsTest {

    @Test
    void processOperator() {
        assertEquals(OperatorUtils.processOperator("+"),new Token("+",Token.OPERATOR));
        assertEquals(OperatorUtils.processOperator("+="),new Token("+=",Token.OPERATOR));
    }

    @Test
    void isBeginningOperator() {
        assertTrue(OperatorUtils.isBeginningOperator("+"));
        assertTrue(OperatorUtils.isBeginningOperator("+="));
        assertTrue(OperatorUtils.isBeginningOperator("=="));
        assertFalse(OperatorUtils.isBeginningOperator("sfsaf"));
        assertFalse(OperatorUtils.isBeginningOperator("±"));
        assertFalse(OperatorUtils.isBeginningOperator("#"));
    }

    @Test
    void isSyntaxNoiseOperator() {
        assertTrue(OperatorUtils.isSyntaxNoiseOperator(":+"));
        assertTrue(OperatorUtils.isSyntaxNoiseOperator("::"));
        assertTrue(OperatorUtils.isSyntaxNoiseOperator(":::"));
        assertFalse(OperatorUtils.isSyntaxNoiseOperator("+++"));
    }

    @Test
    void processSyntaxNoiseOperator() {
        assertEquals(OperatorUtils.processSyntaxNoiseOperator(":::"),new Token("::",Token.OPERATOR_NOISE));
    }
}