package ru.otus.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.domain.QuizResult;
import ru.otus.service.OutputService;

@Component
@PropertySource("application.properties")
public class SimpleQuizResultPrinter implements QuizResultPrinter {

    private final OutputService printService;

    private final int countOfAnswersForPassQuiz;

    public SimpleQuizResultPrinter(OutputService printService,
                                   @Value("${countOfAnswersForPassQuiz}") int countOfAnswersForPassQuiz) {
        this.printService = printService;
        this.countOfAnswersForPassQuiz = countOfAnswersForPassQuiz;
    }

    public void printResult(QuizResult result) {
        printService.outputString("Quiz result:");
        printService.outputFormatString("%s %s", result.getRespondent().getName(), result.getRespondent().getSurname());
        String quizPassedText = isQuizPassed(result.getCountOfTrueAnswers()) ?
                "\nCorrect answers: %s. Congratulation! Quiz passed!" :
                "\nCorrect answers: %s. Quiz failed.";
        printService.outputFormatString(quizPassedText, result.getCountOfTrueAnswers());
    }

    private boolean isQuizPassed(int countOfTrueAnswers) {
        return countOfTrueAnswers >= countOfAnswersForPassQuiz;
    }
}
