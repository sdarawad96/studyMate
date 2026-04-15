package edu.westga.comp2320.studymate.model;

/**
 * Represents a study session with a day, subject, and task.
 */
public class StudySession {

    private String day;
    private String subject;
    private String task;

    /**
     * Creates a StudySession.
     *
     * @param day the day of the week
     * @param subject the subject being studied
     * @param task the task description
     */
    public StudySession(String day, String subject, String task) {
        this.day = day;
        this.subject = subject;
        this.task = task;
    }

    /**
     * Gets the day of the week.
     *
     * @return the day
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
     * Returns a string representation of the study session.
     *
     * @return formatted study session string
     */
    @Override
    public String toString() {
        return this.getFullDayName() + ": " + this.subject + " - " + this.task;
    }

    /**
     * Converts single-letter day into full day name.
     *
     * @return full day name
     */
    private String getFullDayName() {
        switch (this.day.toUpperCase()) {
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
                return this.day;
        }
    }
}