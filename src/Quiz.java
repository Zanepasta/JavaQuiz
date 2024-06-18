import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The Quiz-Klasse ist verantwortlich für das Verwalten des Quizspiels.
 * Sie lädt die Fragen aus einer JSON-Datei, mischt sie und zeigt sie im QuizFrame an.
 * Außerdem verwaltet sie den Punktestand, den Joker und den Fragen index.
 */
public class Quiz {
    /**
     * Dieses Attribut speichert die Liste der Fragen-Objekte, die im Quiz verwendet werden.
     */
    private static final ArrayList<Question> questionList = new ArrayList<>();
    /**
     * Dieses Attribut speichert das QuizFrame-Objekt, das für die Anzeige des Quiz verwendet wird.
     */
    public static QuizFrame quizFrame;
    /**
     * Dieses Attribut speichert den Index der aktuellen Frage.
     */
    private static int questionIndex = 0;
    /**
     * Dieses Attribut speichert den Joker, der im Quiz verwendet werden kann.
     */
    private boolean joker = true;
    /**
     * Dieses Attribut speichert den Punktestand des Spielers.
     */
    private int score = 0;

    /**
     * Der Konstruktor initialisiert das Quiz-Objekt.
     *
     * @param gameMode Der Spielmodus des Quiz. ["normal", "manuel"]
     */
    public Quiz(String gameMode) {
        questionIndex = 0;
        score = 0;
    }

    /**
     * Diese Methode startet das Quiz, indem sie die Fragen aus der JSON-Datei lädt, sie mischt und die erste Frage anzeigt.
     */
    public void start() {
        loadQuestionsFromJson();
        shuffleQuestions();
        joker = true;
        quizFrame = new QuizFrame(Main.getGameMode());
        nextQuestion();
    }
    public void init(){
        loadQuestionsFromJson();
    }

    /**
     * Diese Methode erhöht den Punktestand des Spielers um 1.
     */
    public void addScore() {
        score++;
    }

    /**
     * Diese Methode gibt zurück, ob der Joker verwendet werden kann.
     *
     * @return true , wenn der Joker verwendet werden kann, sonst false.
     */
    public boolean getJoker() {
        return joker;
    }

    /**
     * Diese Methode setzt den Joker.
     *
     * @param joker true, wenn der Joker verwendet werden kann, sonst false.
     */
    public void setJoker(boolean joker) {
        this.joker = joker;
    }

    /**
     * Diese Methode zeigt die nächste Frage im Quiz an.
     */
    public void nextQuestion() {
        if (questionIndex < 10) {
            quizFrame.setQuestion(questionList.get(questionIndex) , questionIndex);
        } else {
            finish();
        }
        questionIndex++;
    }

    /**
     * Diese Methode beendet das Quiz und zeigt den Highscore an.
     */
    public void finish() {
        Highscore highscore = new Highscore();
        highscore.loadHighscoreFromJson();
        if (JOptionPane.showConfirmDialog(null , "Das Quiz ist zu Ende\nSie haben " + score + " von 10 Fragen richtig beantwortet.\nMöchten Sie ihren Highscore speichern?" , "JavaQuiz - Ende" , JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String name = JOptionPane.showInputDialog(null , "Geben Sie bitte Ihren Namen ein" , "Beenden" , JOptionPane.INFORMATION_MESSAGE);
            name = name.trim();
            if (name.isEmpty()) {
                name = "Anonymus";
            }
            Main.highscore.addHighscoreItem(new HighscoreItem(name , score));
            Main.highscore.saveHighscoreToJson();
        }
        quizFrame.dispose();
        Main.showMenu();
    }

    /**
     * Diese Methode mischt die Fragen und die Antwortmöglichkeiten.
     */
    private void shuffleQuestions() {
        for (int i = 0; i < questionList.size(); i++) {
            int randomIndex = (int) (Math.random() * questionList.size());
            Question temp = questionList.get(i);
            questionList.set(i , questionList.get(randomIndex));
            questionList.set(randomIndex , temp);
        }
        for (Question question : questionList) {
            for (int i = 0; i < question.getAnswerList().length; i++) {
                int randomIndex = (int) (Math.random() * question.getAnswerList().length);
                String temp = question.getAnswerList()[i];
                question.getAnswerList()[i] = question.getAnswerList()[randomIndex];
                question.getAnswerList()[randomIndex] = temp;
            }
        }
    }

    /**
     * Diese Methode lädt die Fragen aus der JSON-Datei.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private void loadQuestionsFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("JavaQuizData/questions.json")));
            JSONArray questionsJsonArray = new JSONArray(content);

            for (int i = 0; i < questionsJsonArray.length(); i++) {
                JSONObject jsonObject = questionsJsonArray.getJSONObject(i);
                String question = jsonObject.getString("question");
                String rightAnswer = jsonObject.getString("rightAnswer");
                JSONArray anwersJsonArray = jsonObject.getJSONArray("answerList");
                String[] answerList = new String[anwersJsonArray.length()];
                for (int j = 0; j < anwersJsonArray.length(); j++) {
                    answerList[j] = anwersJsonArray.getString(j);
                }
                questionList.add(new Question(question , rightAnswer , answerList));
            }
        } catch (IOException e) {
            File dir = new File("JavaQuizData");
            if (!dir.exists()) {
                if(!dir.mkdir()) {
                    JOptionPane.showMessageDialog(null , "Fehler beim Erstellen des JavaQuizData-Ordners" , "Fehler" , JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            } try (FileWriter file = new FileWriter("JavaQuizData/questions.json")) {
                file.write("[{\"answerList\":[\"7\",\"8\",\"9\",\"10\"],\"rightAnswer\":\"8\",\"question\":\"Wie viele Planeten hat unser Sonnensystem?\"},{\"answerList\":[\"5\",\"6\",\"7\",\"8\"],\"rightAnswer\":\"7\",\"question\":\"Wie viele Kontinente gibt es?\"},{\"answerList\":[\"14\",\"15\",\"16\",\"17\"],\"rightAnswer\":\"16\",\"question\":\"Wie viele Bundesländer hat Deutschland?\"},{\"answerList\":[\"204\",\"206\",\"208\",\"210\"],\"rightAnswer\":\"206\",\"question\":\"Wie viele Knochen hat ein erwachsener Mensch?\"},{\"answerList\":[\"3600\",\"3700\",\"3800\",\"3900\"],\"rightAnswer\":\"3600\",\"question\":\"Wie viele Sekunden hat eine Stunde?\"},{\"answerList\":[\"Marianengraben\",\"Sunda-Graben\",\"Puerto-Rico-Graben\",\"Tongagraben\"],\"rightAnswer\":\"Marianengraben\",\"question\":\"Was ist der tiefste Ozeangraben?\"},{\"answerList\":[\"Johann Sebastian Bach\",\"Ludwig van Beethoven\",\"Wolfgang Amadeus Mozart\",\"Franz Schubert\"],\"rightAnswer\":\"Ludwig van Beethoven\",\"question\":\"Wer komponierte die \\u201cMondscheinsonate\\u201d?\"},{\"answerList\":[\"Grönland\",\"Neuguinea\",\"Borneo\",\"Madagaskar\"],\"rightAnswer\":\"Grönland\",\"question\":\"Welche Insel ist die größte der Welt?\"},{\"answerList\":[\"S\",\"O\",\"A\",\"U\",\"H\"],\"rightAnswer\":\"O\",\"question\":\"Was ist das chemische Symbol für Sauerstoff?\"},{\"answerList\":[\"Leonardo da Vinci\",\"Michelangelo\",\"Raffael\",\"Donatello\"],\"rightAnswer\":\"Michelangelo\",\"question\":\"Wer malte die Decke der Sixtinischen Kapelle?\"},{\"answerList\":[\"London\",\"New York\",\"Paris\",\"Berlin\"],\"rightAnswer\":\"Paris\",\"question\":\"Wo steht der Eiffelturm?\"},{\"answerList\":[\"Beethoven\",\"Mozart\",\"Bach\",\"Schubert\"],\"rightAnswer\":\"Mozart\",\"question\":\"Wer komponierte die Zauberflöte?\"},{\"answerList\":[\"C++\",\"Java\",\"Python\",\"JavaScript\"],\"rightAnswer\":\"Python\",\"question\":\"Welche Programmiersprache ist besonders beliebt für Datenanalyse?\"},{\"answerList\":[\"New York\",\"Beijing\",\"Tokyo\",\"Mumbai\"],\"rightAnswer\":\"Tokyo\",\"question\":\"Welche Stadt hat die größte Bevölkerung?\"},{\"answerList\":[\"Mars\",\"Venus\",\"Erde\",\"Merkur\"],\"rightAnswer\":\"Venus\",\"question\":\"Welcher Planet ist der zweite von der Sonne aus gesehen?\"},{\"answerList\":[\"Newton\",\"Einstein\",\"Galileo\",\"Copernicus\"],\"rightAnswer\":\"Einstein\",\"question\":\"Wer formulierte die Relativitätstheorie?\"},{\"answerList\":[\"Dickens\",\"Shakespeare\",\"Austen\",\"Tolstoy\"],\"rightAnswer\":\"Shakespeare\",\"question\":\"Wer schrieb Romeo und Julia?\"},{\"answerList\":[\"Englisch\",\"Spanisch\",\"Mandarin\",\"Hindi\"],\"rightAnswer\":\"Mandarin\",\"question\":\"Welche Sprache hat die meisten Muttersprachler weltweit?\"},{\"answerList\":[\"Ricky Ponting\",\"Sachin Tendulkar\",\"Brian Lara\",\"Steve Smith\"],\"rightAnswer\":\"Sachin Tendulkar\",\"question\":\"Wer hat die meisten Läufe im internationalen Cricket erzielt?\"},{\"answerList\":[\"Buzz Aldrin\",\"Neil Armstrong\",\"Yuri Gagarin\",\"Alan Shepard\"],\"rightAnswer\":\"Neil Armstrong\",\"question\":\"Wer war der erste Mensch auf dem Mond?\"},{\"answerList\":[\"1914\",\"1917\",\"1918\",\"1920\"],\"rightAnswer\":\"1914\",\"question\":\"Welches Jahr markiert den Beginn des Ersten Weltkriegs?\"}]");
            } catch (Exception e2) {
                e2.printStackTrace();
                JOptionPane.showMessageDialog(null , "Fehler beim Erstellen der Fragen Datei" , "Fehler" , JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }

    /**
     * Diese Methode speichert die Fragen in die JSON-Datei.
     */
    @SuppressWarnings({"CallToPrintStackTrace" , "ResultOfMethodCallIgnored"})
    private void saveQuestionsToJson() {
        JSONArray questionsJsonArray = new JSONArray();
        for (Question question : questionList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("question" , question.getQuestion());
            jsonObject.put("rightAnswer" , question.getRightAnswer());
            JSONArray anwersJsonArray = new JSONArray();
            for (String answer : question.getAnswerList()) {
                anwersJsonArray.put(answer);
            }
            jsonObject.put("answerList" , anwersJsonArray);
            questionsJsonArray.put(jsonObject);
        }
        File oldFile = new File("JavaQuizData/questions.json");
        oldFile.delete();
        try (FileWriter file = new FileWriter("JavaQuizData/questions.json")) {
            file.write(questionsJsonArray.toString());
            file.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null , "Fehler beim Speichern der Fragen" , "Fehler" , JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System .exit(1);
        }
    }

    /**
     * Diese Methode fügt eine Frage hinzu.
     *
     * @param question Die Frage, die hinzugefügt werden soll.
     */
    public void addQuestion(Question question) {
        loadQuestionsFromJson();
        questionList.add(question);
        saveQuestionsToJson();
    }
}

