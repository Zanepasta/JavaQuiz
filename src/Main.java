/**
 * Main-Klasse des JavaQuiz-Spiels.
 * Diese Klasse enthält die main-Methode und ist der Einstiegspunkt des Programms.
 * Sie enthält auch die Instanzen der Highscore- und Quiz-Klasse.
 */
public class Main {
    /**
     * Dieses Attribut speichert ein Menu-Objekt.
     * Es wird verwendet, um das Hauptmenü anzuzeigen.
     */
    private static final Menu menu = new Menu();
    /**
     * Dieses Attribut speichert ein Highscore-Objekt.
     * Es wird verwendet, um die Highscore-Liste zu verwalten.
     */
    public static Highscore highscore = new Highscore();
    /**
     * Dieses Attribut speichert ein Quiz-Objekt.
     * Es wird verwendet, um die Fragen und Antworten des Quiz zu verwalten.
     */
    public static Quiz quiz;
    /**
     * Dieses Attribut speichert den Spielmodus.
     * Der Spielmodus kann entweder "normal" oder "manuel" sein.
     */
    private static String gameMode = "normal";

    /**
     * Diese Methode ist der Einstiegspunkt des Programms.
     * Sie lädt die Highscore-Liste aus einer JSON-Datei und zeigt das Hauptmenü an.
     *
     * @param args Die Kommandozeilenargumente.
     */
    public static void main(String[] args) {
        highscore.loadHighscoreFromJson();
        Quiz q = new Quiz("normal");
        q.init();
        showMenu();
    }

    /**
     * Diese Methode zeigt das Hauptmenü an.
     */
    public static void showMenu() {
        menu.show();
    }

    /**
     * Diese Methode gibt den aktuellen Spielmodus zurück.
     *
     * @return Den aktuellen Spielmodus.
     */
    public static String getGameMode() {
        return gameMode;
    }

    /**
     * Diese Methode startet ein neues Quiz.
     */
    public static void setGameMode(String gameMode) {
        Main.gameMode = gameMode;
    }
}