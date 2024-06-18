/**
 * Diese Klasse repräsentiert eine Frage.
 * Eine Frage besteht aus einem Fragetext, einer richtigen Antwort und einer Liste von Antwortmöglichkeiten.
 */
public class Question {
    /**
     * Dies Attribut speichert den Fragetext.
     *
     * @see #getQuestion()
     * @see #setQuestion(String)
     */
    private String question;
    /**
     * Dies Attribut speichert die richtige Antwort.
     *
     * @see #getRightAnswer()
     * @see #setRightAnswer(String)
     */
    private String rightAnswer;
    /**
     * Dies Attribut speichert die Liste von Antwortmöglichkeiten.
     *
     * @see #getAnswerList()
     * @see #setAnswerList(String[])
     */
    private String[] answerList;

    /**
     * Dieser Konstruktor initialisiert die Frage mit den übergebenen Werten.
     *
     * @param question    Der Fragetext.
     * @param rightAnswer Die richtige Antwort.
     * @param answerList  Die Liste von Antwortmöglichkeiten.
     */
    public Question(String question , String rightAnswer , String[] answerList) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answerList = answerList;
    }

    /**
     * Diese Methode gibt den Fragetext zurück.
     *
     * @return Der Fragetext.
     * @see #question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Diese Methode setzt den Fragetext.
     *
     * @param question Der neue Fragetext.
     * @see #question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Diese Methode gibt die Liste von Antwortmöglichkeiten zurück.
     *
     * @return Die Liste von Antwortmöglichkeiten.
     * @see #answerList
     */
    public String[] getAnswerList() {
        return answerList;
    }

    /**
     * Diese Methode setzt die Liste von Antwortmöglichkeiten.
     *
     * @param answerList Die neue Liste von Antwortmöglichkeiten.
     * @see #answerList
     */
    public void setAnswerList(String[] answerList) {
        this.answerList = answerList;
    }

    /**
     * Diese Methode gibt die richtige Antwort zurück.
     *
     * @return Die richtige Antwort.
     * @see #rightAnswer
     */
    public String getRightAnswer() {
        return rightAnswer;
    }

    /**
     * Diese Methode setzt die richtige Antwort.
     *
     * @param rightAnswer Die neue richtige Antwort.
     * @see #rightAnswer
     */
    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
