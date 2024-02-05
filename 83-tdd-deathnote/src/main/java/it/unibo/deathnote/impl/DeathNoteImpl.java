package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    @Override
    public String getRule(int ruleNumber) {
        throw new RuntimeException();
    }

    @Override
    public void writeName(String name) {
        throw new RuntimeException();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        throw new RuntimeException();
    }

    @Override
    public boolean writeDetails(String details) {
        throw new RuntimeException();
    }

    @Override
    public String getDeathCause(String name) {
        throw new RuntimeException();
    }

    @Override
    public String getDeathDetails(String name) {
        throw new RuntimeException();
    }

    @Override
    public boolean isNameWritten(String name) {
        throw new RuntimeException();
    }
}
