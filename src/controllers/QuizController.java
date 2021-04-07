package controllers;

import model.Quiz;
import repositories.QuizFileRepository;

import java.util.*;
import java.util.Collections;
import java.util.List;

public class QuizController {
    QuizFileRepository quizRepository;
    List<Quiz> shuffledList, skipped = new ArrayList<Quiz>();
    List<String> answers;
    int currentQuestion, failCounter;

    public List<Quiz> getShuffledList() {
        return shuffledList;
    }

    public void setShuffledList(List<Quiz> shuffledList) {
        this.shuffledList = shuffledList;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCurrentQuestion() { return currentQuestion; }

    public void setCurrentQuestion(int currentQuestion) { this.currentQuestion = currentQuestion; }

    public int getFailCounter() { return failCounter; }

    public void setFailCounter(int failCounter) { this.failCounter = failCounter; }

    /**
     * This method shuffles the questions (stored inside of the existing list)
     * then loads 26 of them into a new list
     * @return List<Quiz> This will be the new, shuffled list
     */
    public QuizController(QuizFileRepository quizRepository1) {
        this.quizRepository = quizRepository1;
        currentQuestion = 0;
        Collections.shuffle(quizRepository.getQuizRepository().getQuizList());
        shuffledList = new ArrayList<Quiz>();
        int steps = 26;
        for(Quiz i: quizRepository.getQuizRepository().getQuizList()){
            shuffledList.add(i);
            if (steps == 0){
                break;
            }
            steps--;
        }
    }

    /**
     * adds object to the skipped questions list
     * @param object
     */
    public void addToSkipped(Quiz object){
        this.skipped.add(object);
    }

    /**
     * loads the next question by incrementing the in-class index
     */
    public void nextQuestion(){
        if (currentQuestion < shuffledList.size())
            currentQuestion++;
    }

    /**
     * Method used when loading questions into buttons
     * @return returns the current question
     */
    public Quiz getQuestion(){
        if (currentQuestion >= shuffledList.size() - 1){
            Quiz temp = skipped.get(0);
            skipped.remove(temp);
            return temp;
        }
        return (shuffledList.get(currentQuestion));
    }

    /**
     * loads the answers from the pair
     * @return list of answers
     */
    public List<String> getAnswers() {
        answers = new ArrayList<>();
        answers.add(shuffledList.get(currentQuestion).getAnswerA().getKey());
        return answers;
    }

}
