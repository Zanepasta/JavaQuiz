import javax.swing.*;
import java.util.ArrayList;

public class NewQuestionFrame {
    /**
     * Dieses Attribut speichert das Label-Objekt für die Überschrift des Fensters.
     */
    protected final JLabel titleL;
    /**
     * Dieses Attribut speichert das JFrame-Objekt für das Fenster.
     */
    private final JFrame frame;
    /**
     * Dieses Attribut speichert das Label-Objekt für die Überschrift der Frage.
     */
    private final JLabel questionL;
    /**
     * Dieses Attribut speichert das Textfeld-Objekt zum Eintragen der Frage.
     */
    private final JTextField questionTf;
    /**
     * Dieses Attribut speichert das Label-Objekt für die Überschrift der richtigen Antwort.
     */
    private final JLabel rightAnswerL;
    /**
     * Dieses Attribut speichert das Textfeld-Objekt zum Eintragen der richtigen Antwort.
     */
    private final JTextField rightAnswerTf;
    /**
     * Dieses Attribut speichert das Label-Objekt für die überschrift der Antwortmöglichkeiten-liste.
     */
    private final JLabel answerListL;
    /**
     * Dieses Attribut speichert die Liste von Label-Objekten zum Anzeigen der Indexe der Antwortmöglichkeiten. [a), b), c), ...]
     */
    private final ArrayList<JLabel> answerIndexLList;
    /**
     * Dieses Attribut speichert die Liste von Textfeld-Objekten zum Eintragen der Antwortmöglichkeiten.
     */
    private final ArrayList<JTextField> answerTfList;
    /**
     * Dieses Attribut speichert die Liste von Button-Objekten zum Löschen einer Antwortmöglichkeit.
     */
    private final ArrayList<JButton> deleteAnswerBtnList;
    /**
     * Dieses Attribut speichert den Button zum Hinzufügen einer Antwortmöglichkeit.
     */
    private final JButton addAnswerBtn;
    /**
     * Dieses Attribut speichert den Button zum Speichern.
     */
    private final JButton saveBtn;
    /**
     * Dieses Attribut speichert den Button zum Abbrechen.
     */
    private final JButton cancelBtn;
    /**
     * Diese Instanz speichert die Fragen.
     */
    private final Quiz quiz = new Quiz("normal");

    public NewQuestionFrame() {
        frame = new JFrame("JavaQuiz - Neue Frage");
        frame.setLayout(null);
        frame.setSize(400 , 405);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        titleL = new JLabel("Neue Frage");
        titleL.setBounds(75 , 25 , 210 , 40);
        titleL.setFont(titleL.getFont().deriveFont(30f));
        titleL.setHorizontalAlignment(JLabel.CENTER);

        questionL = new JLabel("Frage:");
        questionL.setBounds(75 , 75 , 210 , 30);

        questionTf = new JTextField();
        questionTf.setBounds(75 , 115 , 250 , 30);

        rightAnswerL = new JLabel("Richtige Antwort: [ a) ]");
        rightAnswerL.setBounds(75 , 155 , 210 , 30);

        rightAnswerTf = new JTextField();
        rightAnswerTf.setBounds(75 , 195 , 250 , 30);

        answerListL = new JLabel("Antworten:");
        answerListL.setBounds(75 , 235 , 210 , 30);

        answerIndexLList = new ArrayList<>();
        answerTfList = new ArrayList<>();
        deleteAnswerBtnList = new ArrayList<>();
        addAnswerBtn = new JButton("Antwort hinzufügen");
        addAnswerBtn.setBounds(75 , 275 , 250 , 30);
        addAnswerBtn.addActionListener(e -> addAnswer());

        saveBtn = new JButton("Speichern");
        saveBtn.setBounds(75 , 315 , 100 , 50);
        saveBtn.addActionListener(e -> saveQuestion());

        cancelBtn = new JButton("Abbrechen");
        cancelBtn.setBounds(225 , 315 , 100 , 50);
        cancelBtn.addActionListener(e -> {
            frame.dispose();
            Main.showMenu();
        });

        frame.add(titleL);
        frame.add(questionL);
        frame.add(questionTf);
        frame.add(rightAnswerL);
        frame.add(rightAnswerTf);
        frame.add(answerListL);
        frame.add(addAnswerBtn);
        frame.add(saveBtn);
        frame.add(cancelBtn);

        relocateElements();
        frame.setVisible(true);
    }

    /**
     * Diese Methode fügt die Elemente zum Eintragen einer weiteren Antwortmöglichkeit hinzu.
     */
    private void addAnswer() {
        char index = (char) (98 + answerIndexLList.size());
        JLabel answerIndexLabel = new JLabel((index) + ")");
        answerIndexLList.add(answerIndexLabel);

        JTextField answerField = new JTextField();
        answerTfList.add(answerField);

        JButton deleteAnswerBT = new JButton("-");
        deleteAnswerBT.addActionListener(e -> deleteAnswer(answerField));
        deleteAnswerBtnList.add(deleteAnswerBT);

        frame.add(answerIndexLabel);
        frame.add(answerField);
        frame.add(deleteAnswerBT);
        relocateElements();
    }

    /**
     * Diese Methode löscht die Elemente zum Eintragen einer Antwortmöglichkeit.
     *
     * @param answerField Antwort, die gelöscht werden soll.
     */
    private void deleteAnswer(JTextField answerField) {
        int index = answerTfList.indexOf(answerField);
        frame.remove(answerIndexLList.get(index));
        frame.remove(answerField);
        frame.remove(deleteAnswerBtnList.get(index));
        answerIndexLList.remove(index);
        answerTfList.remove(index);
        deleteAnswerBtnList.remove(index);

        relocateElements();
    }

    /**
     * Diese Methode speichert die Frage in der Quiz-Instanz und schließt das Fenster.
     */
    private void saveQuestion() {
        String question = questionTf.getText();
        String rightAnswer = rightAnswerTf.getText();
        ArrayList<String> answerList = new ArrayList<>();
        answerList.add(rightAnswer);
        for (JTextField answerField : answerTfList) {
            answerList.add(answerField.getText());
        }
        quiz.addQuestion(new Question(question , rightAnswer , answerList.toArray(new String[0])));
        frame.dispose();
        Main.showMenu();
    }

    /**
     * Diese Methode setzt die Positionen aller Elemente neu.
     */
    private void relocateElements() {
        questionL.setBounds(75 , 75 , 210 , 30);
        questionTf.setBounds(75 , 115 , 250 , 30);
        rightAnswerL.setBounds(75 , 155 , 210 , 30);
        rightAnswerTf.setBounds(75 , 195 , 250 , 30);
        answerListL.setBounds(75 , 235 , 210 , 30);
        addAnswerBtn.setBounds(75 , 275 + answerTfList.size() * 40 , 250 , 30);
        saveBtn.setBounds(75 , 315 + answerTfList.size() * 40 , 100 , 50);
        cancelBtn.setBounds(225 , 315 + answerTfList.size() * 40 , 100 , 50);
        frame.setSize(400 , 440 + answerTfList.size() * 40);
        for (int i = 0; i < answerIndexLList.size(); i++) {
            answerIndexLList.get(i).setBounds(75 , 275 + i * 40 , 20 , 30);
            answerTfList.get(i).setBounds(100 , 275 + i * 40 , 185 , 30);
            deleteAnswerBtnList.get(i).setBounds(285 , 275 + i * 40 , 40 , 29);
            answerIndexLList.get(i).setText((char) (98 + i) + ")");
        }
    }
}
