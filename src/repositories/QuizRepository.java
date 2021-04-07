package repositories;

import model.Quiz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizRepository{

    List<Quiz> quizList;

    public List<Quiz> getQuizList() { return quizList; }

    public void setQuizList(List<Quiz> quizList) { this.quizList = quizList; }

    public QuizRepository(List<Quiz> quizList) { this.quizList = quizList; }

    /**
     * add a question
     * @param entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    public Quiz add(Quiz entity) {
        for (Quiz i : quizList)
        {
            if (i.getQuestion() == entity.getQuestion())
                return entity;
        }
        quizList.add(entity);
        return null;
    }

    /**
     * find a question by id
     * @param id valid id
     * @return element with the given id
     */
    public Quiz get(int id) {
        if(quizList.size()>id)
            return quizList.get(id);
        return null;
    }


    /**
     * This method shuffles the questions (stored inside of the existing list)
     * then loads 26 of them into a new list
     * @return List<Quiz> This will be the new, shuffled list
     */
    public List<Quiz> createShuffledList() {
        Collections.shuffle(quizList);
        List<Quiz> shuffled = new ArrayList<Quiz>();
        int steps = 26;
        for(Quiz i:quizList){
            shuffled.add(i);
            steps--;
            if (steps == 0){
                break;
            }
        }
        return shuffled;
    }
}
