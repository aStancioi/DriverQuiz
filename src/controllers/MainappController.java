package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Quiz;
import repositories.QuizFileRepository;
import repositories.QuizRepository;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainappController implements Initializable {

    @FXML
    private CheckBox checkBox1;

    @FXML
    private CheckBox checkBox2;

    @FXML
    private CheckBox checkBox3;

    @FXML
    private Text question, failDisplay, timeDisplay;

    private QuizController quizController;
    private List<String> answers;
    private Quiz current;
    Timer myTimer;
    TimerTask myTimerTask;
    int secondsPassed, answered;

    private int failCounter, questionNumber;

    public void setCheckBox1(String text) {
        this.checkBox1.setText(text);
    }

    public void setCheckBox2(String text) {
        this.checkBox2.setText(text);
    }

    public void setCheckBox3(String text) {
        this.checkBox3.setText(text);
    }


    /**
     * questionNumber setter
     * @param number number of questions
     */
    public void setQuestionNumber(int number){
        this.questionNumber = number;
    }

    /**
     * failCounter setter
     * @param number number of fails
     */
    public void setFailCounter(int number) {
        this.failCounter = number;
    }

    /**
     * This method is used to change/update the fields on whenever a question is submitted or skipped
     */
    public void setFields(){

        quizController.nextQuestion();

        System.out.println(failCounter);

        this.current = quizController.getQuestion();

        System.out.println(current.toString());

        answers = new ArrayList<String>();
        answers.add(this.current.getAnswerA().getKey());
        answers.add(this.current.getAnswerB().getKey());
        answers.add(this.current.getAnswerC().getKey());

        System.out.println(answers);

        checkBox1.setText(this.current.getAnswerA().getValue());
        checkBox2.setText(this.current.getAnswerB().getValue());
        checkBox3.setText(this.current.getAnswerC().getValue());

        if(quizController.getCurrentQuestion() + 1 > 26)
            question.setText(this.current.getQuestion());
        else
            question.setText(quizController.getCurrentQuestion() + 1 + ". " + this.current.getQuestion());


        failDisplay.setText(String.valueOf(failCounter));
    }


    /**
     * Method used for closing the app when the exit button is clicked
     * @return null
     */
    public EventHandler<ActionEvent> closeApp()
    {
        Stage appStage = (Stage) failDisplay.getScene().getWindow();
        appStage.close();
        return null;
    }

    /**
     * Method used to display the alert box in case of a failure
     * (used when the timer runs out or when the maximum number of fails has been reached)
     */
    public void failAlert(){
        Alert result = new Alert(Alert.AlertType.ERROR);
        ButtonType button1 = new ButtonType("Restart");
        ButtonType button2 = new ButtonType("Iesire");
        result.setTitle("Rezultat");
        result.setHeaderText("Din pacate, respins.");
        result.setContentText("Incercati din nou?");
        result.getButtonTypes().setAll(button1,button2);
        Optional<ButtonType> temp = result.showAndWait();
        if(temp.get() == button1){
            try {
                restartApp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (temp.get() == button2){
            System.exit(0);
        }
    }


    /**
     * Method used for the "Next" button, if enough questions have been answered or failed
     * the end screen will be displayed
     * @throws IOException
     */

    public void goToNext(MouseEvent mouseEvent) throws IOException {

        if ((checkBox1.isSelected() && answers.get(0).equals("F")) || (checkBox2.isSelected() && answers.get(1).equals("F")) || (checkBox3.isSelected() && answers.get(2).equals("F"))) {
            failCounter++;
        }
        else
        if ((answers.get(0).equals("T") && !checkBox1.isSelected()) || (answers.get(1).equals("T") && !checkBox2.isSelected()) || (answers.get(2).equals("T") && !checkBox3.isSelected())) {
            failCounter++;
        }
        else
            answered++;

        if (failCounter > 4) {
            myTimer.cancel();
            failAlert();

        } else {
            if (answered > 21) {
                myTimer.cancel();
                Alert result = new Alert(Alert.AlertType.INFORMATION);
                ButtonType button1 = new ButtonType("Restart");
                ButtonType button2 = new ButtonType("Iesire");
                result.setTitle("Rezultat");
                result.setHeaderText("Felicitari, admis!");
                result.getButtonTypes().setAll(button1,button2);
                Optional<ButtonType> temp = result.showAndWait();
                if(temp.get() == button1){
                    restartApp();
                }else if (temp.get() == button2){
                    System.exit(0);
                }
            }
            else{
                System.out.println(answered);
                setQuestionNumber(questionNumber + 1);
                setFields();
            }
        }
    }

    /**
     * Whenever a question is skipped, it will be added to the end of the list
     * @param mouseEvent
     */
    public void skipQuestion(MouseEvent mouseEvent) {

        Pair<String,String> pair;
        pair = new Pair<>(answers.get(0),checkBox1.getText());
        Quiz toSkip = new Quiz("temp",pair,pair,pair);

        pair = new Pair<>(answers.get(1),checkBox2.getText());
        toSkip.setAnswerB(pair);

        pair = new Pair<>(answers.get(2),checkBox3.getText());
        toSkip.setAnswerC(pair);

        toSkip.setQuestion(question.getText());

        System.out.println(toSkip);

        quizController.addToSkipped(toSkip);

        setFields();
    }

    /**
     * Method used by the "restart" button, when the button is clicked the start screen will be displayed
     * and the quiz can be taken again
     * @throws IOException
     */
    public void restartApp() throws IOException {
        failCounter = 0;
        questionNumber = 0;
        Stage stage = (Stage) checkBox1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/resources/startscreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizController = new QuizController(new QuizFileRepository(new QuizRepository(new ArrayList<Quiz>())));
        current = quizController.getQuestion();
        secondsPassed = 0;
        failCounter = 0;
        answered = 0;
        question.setText( quizController.getCurrentQuestion() + 1 + ". " + current.getQuestion());
        checkBox1.setText(current.getAnswerA().getValue());
        checkBox2.setText(current.getAnswerB().getValue());
        checkBox3.setText(current.getAnswerC().getValue());
        answers = new ArrayList<String>();
        answers.add(current.getAnswerA().getKey());
        answers.add(current.getAnswerB().getKey());
        answers.add(current.getAnswerC().getKey());
        failDisplay.setText("0");
        myTimerTask = new TimerTask() {
            int i = 0;
            @Override
            public void run()
            {
                i++;
                secondsPassed++;
                if (secondsPassed == 30 * 60)
                {
                    myTimer.cancel();
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            failAlert();
                        }});
                }
                int minutes, seconds;
                minutes = (1800 - secondsPassed) / 60;
                seconds = (1800 - secondsPassed) % 60;
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        timeDisplay.setText(minutes + ":" + seconds);
                    }});
            }
        };
        myTimer = new Timer();
        myTimer.schedule(myTimerTask, 0, 1000);
    }
}
