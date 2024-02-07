package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DeathNoteImpl implements DeathNote {

    private static final int TIME_TO_ENTER_THE_CAUSE_OF_DEATH = 40;
    private static final int TIME_TO_ENTER_THE_DETAILS = 6040;
    private final Map<String, DeathNotePage> deathNote;
    private String actualName;
    private long timeNameEntered;

    public DeathNoteImpl() {
        this.deathNote = new HashMap<>();
        this.actualName = null;
        this.timeNameEntered = 0;
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Rule number" + ruleNumber + "doesn't exist. The first rule has number one.");
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        Objects.requireNonNull(name);
        this.deathNote.put(name, new DeathNotePage(name));
        this.timeNameEntered = System.currentTimeMillis();
        this.actualName = name;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (!isNameWritten(this.actualName) || cause == null) {
            throw new IllegalStateException("Impossible to enter the cause of death");
        }
        long delay = System.currentTimeMillis() - this.timeNameEntered;
        if (delay < TIME_TO_ENTER_THE_CAUSE_OF_DEATH) {
            this.deathNote.get(this.actualName).setCause(cause);
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if (!isNameWritten(this.actualName) || details == null) {
            throw new IllegalStateException("Impossible to enter the details of the human's death");
        }
        long delay = System.currentTimeMillis() - this.timeNameEntered;
        if (delay < TIME_TO_ENTER_THE_DETAILS) {
            this.deathNote.get(this.actualName).setDetails(details);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException(name + "is not written in this DeathNote");
        }
        return this.deathNote.get(name).getCause();
    }

    @Override
    public String getDeathDetails(String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException(name + "is not written in this DeathNote");
        }
        return this.deathNote.get(name).getDetails();
    }

    @Override
    public boolean isNameWritten(String name) {
        return this.deathNote.containsKey(name);
    }

    private static class DeathNotePage {
        private static final String DEFAULT_DEATH_CAUSE = "heart attack";
        private final String name;
        private String cause;
        private String details;

        public DeathNotePage(final String name) {
            this.name = name;
            this.cause = DEFAULT_DEATH_CAUSE;
            this.details = "";
        }

        public void setCause(final String cause) {
            this.cause = cause;
        }

        public String getCause() {
            return this.cause;
        }

        public void setDetails(final String details) {
            this.details = details;
        }

        public String getDetails() {
            return this.details;
        }

        public String getName() {
            return this.name;
        }
    }
}
