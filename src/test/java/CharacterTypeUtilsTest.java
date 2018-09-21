import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.*;

class CharacterTypeUtilsTest {

    @Test
    void isCharacter() {
        assertTrue(CharacterTypeUtils.isCharacter("'"));
        assertTrue(CharacterTypeUtils.isCharacter("'sfdfsdf"));
        assertTrue(CharacterTypeUtils.isCharacter("'fdsf'"));
        assertFalse(CharacterTypeUtils.isCharacter(""));
        assertFalse(CharacterTypeUtils.isCharacter("dsfdsfsdfsd"));
        assertFalse(CharacterTypeUtils.isCharacter("fdsfsd'"));
    }

    @Test
    void processCharacter() throws Exception {
        assertEquals(CharacterTypeUtils.processCharacter("'a'",0),new Token("'a'",Token.LITERAL_CHARACTER));
        assertEquals(CharacterTypeUtils.processCharacter("'\n'",0),new Token("'\n'",Token.LITERAL_CHARACTER));
        assertEquals(CharacterTypeUtils.processCharacter("'l'",0),new Token("'l'",Token.LITERAL_CHARACTER));
        Executable closureContainingCodeToTest = () -> {CharacterTypeUtils.processCharacter("'ldsfs'",0);};
        assertThrows(Exception.class,closureContainingCodeToTest,"wrong character");
        closureContainingCodeToTest = () -> {CharacterTypeUtils.processCharacter("''",0);};
        assertThrows(Exception.class,closureContainingCodeToTest,"wrong character");
        closureContainingCodeToTest = () -> {CharacterTypeUtils.processCharacter("'\\dsfdsfsdf'",0);};
        assertThrows(Exception.class,closureContainingCodeToTest,"wrong character");//TODO check bug

    }
}