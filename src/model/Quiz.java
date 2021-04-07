package model;

import javafx.util.Pair;


public class Quiz {
    String question;
    Pair<String,String> answerA;
    Pair<String,String> answerB;
    Pair<String,String> answerC;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Pair<String, String> getAnswerA() {
        return answerA;
    }

    public void setAnswerA(Pair<String, String> answerA) {
        this.answerA = answerA;
    }

    public Pair<String, String> getAnswerB() {
        return answerB;
    }

    public void setAnswerB(Pair<String, String> answerB) {
        this.answerB = answerB;
    }

    public Pair<String, String> getAnswerC() {
        return answerC;
    }

    public void setAnswerC(Pair<String, String> answerC) {
        this.answerC = answerC;
    }

    public Quiz(String question, Pair<String, String> answerA, Pair<String, String> answerB, Pair<String, String> answerC) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "question='" + question + '\'' +
                ", answerA=" + answerA +
                ", answerB=" + answerB +
                ", answerC=" + answerC +
                '}';
    }
}
