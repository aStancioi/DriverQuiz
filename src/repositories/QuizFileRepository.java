package repositories;

import javafx.util.Pair;
import model.Quiz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class QuizFileRepository {
    QuizRepository quizRepository;

    public QuizRepository getQuizRepository() { return quizRepository; }

    public void setQuizRepository(QuizRepository quizRepository) { this.quizRepository = quizRepository; }

    /**
     * Constructor, also reads from file
     * This method loads all the questions from the file into a list
     * @param current - FragenRepository
     * @return nothing
     * @exception IOException on invalid questions
     * @see IOException
     */
    public QuizFileRepository(QuizRepository current) {
        try {
            quizRepository = current;
            List<String> questionLines = Files.readAllLines(Paths.get("F:/JavaProjects/DriverQuiz/src/sample/Questions"));
            String[] lineList;
            Pair<String,String> answer1,answer2, answer3;
            for (String line : questionLines) {
                lineList = line.split(";");
                answer1 = new Pair<>(lineList[4],lineList[1]);
                answer2 = new Pair<>(lineList[5],lineList[2]);
                answer3 = new Pair<>(lineList[6],lineList[3]);

                Quiz temp = new Quiz(lineList[0],answer1,answer2,answer3);

                quizRepository.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * find a question by id
     * @param id valid id
     * @return element with the given id
     */
    public Quiz getFromList(int id)
    {
        return quizRepository.get(id);
    }
}
