package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final int AMOUNT = 100;
    private static final int NEG_AMOUNT = -40;
    public static final double MANAGEMENT_FEE = 5;
    public static final double TRANSACTION_FEE = 0.1;
    private static final int ACCEPTABLE_MESSAGE_LENGTH = 10;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are
    // executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the
     * balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        assertEquals(0.0, this.bankAccount.getBalance());
        assertEquals(0, this.bankAccount.getTransactionsCount());
        this.bankAccount.deposit(this.mRossi.getUserID(), AMOUNT);
        assertEquals(100, this.bankAccount.getBalance());
        assertEquals(1, this.bankAccount.getTransactionsCount());
        double fees = MANAGEMENT_FEE + this.bankAccount.getTransactionsCount() * TRANSACTION_FEE;
        this.bankAccount.chargeManagementFees(this.mRossi.getUserID());
        assertEquals(AMOUNT - fees, this.bankAccount.getBalance());
        assertEquals(0, this.bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            this.bankAccount.withdraw(this.mRossi.getUserID(), NEG_AMOUNT);
            fail("Withdrawing a negative amount should have thrown an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(0, bankAccount.getBalance());
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        this.bankAccount.deposit(this.mRossi.getUserID(), AMOUNT);
        assertEquals(100, this.bankAccount.getBalance());
        assertEquals(1, this.bankAccount.getTransactionsCount());
        try {
            this.bankAccount.withdraw(this.mRossi.getUserID(), 2 * AMOUNT);
            fail("Withdrawing more money than it is in the account should have thrown an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(100, bankAccount.getBalance());
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }
    }
}
