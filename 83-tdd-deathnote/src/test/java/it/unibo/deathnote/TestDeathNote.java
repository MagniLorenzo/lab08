package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link DeathNote} class.
 */
class TestDeathNote {

    private DeathNote dNote = new DeathNoteImpl();

    @Test
    public void testGetRule() {
        try {
            dNote.getRule(0);
            fail("Getting the rule in position 0 should have thrown an exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        try {
            dNote.getRule(-1);
            fail("Getting the rule in a negative position should have thrown an exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

    @Test
    public void testRules() {
        for (final String rule : DeathNote.RULES){
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }


}