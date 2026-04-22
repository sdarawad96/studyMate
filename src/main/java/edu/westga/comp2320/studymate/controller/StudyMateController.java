package edu.westga.comp2320.studymate.controller;

import edu.westga.comp2320.studymate.model.StudySession;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;

/**
 * Controller for the StudyMate GUI.
 * Handles adding, deleting, selecting, and validating study sessions.
 */
public class StudyMateController {

    @FXML
    private TextField subjectField;

    @FXML
    private TextField taskField;

    @FXML
    private ListView<StudySession> sessionListView;

    @FXML
    private Label subjectErrorLabel;

    @FXML
    private RadioButton mRadio;

    @FXML
    private RadioButton tRadio;

    @FXML
    private RadioButton wRadio;

    @FXML
    private RadioButton rRadio;

    @FXML
    private RadioButton fRadio;

    /**
     * Group that ensures only one day radio button can be selected.
     */
    private ToggleGroup dayGroup;

    private StudySession selectedSession;

    /**
     * Initializes the controller by setting up:
     * - ListView selection listener
     * - Radio button toggle group
     * - Default selected day (Monday)
     */
    @FXML
    public void initialize() {

        this.dayGroup = new ToggleGroup();

        this.mRadio.setToggleGroup(this.dayGroup);
        this.tRadio.setToggleGroup(this.dayGroup);
        this.wRadio.setToggleGroup(this.dayGroup);
        this.rRadio.setToggleGroup(this.dayGroup);
        this.fRadio.setToggleGroup(this.dayGroup);

        this.mRadio.setSelected(true);
        this.mRadio.requestFocus();

        this.sessionListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.selectedSession = newValue;

                    if (newValue != null) {
                        this.subjectField.setText(newValue.getSubject());
                        this.taskField.setText(newValue.getTask());
                        this.selectDayRadioButton(newValue.getDay());
                    } else {
                        this.subjectField.clear();
                        this.taskField.clear();
                        this.mRadio.setSelected(true);
                    }
                });
    }

    /**
     * Adds a new study session based on user input.
     * Validates subject input before adding.
     */
    @FXML
    public void addSession() {

        this.clearErrorMessages();

        String subject = this.subjectField.getText().trim();
        String task = this.taskField.getText().trim();
        String day = this.getSelectedDay();

        if (subject.isBlank()) {
            this.subjectErrorLabel.setText("required");
            return;
        }

        StudySession newSession = new StudySession(day, subject, task);

        this.sessionListView.getItems().add(newSession);
        this.sessionListView.getSelectionModel().select(newSession);
    }

    /**
     * Deletes the currently selected study session.
     * If no session is selected, displays a warning alert.
     */
    @FXML
    public void deleteSession() {
        if (this.selectedSession == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No selection");
            alert.setContentText("Please select a study session to delete.");
            alert.showAndWait();
            return;
        }

        this.sessionListView.getItems().remove(this.selectedSession);
        this.sessionListView.getSelectionModel().clearSelection();
        this.selectedSession = null;

        this.mRadio.setSelected(true);
        this.subjectField.clear();
        this.taskField.clear();

        this.clearErrorMessages();
    }

    /**
     * Returns the selected day from the radio buttons.
     *
     * @return the selected day code (M, T, W, R, or F)
     */
    private String getSelectedDay() {
        if (this.mRadio.isSelected()) return "M";
        if (this.tRadio.isSelected()) return "T";
        if (this.wRadio.isSelected()) return "W";
        if (this.rRadio.isSelected()) return "R";
        return "F";
    }

    /**
     * Selects the correct radio button based on a day value.
     *
     * @param day the day code
     */
    private void selectDayRadioButton(String day) {
        switch (day) {
            case "M":
                this.mRadio.setSelected(true);
                break;
            case "T":
                this.tRadio.setSelected(true);
                break;
            case "W":
                this.wRadio.setSelected(true);
                break;
            case "R":
                this.rRadio.setSelected(true);
                break;
            case "F":
                this.fRadio.setSelected(true);
                break;
            default:
                this.mRadio.setSelected(true);
        }
    }

    /**
     * Clears all error messages.
     */
    private void clearErrorMessages() {
        this.subjectErrorLabel.setText("");
    }
}