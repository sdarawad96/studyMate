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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the StudyMate GUI.
 * Handles adding, deleting, selecting, and saving study sessions.
 */
public class StudyMateController {

    /** Checkbox for ENGL. */
    @FXML
    private CheckBox englCheck;

    /** Checkbox for HIST. */
    @FXML
    private CheckBox histCheck;

    /** Checkbox for MATH. */
    @FXML
    private CheckBox mathCheck;

    /** Checkbox for COMP. */
    @FXML
    private CheckBox compCheck;

    /** Error label shown when no subject checkbox is selected. */
    @FXML
    private Label subjectSelectionErrorLabel;

    /** Text field for the task. */
    @FXML
    private TextField taskField;

    /** ListView that displays study sessions. */
    @FXML
    private ListView<StudySession> sessionListView;

    /** Radio button for Monday. */
    @FXML
    private RadioButton mRadio;

    /** Radio button for Tuesday. */
    @FXML
    private RadioButton tRadio;

    /** Radio button for Wednesday. */
    @FXML
    private RadioButton wRadio;

    /** Radio button for Thursday. */
    @FXML
    private RadioButton rRadio;

    /** Radio button for Friday. */
    @FXML
    private RadioButton fRadio;

    /** Group that ensures only one day radio button can be selected. */
    private ToggleGroup dayGroup;

    /** The currently selected study session. */
    private StudySession selectedSession;

    /**
     * Initializes the controller.
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
                (observable, oldValue, newValue) -> this.handleSessionSelection(newValue));
    }

    /**
     * Adds a study session for each selected subject checkbox.
     */
    @FXML
    public void addSession() {
        this.clearErrorMessages();

        String task = this.taskField.getText().trim();
        String day = this.getSelectedDay();
        List<String> selectedSubjects = this.getSelectedSubjects();

        if (selectedSubjects.isEmpty()) {
            this.subjectSelectionErrorLabel.setText("select at least one subject");
            return;
        }

        StudySession firstAddedSession = null;

        for (String subject : selectedSubjects) {
            StudySession newSession = new StudySession(day, subject, task);
            this.sessionListView.getItems().add(newSession);

            if (firstAddedSession == null) {
                firstAddedSession = newSession;
            }
        }

        this.sessionListView.getSelectionModel().select(firstAddedSession);
    }

    /**
     * Deletes the currently selected study session.
     */
    @FXML
    public void deleteSession() {
        StudySession sessionToDelete = this.sessionListView.getSelectionModel().getSelectedItem();

        if (sessionToDelete == null) {
            this.showNoSelectionAlert();
            return;
        }

        this.sessionListView.getItems().remove(sessionToDelete);
        this.sessionListView.getSelectionModel().clearSelection();
        this.selectedSession = null;

        this.mRadio.setSelected(true);
        this.taskField.clear();
        this.clearSubjectCheckBoxes();
        this.clearErrorMessages();
    }

    /**
     * Saves all study sessions to a file grouped by day.
     */
    @FXML
    public void saveSessions() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Study Sessions");

        File file = fileChooser.showSaveDialog(this.sessionListView.getScene().getWindow());

        if (file == null) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(file)) {
            Map<String, List<StudySession>> groupedSessions = this.groupSessionsByDay();

            for (String day : groupedSessions.keySet()) {
                List<StudySession> sessions = groupedSessions.get(day);

                if (!sessions.isEmpty()) {
                    writer.println(this.getFullDayName(day));

                    for (StudySession session : sessions) {
                        writer.println(session.getSubject() + " - " + session.getTask());
                    }

                    writer.println();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Updates the form when the selected session changes.
     *
     * @param selectedSession the selected study session
     */
    private void handleSessionSelection(StudySession selectedSession) {
        this.selectedSession = selectedSession;

        if (selectedSession != null) {
            this.taskField.setText(selectedSession.getTask());
            this.selectDayRadioButton(selectedSession.getDay());
            this.selectSubjectCheckBox(selectedSession.getSubject());
        } else {
            this.taskField.clear();
            this.mRadio.setSelected(true);
            this.clearSubjectCheckBoxes();
        }
    }

    /**
     * Gets the selected subjects.
     *
     * @return list of selected subjects
     */
    private List<String> getSelectedSubjects() {
        List<String> selectedSubjects = new ArrayList<>();

        if (this.englCheck.isSelected()) {
            selectedSubjects.add("ENGL");
        }
        if (this.histCheck.isSelected()) {
            selectedSubjects.add("HIST");
        }
        if (this.mathCheck.isSelected()) {
            selectedSubjects.add("MATH");
        }
        if (this.compCheck.isSelected()) {
            selectedSubjects.add("COMP");
        }

        return selectedSubjects;
    }

    /**
     * Groups study sessions by day.
     *
     * @return map of day codes to study sessions
     */
    private Map<String, List<StudySession>> groupSessionsByDay() {
        Map<String, List<StudySession>> groupedSessions = new LinkedHashMap<>();
        groupedSessions.put("M", new ArrayList<>());
        groupedSessions.put("T", new ArrayList<>());
        groupedSessions.put("W", new ArrayList<>());
        groupedSessions.put("R", new ArrayList<>());
        groupedSessions.put("F", new ArrayList<>());

        for (StudySession session : this.sessionListView.getItems()) {
            groupedSessions.get(session.getDay()).add(session);
        }

        return groupedSessions;
    }

    /**
     * Returns the selected day from the radio buttons.
     *
     * @return selected day code
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
     * Converts a day code to a full day name.
     *
     * @param day day code
     * @return full day name
     */
    private String getFullDayName(String day) {
        switch (day) {
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
                return "";
        }
    }

    /**
     * Selects the correct radio button for a day.
     *
     * @param day day code
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
     * Selects the checkbox matching a subject.
     *
     * @param subject subject to select
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

    /**
     * Shows a warning when no session is selected.
     */
    private void showNoSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No selection");
        alert.setContentText("Please select a study session to delete.");
        alert.showAndWait();
    }
}