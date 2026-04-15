package edu.westga.comp2320.studymate;

import edu.westga.comp2320.studymate.model.StudySession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controller for the StudyMate GUI.
 * Handles user interactions such as adding and deleting study sessions.
 */
public class StudyMateController {

    @FXML
    private TextField dayField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField taskField;

    @FXML
    private ListView<StudySession> sessionListView;

    @FXML
    private Label dayErrorLabel;

    @FXML
    private Label subjectErrorLabel;

    /**
     * Handles the Add button click.
     * Validates input and adds a new StudySession to the ListView if valid.
     */
    @FXML
    public void addSession() {
        this.clearErrorMessages();

        String day = this.dayField.getText().trim();
        String subject = this.subjectField.getText().trim();
        String task = this.taskField.getText().trim();

        boolean hasError = false;

        if (day.isEmpty() || !this.isValidDay(day)) {
            this.dayErrorLabel.setText("must be M, T, W, R, or F");
            hasError = true;
        }

        if (subject.isEmpty()) {
            this.subjectErrorLabel.setText("required");
            hasError = true;
        }

        if (hasError) {
            return;
        }

        StudySession newSession = new StudySession(day, subject, task);
        this.sessionListView.getItems().add(newSession);
        this.sessionListView.getSelectionModel().select(newSession);
    }

    /**
     * Handles the Delete button click.
     * This will be fully implemented in a later task.
     */
    @FXML
    public void deleteSession() {
        // To be implemented later.
    }

    /**
     * Checks if the provided day is valid.
     *
     * @param day the day input by the user
     * @return true if valid, false otherwise
     */
    private boolean isValidDay(String day) {
        String upperDay = day.toUpperCase();
        return upperDay.equals("M")
                || upperDay.equals("T")
                || upperDay.equals("W")
                || upperDay.equals("R")
                || upperDay.equals("F");
    }

    /**
     * Clears all error messages from the GUI.
     */
    private void clearErrorMessages() {
        this.dayErrorLabel.setText("");
        this.subjectErrorLabel.setText("");
    }
}