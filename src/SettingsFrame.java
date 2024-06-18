import javax.swing.*;
import java.awt.*;

/**
 * Diese Klasse erstellt ein Fenster, in dem der Benutzer die Einstellungen des Spiels ändern kann.
 * Der Benutzer kann zwischen den Spielmodi "Normal" und "Manuel" wählen.
 */
public class SettingsFrame {
    /**
     * Dieses Attribut speichert ein JLabel-Objekt, das den Titel des Fensters darstellt.
     */
    protected final JLabel titleL;
    /**
     * Dieses Attribut speichert ein JButton-Objekt, das den Benutzer die Einstellungen speichern lässt.
     */
    protected final JButton saveBtn;
    /**
     * Dieses Attribut speichert ein JLabel-Objekt, das die Überschrift für die Spielmodusauswahl darstellt.
     */
    protected final JLabel gameModeL;
    /**
     * Dieses Attribut speichert ein JButton-Objekt, das den Benutzer über die Bedeutung des Spielmodus informiert.
     */
    protected final JButton gameModeHelpBtn;
    /**
     * Dieses Attribut speichert ein JButton-Objekt, das den Benutzer eine neue Frage hinzufügen lässt.
     */
    protected final JButton newQuestionBtn;
    /**
     * Dieses Attribut speichert ein JFrame-Objekt, das das Fenster darstellt, in dem die Einstellungen geändert werden können.
     */
    private final JFrame frame;
    /**
     * Dieses Attribut speichert ein JToggleButton-Objekt, das den Benutzer den Spielmodus "Normal" auswählen lässt.
     */
    private final JToggleButton normalTBtn;
    /**
     * Dieses Attribut speichert ein JToggleButton-Objekt, das den Benutzer den Spielmodus "Manuel" auswählen lässt.
     */
    private final JToggleButton manuelTBtn;

    /**
     * Dieser Konstruktor erstellt ein neues Fenster, in dem der Benutzer die Einstellungen des Spiels ändern kann.
     */
    public SettingsFrame() {
        frame = new JFrame("JavaQuiz - Einstellungen");
        frame.setLayout(null);
        frame.setSize(360 , 405);
        frame.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getSize().getWidth()) / 2 , (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getSize().getHeight()) / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        titleL = new JLabel("Einstellungen");
        titleL.setBounds(75 , 25 , 210 , 40);
        titleL.setFont(new Font("Arial" , Font.BOLD , 30));
        titleL.setHorizontalAlignment(JLabel.CENTER);

        gameModeL = new JLabel("Spielmodus:");
        gameModeL.setFont(new Font("Arial" , Font.BOLD , 20));
        gameModeL.setBounds(75 , 75 , 210 , 30);

        gameModeHelpBtn = new JButton("?");
        gameModeHelpBtn.setBounds(235 , 75 , 50 , 30);
        gameModeHelpBtn.addActionListener(e -> gameModeHelpBtnClickEvent());

        normalTBtn = new JToggleButton("Normal");
        normalTBtn.setBounds(75 , 115 , 100 , 50);
        normalTBtn.addActionListener(e -> jToggleButtonActionPerformed((JToggleButton) e.getSource()));

        manuelTBtn = new JToggleButton("Manuel");
        manuelTBtn.setBounds(185 , 115 , 100 , 50);
        manuelTBtn.addActionListener(e -> jToggleButtonActionPerformed((JToggleButton) e.getSource()));

        if (Main.getGameMode().equals("normal")) {
            normalTBtn.setSelected(true);
        } else {
            manuelTBtn.setSelected(true);
        }

        newQuestionBtn = new JButton("Neue Frage hinzufügen");
        newQuestionBtn.setBounds(75 , 200 , 210 , 50);
        newQuestionBtn.addActionListener(e -> {
            frame.setVisible(false);
            new NewQuestionFrame();
        });

        saveBtn = new JButton("Speichern");
        saveBtn.setBounds(130 , 300 , 100 , 50);
        saveBtn.addActionListener(e -> {
            switchGameMode();
            frame.setVisible(false);
            Main.showMenu();
        });

        frame.add(titleL);
        frame.add(gameModeL);
        frame.add(gameModeHelpBtn);
        frame.add(normalTBtn);
        frame.add(manuelTBtn);
        frame.add(newQuestionBtn);
        frame.add(saveBtn);

        frame.setVisible(true);
    }

    /**
     * Diese Methode wird aufgerufen, wenn der Benutzer auf einen der beiden JToggleButtons klickt.
     *
     * @param button Der JToggleButton, auf den der Benutzer geklickt hat.
     */
    private void jToggleButtonActionPerformed(JToggleButton button) {
        normalTBtn.setSelected(false);
        manuelTBtn.setSelected(false);
        if (button.equals(normalTBtn)) {
            normalTBtn.setSelected(true);
        } else {
            manuelTBtn.setSelected(true);
        }
    }

    /**
     * Diese Methode wird aufgerufen, wenn der Benutzer auf den Speichern-Button klickt.
     * Es speichert den ausgewählten Spielmodus und schließt das Fenster.
     *
     * @see Main#setGameMode(String)
     */
    private void switchGameMode() {
        if (normalTBtn.isSelected()) {
            Main.setGameMode("normal");
        } else {
            Main.setGameMode("manuel");
        }
    }

    /**
     * Diese Methode wird aufgerufen, wenn der Benutzer auf den Hilfe-Button klickt.
     * Es zeigt ein Dialogfeld mit Informationen über die Spielmodi.
     *
     * @see JOptionPane
     */
    private void gameModeHelpBtnClickEvent() {
        JOptionPane.showMessageDialog(frame , "<html><body><h1>Spielmodi:</h1><h2>Normal:</h2><p>In diesem Spielmodus sollen Sie 10 Multiplechoicefragen beantworten. Dafür haben Sie, für jede Frage jeweils 30 Sekunden und für das gesamte Quiz 3 minuten Zeit.</p><h2>Manuel:</h2><p>Anders als im Normalen Spielmodus geben Sie die Lösungen in diesem Spielmodus eigenständig in ein Textfeld ein. Auch hier haben Sie für jede Frage 30 Sekunden Zeit und für das gesamte Quiz 3 Minuten.</p></body></html>" , "JavaQuiz - Hilfe" , JOptionPane.INFORMATION_MESSAGE);
    }
}
