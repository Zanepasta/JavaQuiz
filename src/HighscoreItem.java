/**
 * HighscoreItem.java
 * Stellt ein einzelnes Highscore-Element dar.
 */
public class HighscoreItem {
    /**
     * Dieses Attribut speichert den Namen des Spielers.
     *
     * @see #getName()
     */
    private String name;
    /**
     * Dieses Attribut speichert den Punktestand des Spielers.
     *
     * @see #getScore()
     */
    private int score;

    /**
     * Konstruktor für ein Highscore-Element.
     *
     * @param name  Name des Spielers
     * @param score Punktestand des Spielers
     */
    public HighscoreItem(String name , int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Diese Methode gibt den Namen des Spielers zurück.
     *
     * @return Name des Spielers
     * @see #name
     */
    public String getName() {
        return name;
    }

    /**
     * Diese Methode setzt den Namen des Spielers.
     *
     * @param name Name des Spielers
     * @see #name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Diese Methode gibt den Punktestand des Spielers zurück.
     *
     * @return Punktestand des Spielers
     * @see #score
     */
    public int getScore() {
        return score;
    }

    /**
     * Diese Methode setzt den Punktestand des Spielers.
     *
     * @param score Punktestand des Spielers
     * @see #score
     */
    public void setScore(int score) {
        this.score = score;
    }
}
