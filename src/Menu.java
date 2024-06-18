import javax.swing.*;
import java.awt.*;

/**
 * Die Menu-Klasse ist das Hauptmenü des Spiels.
 * Sie enthält die Schaltflächen zum Starten des Spiels, Öffnen der Einstellungen, Anzeigen des Highscores und Beenden des Spiels.
 */
public class Menu {
    /**
     * Dieses Attribut speichert ein JLabel-Objekt, das den Titel des Quiz darstellt.
     */
    protected final JLabel titleL;
    /**
     * Dieses Attribut speichert ein JButton-Objekt, das das Quiz startet.
     */
    protected final JButton startBtn;
    /**
     * Dieses Attribut speichert ein JButton-Objekt, das die Einstellungen öffnet.
     */
    protected final JButton settingsBtn;
    /**
     * Dieses Attribut speichert ein JButton-Objekt, das den Highscore anzeigt.
     */
    protected final JButton highscoreBtn;
    /**
     * Dieses Attribut speichert ein JButton-Objekt, das das Quiz beendet.
     */
    protected final JButton exitBtn;
    /**
     * Dieses Attribut speichert ein JFrame-Objekt, das das Hauptmenü darstellt.
     */
    private final JFrame frame;

    /**
     * Der Konstruktor initialisiert das Hauptmenü.
     * Es wird ein JFrame-Objekt erstellt und konfiguriert.
     * Es werden ein JLabel-Objekt für den Titel, vier JButton-Objekte für die Schaltflächen und ein ActionListener für die Schaltflächen erstellt.
     */
    public Menu() {
        frame = new JFrame("JavaQuiz - Menü");
        frame.setLayout(null);
        frame.setSize(400 , 405);
        frame.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getSize().getWidth()) / 2 , (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getSize().getHeight()) / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        titleL = new JLabel("JavaQuiz");
        titleL.setFont(new Font("Arial" , Font.BOLD , 30));
        titleL.setHorizontalAlignment(JLabel.CENTER);
        titleL.setBounds(10 , 0 , 380 , 100);

        startBtn = new JButton("Start");
        startBtn.setBounds(75 , 100 , 250 , 50);
        startBtn.addActionListener(e -> {
            frame.setVisible(false);
            Main.quiz = new Quiz(Main.getGameMode());
            Main.quiz.start();
        });

        settingsBtn = new JButton("Einstellungen");
        settingsBtn.setBounds(75 , 160 , 250 , 50);
        settingsBtn.addActionListener(e -> {
            frame.setVisible(false);
            new SettingsFrame();
        });

        highscoreBtn = new JButton("Highscore");
        highscoreBtn.setBounds(75 , 220 , 250 , 50);
        highscoreBtn.addActionListener(e -> Main.highscore.showHighscore());

        exitBtn = new JButton("Beenden");
        exitBtn.setBounds(75 , 280 , 250 , 50);
        exitBtn.addActionListener(e -> System.exit(0));

        frame.add(titleL);
        frame.add(startBtn);
        frame.add(settingsBtn);
        frame.add(highscoreBtn);
        frame.add(exitBtn);
    }

    /**
     * Diese Methode zeigt das Hauptmenü an.
     */
    public void show() {
        frame.setVisible(true);
    }
}
