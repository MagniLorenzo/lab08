package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link DeathNote} class.
 */
class TestDeathNote {

    private static final String HUMAN1 = "d3r4t8";
    private static final String HUMAN2 = "j8k3b9";
    private static final String KART_ACCIDENT = "karting accident";
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
        for (final String rule : DeathNote.RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    public void testNameInsertionInDN() {
        assertFalse(dNote.isNameWritten(HUMAN1));
        dNote.writeName(HUMAN1);
        assertTrue(dNote.isNameWritten(HUMAN1));
        assertFalse(dNote.isNameWritten(HUMAN2));
        assertFalse(dNote.isNameWritten(""));
    }

    @Test
    public void testDeathCauseInsertion() throws InterruptedException {
        try {
            dNote.writeDeathCause("broken nail");
            fail("Writing a cause of death before writing a name should have thrown an exception");
        } catch (IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        dNote.writeName(HUMAN1);
        assertEquals("heart attack", dNote.getDeathCause(HUMAN1));
        dNote.writeName(HUMAN2);
        assertTrue(dNote.writeDeathCause(KART_ACCIDENT));
        assertEquals(KART_ACCIDENT, dNote.getDeathCause(HUMAN2));
        Thread.sleep(100);
        assertFalse(dNote.writeDeathCause("broken nail"));
        assertEquals(KART_ACCIDENT, dNote.getDeathCause(HUMAN2));
    }

    @Test
    public void testDeathDetailsInsertion() throws InterruptedException{
        try {
            dNote.writeDetails("ran for too long");
            fail("Writing the death details before writing a name should have thrown an exception");
        } catch (IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        dNote.writeName(HUMAN1);
        assertTrue(dNote.getDeathDetails(HUMAN1).isBlank());
        assertTrue(dNote.writeDetails("ran for too long"));
        assertEquals(new String("ran for too long"), dNote.getDeathDetails(HUMAN1));
        dNote.writeName(HUMAN2);
        Thread.sleep(6100);
        assertFalse(dNote.writeDetails("pet a puma thinking it was a dog"));
        assertEquals(new String(), dNote.getDeathDetails(HUMAN2));
        assertEquals(new String("ran for too long"), dNote.getDeathDetails(HUMAN1));
    }
}