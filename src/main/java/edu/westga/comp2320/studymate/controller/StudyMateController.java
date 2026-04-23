package edu.westga.comp2320.studymate.controller;

import edu.westga.comp2320.studymate.model.StudySession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * Controller for the StudyMate GUI.
 * Handles adding, deleting, selecting, and validating study sessions.
 */
public class StudyMateController {


    @FXML
    private CheckBox englCheck;


    @FXML
    private CheckBox histCheck;


    @FXML
    private CheckBox mathCheck;


    @FXML
    private CheckBox compCheck;

    /**
     * Error label shown when no subject checkbox is selected.
     */
    @FXML
    private Label subjectSelectionErrorLabel;


    @FXML
    private TextField taskField;

    /**
     * ListView that displays study sessions.
     */
    @FXML
    private ListView<StudySession> sessionListView;


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

    /**
     * The currently selected study session.
     */
    private StudySession selectedSession;

    /**
     * Initializes the controller by setting up the radio buttons,
     * clearing error messages, and wiring the ListView selection listener.
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

        this.clearErrorMessages();

        this.sessionListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.selectedSession = newValue;

                    if (newValue != null) {
                        this.taskField.setText(newValue.getTask());
                        this.selectDayRadioButton(newValue.getDay());
                        this.selectSubjectCheckBox(newValue.getSubject());
                    } else {
                        this.taskField.clear();
                        this.mRadio.setSelected(true);
                        this.clearSubjectCheckBoxes();
                    }
                });
    }

    /**
     * Adds a new study session for each selected subject checkbox.
     * If no subject is selected, an error message is shown.
     */
    @FXML
    public void addSession() {
        this.clearErrorMessages();

        String task = this.taskField.getText().trim();
        String day = this.getSelectedDay();

        boolean hasSelection = this.englCheck.isSelected()
                || this.histCheck.isSelected()
                || this.mathCheck.isSelected()
                || this.compCheck.isSelected();

        if (!hasSelection) {
            this.subjectSelectionErrorLabel.setText("select at least one subject");
            return;
        }

        StudySession firstAddedSession = null;

        if (this.englCheck.isSelected()) {
            StudySession newSession = new StudySession(day, "ENGL", task);
            this.sessionListView.getItems().add(newSession);
            if (firstAddedSession == null) {
                firstAddedSession = newSession;
            }
        }

        if (this.histCheck.isSelected()) {
            StudySession newSession = new StudySession(day, "HIST", task);
            this.sessionListView.getItems().add(newSession);
            if (firstAddedSession == null) {
                firstAddedSession = newSession;
            }
        }

        if (this.mathCheck.isSelected()) {
            StudySession newSession = new StudySession(day, "MATH", task);
            this.sessionListView.getItems().add(newSession);
            if (firstAddedSession == null) {
                firstAddedSession = newSession;
            }
        }

        if (this.compCheck.isSelected()) {
            StudySession newSession = new StudySession(day, "COMP", task);
            this.sessionListView.getItems().add(newSession);
            if (firstAddedSession == null) {
                firstAddedSession = newSession;
            }
        }

        this.sessionListView.getSelectionModel().select(firstAddedSession);
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
        this.taskField.clear();
        this.clearSubjectCheckBoxes();
        this.clearErrorMessages();
    }

    /**
     * Returns the selected day from the radio buttons.
     *
     * @return the selected day code
     */
    private String getSelectedDay() {
        if (this.mRadio.isSelected()) {
            return "M";
        }
        if (this.tRadio.isSelected()) {
            return "T";
        }
        if (this.wRadio.isSelected()) {
            return "W";
        }
        if (this.rRadio.isSelected()) {
            return "R";
        }
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
                break;
        }
    }

    /**
     * Selects the checkbox matching the subject of the selected study session.
     *
     * @param subject the subject to select
     */
    private void selectSubjectCheckBox(String subject) {
        this.clearSubjectCheckBoxes();

        switch (subject) {
            case "ENGL":
                this.englCheck.setSelected(true);
                break;
            case "HIST":
                this.histCheck.setSelected(true);
                break;
            case "MATH":
                this.mathCheck.setSelected(true);
                break;
            case "COMP":
                this.compCheck.setSelected(true);
                break;
            default:
                break;
        }
    }

    /**
     * Clears all subject checkboxes.
     */
    private void clearSubjectCheckBoxes() {
        this.englCheck.setSelected(false);
        this.histCheck.setSelected(false);
        this.mathCheck.setSelected(false);
        this.compCheck.setSelected(false);
    }

    /**
     * Clears all error messages.
     */
    private void clearErrorMessages() {
        this.subjectSelectionErrorLabel.setText("");
    }
}