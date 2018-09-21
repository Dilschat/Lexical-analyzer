import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class NumericalTypeUtilsTest {

    @org.junit.jupiter.api.Test
    void isNumberLiteralTest() {
        assertTrue(NumericalTypeUtils.isNumberLiteral("12"));
        assertTrue(NumericalTypeUtils.isNumberLiteral("0"));
        assertTrue(NumericalTypeUtils.isNumberLiteral("3.0"));
        assertTrue(NumericalTypeUtils.isNumberLiteral("3.0e"));
        assertTrue(NumericalTypeUtils.isNumberLiteral("1fasa"));
        assertFalse(NumericalTypeUtils.isNumberLiteral("-123"));
        assertFalse(NumericalTypeUtils.isNumberLiteral("+wqe"));
        assertFalse(NumericalTypeUtils.isNumberLiteral("+123"));
        assertFalse(NumericalTypeUtils.isNumberLiteral(".23"));
    }

    @org.junit.jupiter.api.Test
    void processNumericLiteral() throws Exception {
        assertEquals(NumericalTypeUtils.processNumericLiteral("", "12", 0),
                new Token("12", Token.LITERAL_NUMERIC));
        assertEquals(NumericalTypeUtils.processNumericLiteral("","3.0",0),new Token("3.0",Token.LITERAL_NUMERIC));
        assertEquals(NumericalTypeUtils.processNumericLiteral("","3.0000e-12",0),new Token("3.0000e-12",Token.LITERAL_NUMERIC));
        Executable closureContainingCodeToTest = () -> {NumericalTypeUtils.processNumericLiteral("","dsad3.0000e-12",0);};
        assertThrows(Exception.class,closureContainingCodeToTest,"is not beginning with numeric");
        closureContainingCodeToTest = () -> {NumericalTypeUtils.processNumericLiteral("","-3.0000e-12",0);};
        assertThrows(Exception.class,closureContainingCodeToTest,"is not beginning with numeric");
        closureContainingCodeToTest = () -> {NumericalTypeUtils.processNumericLiteral("","+3.0000e-12",0);};
        assertThrows(Exception.class,closureContainingCodeToTest,"is not beginning with numeric");

    }
}