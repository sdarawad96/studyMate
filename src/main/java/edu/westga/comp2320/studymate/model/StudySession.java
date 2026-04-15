package edu.westga.comp2320.studymate.model;

public class StudySession {

    private String day;
    private String subject;
    private String task;

    public StudySession(String day, String subject, String task) {

        if (day == null || day.isBlank()) {
            throw new IllegalArgumentException("Day is required.");
        }

        String upperDay = day.trim().toUpperCase();

        if (!upperDay.equals("M") && !upperDay.equals("T")
                && !upperDay.equals("W") && !upperDay.equals("R")
                && !upperDay.equals("F")) {
            throw new IllegalArgumentException("Day must be M, T, W, R, or F.");
        }

        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject is required.");
        }

        this.day = upperDay;
        this.subject = subject.trim();
        this.task = (task == null) ? "" : task.trim();
    }

    public String getDay() {
        return this.day;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getTask() {
        return this.task;
    }

    @Override
    public String toString() {
        String fullDay = this.getFullDayName();

        if (this.task.isBlank()) {
            return fullDay + ": " + this.subject;
        }

        return fullDay + ": " + this.subject + " - " + this.task;
    }

    private String getFullDayName() {
        switch (this.day) {
            case "M": return "Monday";
            case "T": return "Tuesday";
            case "W": return "Wednesday";
            case "R": return "Thursday";
            case "F": return "Friday";
            default: return "";
        }
    }
}
