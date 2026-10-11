package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.constants.AppConstants;

public class Student extends Person {

    private String batch;
    private boolean active;

    public Student() {
        super();
        this.active = true;
    }

    // Constructor with email
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    // Overloaded constructor without email
    public Student(int id, String firstName, String lastName, String batch) {
        this(id, firstName, lastName, AppConstants.DEFAULT_EMAIL, batch);
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " | " + getDisplayName() + " | " + getEmail()
                + " | " + (active ? "ACTIVE" : "INACTIVE");
    }
}
