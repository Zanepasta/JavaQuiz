import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class QuizFrame {
    /**
     * Diese Konstante gibt die Zeit für eine Frage an.
     */
    private static final int QUESTION_TIME = 600; // 30 sec (30 sec * 20 Ticks pro Sekunde)
    /**
     * Diese Konstante gibt die Zeit für das Quiz an.
     */
    private static final int QUIZ_TIME = 720; // 3 min (180 sec * 4 Ticks pro Sekunde)
    /**
     * Dieses Attribut speichert das JFrame-Objekt.
     */
    private final JFrame frame;
    /**
     * Dieses Attribut speichert das JLabel-Objekt für die Frage.
     */
    private final JLabel questionL;
    /**
     * Dieses Attribut speichert den "Next"-Button. Dieser Button wird verwendet, um zur nächsten Frage zu gelangen oder die Antwort zu überprüfen.
     */
    private final JButton nextBtn;
    /**
     * Dieses Attribut speichert den "Joker"-Button. Dieser Button wird verwendet, um die richtige Antwort anzuzeigen. Der Joker kann nur einmal pro Spiel verwendet werden.
     */
    private final JButton jokerBtn;
    /**
     * Dieses Attribut speichert die JProgressBar für die verbleibende Zeit der Frage.
     */
    private final JProgressBar questionTimePb;
    /**
     * Dieses Attribut speichert die JProgressBar für die verbleibende Zeit des Quiz.
     */
    private final JProgressBar quizTimePb;
    /**
     * Dieses Attribut speichert den Spielmodus. ["normal", "manuel"]
     */
    private final String gameMode;

    /**
     * Dieses Attribut speichert das JTextField-Objekt, in das die Antwort für den manuellen Spielmodus eingetragen werden soll.
     */
    private JTextField answerTf;
    /**
     * Dieses Attribut speichert das JLabel-Objekt für die Antwort im manuellen Spielmodus. Hier wird die richtige Antwort oder der Joker angezeigt. Das Attribut wird nur im manuellen Spielmodus verwendet.
     */
    private JLabel answerL;
    /**
     * Diese Liste speichert JButton-Objekte für die Antwortmöglichkeiten. Diese Liste wird nur im normalen Spielmodus verwendet.
     */
    private ArrayList<JButton> answerBtnList;
    /**
     * Dieses Attribut speichert die aktuelle Frage.
     */
    private Question question;
    /**
     * Dieses Attribut speichert die ausgewählte Antwort. -1 bedeutet, dass keine Antwort ausgewählt wurde. Dieses Attribut wird nur im normalen Spielmodus verwendet.
     */
    private int selectedAnswer = -1;
    /**
     * Dieses Attribut speichert, ob die Antwort überprüft wurde. Dieses Attribut wird nur im normalen Spielmodus verwendet.
     */
    private boolean answerChecked = false;
    /**
     * Dieses Attribut speichert den Timer für die Frage.
     */
    private Timer questionTimer;
    /**
     * Dieses Attribut speichert den Timer für das Quiz.
     */
    private Timer quizTimer;
    /**
     * Dieses Attribut speichert die verbleibende Zeit der Frage.
     */
    private int questionTimeLeft; // 20 = 1 sec stimmt aber eigentlich nicht
    /**
     * Dieses Attribut speichert die verbleibende Zeit des Quiz.
     */
    private int quizTimeLeft; // 1 = 1 sec

    /**
     * Dieser Konstruktor erstellt ein neues QuizFrame-Objekt.
     *
     * @param gameMode Dieser Parameter gibt den Spielmodus an. ["normal", "manuel"]
     */
    public QuizFrame(String gameMode) {
        this.gameMode = gameMode;
        //Erstellt das Fenster
        frame = new JFrame("JavaQuiz - Quiz");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        questionL = new JLabel("Frage");
        questionL.setFont(new Font("Arial" , Font.BOLD , 20));
        questionL.setHorizontalAlignment(JLabel.CENTER);
        questionL.setBounds(10 , 0 , 640 , 100);

        questionTimePb = new JProgressBar();
        questionTimePb.setBounds(75 , 110 , 510 , 10);
        questionTimePb.setMaximum(QUESTION_TIME); //30 sec (30sec x 20 Ticks pro Sekunde)

        quizTimePb = new JProgressBar();
        quizTimePb.setBounds(0 , 0 , 643 , 5);
        quizTimePb.setMaximum(QUIZ_TIME);
        quizTimePb.setBorder(null);
        quizTimePb.setValue(QUIZ_TIME);

        if (this.gameMode.equals("normal")) answerBtnList = new ArrayList<>();
        if (this.gameMode.equals("manuel")) {
            answerTf = new JTextField();
            answerTf.setBounds(75 , 130 , 510 , 50);
            answerTf.setFont(new Font("Arial" , Font.BOLD , 20));
            answerL = new JLabel("");
            answerL.setBounds(75 , 180 , 510 , 50);
            answerL.setFont(new Font("Arial" , Font.BOLD , 20));
            frame.add(answerTf);
            frame.add(answerL);
        }

        nextBtn = new JButton("Überprüfen");
        jokerBtn = new JButton("Joker");

        frame.add(questionL);
        frame.add(questionTimePb);
        frame.add(quizTimePb);
        frame.add(nextBtn);
        frame.add(jokerBtn);
        //Button-Klick-Event für den "next" Button
        nextBtn.addActionListener(e -> {
            if (answerChecked) {
                selectedAnswer = -1;
                Main.quiz.nextQuestion();
            } else {
                quizTimer.cancel();
                if (gameMode.equals("manuel")) if (answerTf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null , "Bitte geben Sie eine Antwort ein" , "JavaQuiz - Fehler" , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                questionTimer.cancel();
                checkAnswer();
                nextBtn.setText("Nächste Frage");
            }
        });
        //Button-Klick-Event für den Joker
        jokerBtn.addActionListener(e -> {
            questionTimer.cancel();
            Main.quiz.setJoker(false);
            if (gameMode.equals("normal")) {
                for (int i = 0; i < question.getAnswerList().length; i++) {
                    if (question.getAnswerList()[i].equals(question.getRightAnswer())) {
                        selectedAnswer = i;
                    }
                }
                checkAnswer();
            } else {
                answerL.setText("<html><body><p style='color: blue; text-align: center;'>Joker - Die richtige Antwort ist: " + question.getRightAnswer() + "</p></body></html>");
                answerL.setHorizontalAlignment(JLabel.CENTER);
                refreshButtons();
            }
        });
        refreshFrameSize();
        frame.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getSize().getWidth()) / 2 , (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getSize().getHeight()) / 2);
        frame.setVisible(true);

        quizTimeLeft = QUIZ_TIME; // 3 min
    }

    /**
     * Diese Methode setzt die Frage und startet den Timer.
     *
     * @param question      Dieser Parameter ist die Frage, die angezeigt werden soll.
     * @param questionIndex Gibt an, die wievielte Frage es ist, die angezeigt wird.
     */
    public void setQuestion(Question question , int questionIndex) {
        quizTimer = new Timer();
        quizTimer.scheduleAtFixedRate(getQuizTimerTask() , 0 , 250);
        //Setzt die Frage
        answerChecked = false;
        this.question = question;
        refreshButtons();
        questionL.setText("<html><body><p style='text-align: center;'>" + (questionIndex + 1) + ". Frage:<br>" + question.getQuestion() + "</p></body></html>");
        questionL.setVerticalAlignment(JLabel.CENTER);
        if (gameMode.equals("manuel")) {
            answerTf.setText("");
            answerL.setText("");
        }
        refreshFrameSize();

        questionTimePb.setValue(QUESTION_TIME);
        questionTimeLeft = QUESTION_TIME;

        questionTimer = new Timer();
        questionTimer.scheduleAtFixedRate(getQuestionTimerTask() , 0 , 50);
    }

    /**
     * Diese Methode färbt den ausgewählten Button weiß und die anderen grau.
     *
     * @param button Dieser Parameter ist der Button, der ausgewählt wurde und geändert werden soll.
     */
    private void selectButton(JButton button) {
        //Speichert die ausgewählte Antwort
        for (int i = 0; i < answerBtnList.size(); i++) {
            if (button == answerBtnList.get(i)) {
                selectedAnswer = i;
            }
        }
        refreshButtons();
    }

    /**
     * Diese Methode aktualisiert die Größe des Frames.
     */
    private void refreshFrameSize() {
        //Passt die Größe des Frames an die Anzahl der Antwort Buttons an
        if (gameMode.equals("normal")) {
            frame.setSize(660 , 235 + (answerBtnList.size() / 2 * 60));
        } else {
            frame.setSize(660 , 345);
        }
        //Zentriert den Frame
        //frame.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()-frame.getSize().getWidth())/2, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-frame.getSize().getHeight())/2);
    }

    /**
     * Diese Methode überprüft die Antwort und zeigt bei falscher Eingabe die richtige Antwort an.
     */
    private void checkAnswer() {
        //Überprüft die Antwort
        answerChecked = true;
        if (gameMode.equals("normal")) {
            if (selectedAnswer >= 0) if (question.getAnswerList()[selectedAnswer].equals(question.getRightAnswer())) {
                Main.quiz.addScore();
            }
        } else {
            if (answerTf.getText().equalsIgnoreCase(question.getRightAnswer())) {
                Main.quiz.addScore();
                answerL.setText("<html><body><p style='color: green; text-align: center;'>Richtig!</p></body></html>");
            } else {
                answerL.setText("<html><body><p style='color: red; text-align: center;'>Falsch! Die richtige Antwort ist: " + question.getRightAnswer() + "</p></body></html>");
            }
            answerL.setHorizontalAlignment(JLabel.CENTER);
        }
        refreshButtons();
    }

    /**
     * Diese Methode aktualisiert alle Buttons.
     */
    private void refreshButtons() {
        if (gameMode.equals("normal")) {
            // Fügt mehr Antwort Buttons hinzu oder entfernt sie
            // Überprüft, ob die Anzahl der Antwort-Buttons kleiner ist als die Anzahl der Antworten in der Frage
            if (question.getAnswerList().length > answerBtnList.size()) {
                // Wenn ja, erstellt neuen Button für die verbleibenden Antworten
                for (int i = answerBtnList.size(); i < question.getAnswerList().length; i++) {
                    // Erstellt einen neuen Button
                    JButton newAnswerBtn = new JButton();
                    // Fügt dem Button einen ActionListener hinzu, der die Methode selectButton aufruft, wenn der Button geklickt wird
                    newAnswerBtn.addActionListener(e -> selectButton(newAnswerBtn));
                    // Fügt den neuen Button zur Liste der Antwort-Buttons hinzu
                    answerBtnList.add(newAnswerBtn);
                    // Fügt den neuen Button zum Frame hinzu
                    frame.add(newAnswerBtn);
                }
            } else {
                // Die zusätzlichen Buttons sollen entfernt werden, wenn es mehr Buttons als Antworten gibt.
                for (int i = 0; i < answerBtnList.size(); i++) {
                    // Überprüft, ob der aktuelle Button-index größer ist als die Anzahl der Antworten (-1)
                    if (i > question.getAnswerList().length - 1) {
                        // Wenn ja, "versteckt" den Button
                        answerBtnList.get(i).setVisible(false);
                        // Entfernt den Button vom Frame
                        frame.remove(answerBtnList.get(i));
                        // Entfernt den Button aus der Liste der Antwort-Buttons
                        answerBtnList.remove(answerBtnList.get(i));
                    }
                }
            }
            //Formatiert die Buttons
            for (int i = 0; i < answerBtnList.size(); i++) {
                if (selectedAnswer == -1) {
                    answerBtnList.get(i).setEnabled(true);
                    answerBtnList.get(i).setBackground(null);
                    if (answerChecked) {
                        for (int j = 0; j < question.getAnswerList().length; j++) {
                            answerBtnList.get(i).setBackground(Color.red);
                            answerBtnList.get(j).setEnabled(false);
                            if (question.getAnswerList()[j].equals(question.getRightAnswer())) {
                                answerBtnList.get(j).setBackground(Color.green);
                            }
                        }
                    }
                } else {
                    if (answerChecked) {
                        answerBtnList.get(i).setEnabled(false);
                        for (int j = 0; j < question.getAnswerList().length; j++) {
                            if (question.getAnswerList()[j].equals(question.getRightAnswer())) {
                                for (JButton answerBtn : answerBtnList) {
                                    answerBtn.setBackground(null);
                                }
                                if (selectedAnswer == j) {
                                    answerBtnList.get(j).setBackground(Color.green);
                                } else {
                                    if (selectedAnswer >= 0) answerBtnList.get(selectedAnswer).setBackground(Color.red);
                                    answerBtnList.get(j).setBackground(Color.green);
                                }
                            }
                        }
                    } else {
                        answerBtnList.get(i).setEnabled(true);
                        answerBtnList.get(i).setBackground(null);
                        if (i == selectedAnswer) {
                            answerBtnList.get(i).setBackground(Color.white);
                        }
                    }

                }
                char option = (char) (i + 97);
                answerBtnList.get(i).setText(option + ") " + question.getAnswerList()[i]);
                int x = i % 2;
                int y = i / 2;
                answerBtnList.get(i).setBounds(75 + 260 * x , 130 + y * 60 , 250 , 50);
            }

            if (answerChecked) {
                nextBtn.setText("Nächste Frage");
                nextBtn.setEnabled(true);
                jokerBtn.setEnabled(false);
            } else {
                nextBtn.setText("Überprüfen");
                nextBtn.setEnabled(selectedAnswer != -1);
                jokerBtn.setEnabled(Main.quiz.getJoker());
            }

            nextBtn.setBounds(465 , 130 + answerBtnList.size() / 2 * 60 , 120 , 50);
            jokerBtn.setBounds(335 , 130 + answerBtnList.size() / 2 * 60 , 120 , 50);
        } else {
            if (answerChecked) {
                nextBtn.setText("Nächste Frage");
                nextBtn.setEnabled(true);
                jokerBtn.setEnabled(false);
                answerTf.setEnabled(false);
            } else {
                nextBtn.setText("Überprüfen");
                nextBtn.setEnabled(true);
                jokerBtn.setEnabled(Main.quiz.getJoker());
                answerTf.setEnabled(true);
            }
            nextBtn.setBounds(465 , 220 , 120 , 50);
            jokerBtn.setBounds(335 , 220 , 120 , 50);
        }
    }

    /**
     * Diese Methode schließt das Fenster.
     */
    public void dispose() {
        frame.dispose();
    }

    /**
     * Diese Methode gibt ein TimerTask-Objekt zurück.
     *
     * @return Dieses TimerTask-Objekt aktualisiert die verbleibende Zeit der Frage.
     */
    private TimerTask getQuestionTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                questionTimePb.setValue(questionTimeLeft--);
                if (questionTimeLeft > questionTimePb.getMaximum() / 2) questionTimePb.setForeground(Color.green);
                if (questionTimeLeft <= questionTimePb.getMaximum() / 2 && questionTimeLeft > questionTimePb.getMaximum() / 4)
                    questionTimePb.setForeground(Color.yellow);
                if (questionTimeLeft <= questionTimePb.getMaximum() / 4) questionTimePb.setForeground(Color.red);
                frame.repaint();
                if (questionTimeLeft <= 0) {
                    questionTimer.cancel();
                    quizTimer.cancel();
                    JOptionPane.showMessageDialog(null , "Die Zeit ist abgelaufen" , "JavaQuiz - Zeit abgelaufen" , JOptionPane.INFORMATION_MESSAGE);
                    checkAnswer();
                }
            }
        };
    }

    /**
     * Diese Methode gibt ein TimerTask-Objekt zurück.
     *
     * @return Dieses TimerTask-Objekt aktualisiert die verbleibende Zeit des Quiz.
     */
    private TimerTask getQuizTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                quizTimePb.setValue(quizTimeLeft--);
                if (quizTimeLeft <= 0) {
                    quizTimer.cancel();
                    questionTimer.cancel();
                    JOptionPane.showMessageDialog(null , "Die Zeit ist abgelaufen. Das Quiz wird beendet." , "JavaQuiz - Zeit abgelaufen" , JOptionPane.INFORMATION_MESSAGE);
                    Main.quiz.finish();
                }
            }
        };
    }

}
