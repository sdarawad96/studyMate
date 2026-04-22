package edu.westga.comp2320.studymate.model;

/**
 * Represents one study session with a day, subject, and task.
 * A study session must always have a valid day and a non-empty subject.
 */
public class StudySession {

    private String day;
    private String subject;
    private String task;

    /**
     * Creates a new StudySession object.
     *
     * @param day the day of the week as M, T, W, R, or F
     * @param subject the subject for the study session
     * @param task the task for the study session
     * @throws IllegalArgumentException if the day is invalid or the subject is blank
     */
    public StudySession(String day, String subject, String task) {

        String cleanDay = day.trim().toUpperCase();

        if (!this.isValidDay(cleanDay)) {
            throw new IllegalArgumentException("Day must be M, T, W, R, or F.");
        }

        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject is required.");
        }

        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        this.day = cleanDay;
        this.subject = subject.trim();
        this.task = task.trim();
    }

    /**
     * Gets the day of the week.
     *
     * @return the day of the week
     */
    public String getDay() {
        return this.day;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * Gets the task.
     *
     * @return the task
     */
    public String getTask() {
        return this.task;
    }

    /**
     * Returns the study session as a formatted string.
     *
     * @return the formatted study session string
     */
    @Override
    public String toString() {
        String fullDay = this.getFullDayName();

        if (this.task.isBlank()) {
            return fullDay + ": " + this.subject;
        }

        return fullDay + ": " + this.subject + " - " + this.task;
    }

    /**
     * Converts the stored day code into its full day name.
     *
     * @return the full day name
     */
    private String getFullDayName() {
        switch (this.day) {
            case "M":
                return "Monday";
            case "T":
                return "Tuesday";
            case "W":
                return "Wednesday";
            case "R":
                return "Thursday";
            case "F":
                return "Friday";
            default:
                throw new IllegalStateException("Unexpected day value: " + this.day);
        }
    }

    /**
     * Checks whether a day value is valid.
     *
     * @param day the day value to check
     * @return true if valid, false otherwise
     */
    private boolean isValidDay(String day) {
        if (day == null || day.isBlank()) {
            return false;
        }

        String upperDay = day.trim().toUpperCase();
        return upperDay.equals("M")
                || upperDay.equals("T")
                || upperDay.equals("W")
                || upperDay.equals("R")
                || upperDay.equals("F");
    }
}