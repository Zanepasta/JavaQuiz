import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Highscore-Klasse zur Speicherung und Anzeige von Bestenlisten.
 *
 * @version 1.0
 */
@SuppressWarnings("CallToPrintStackTrace")
public class Highscore {
    /**
     * Dieses Attribut speichert ein Feld von HighscoreItem-Objekten.
     *
     * @see HighscoreItem
     */
    private final HighscoreItem[] highscoreList;

    /**
     * Konstruktor der Klasse Highscore.
     * Erzeugt ein Feld von HighscoreItem-Objekten.
     *
     * @see HighscoreItem
     */
    public Highscore() {
        highscoreList = new HighscoreItem[10];
    }

    /**
     * Getter-Methode für das Attribut highscoreList.
     *
     * @return Gibt das Feld von HighscoreItem-Objekten zurück.
     * @see HighscoreItem
     */
    public HighscoreItem[] getHighscoreList() {
        return highscoreList;
    }

    /**
     * Diese Methode fügt ein HighscoreItem-Objekt in das Feld highscoreList ein.
     *
     * @param newHighscoreItem Das HighscoreItem-Objekt, das eingefügt werden soll.
     * @see HighscoreItem
     */
    public void addHighscoreItem(HighscoreItem newHighscoreItem) {
        for (int i = 0; i < highscoreList.length; i++) {
            if (highscoreList[i] == null) {
                highscoreList[i] = newHighscoreItem;
                break;
            } else if (newHighscoreItem.getScore() > highscoreList[i].getScore()) {
                for (int j = highscoreList.length - 1; j > i; j--) {
                    highscoreList[j] = highscoreList[j - 1];
                }
                highscoreList[i] = newHighscoreItem;
                break;
            }
        }
    }

    /**
     * Diese Methode zeigt die Bestenliste in einem Dialogfenster an.
     *
     * @see JOptionPane
     */
    public void showHighscore() {
        StringBuilder highscoreString = new StringBuilder("<html><body><table>");
        highscoreString.append("<tr><th>Rank</th><th>Name</th><th>Score</th></tr>");
        for (int i = 0; i < highscoreList.length; i++) {
            if (highscoreList[i] != null) {
                highscoreString.append("<tr><td>").append(i + 1).append("</td><td>")
                        .append(highscoreList[i].getName()).append("</td><td>")
                        .append(highscoreList[i].getScore()).append("</td></tr>");
            } else {
                highscoreString.append("<tr><td>").append(i + 1).append("</td><td>-</td><td>-</td></tr>");
            }
        }
        highscoreString.append("</table></body></html>");
        JOptionPane.showMessageDialog(null , highscoreString.toString() , "JavaQuiz - Highscore" , JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Diese Methode speichert die Bestenliste in einer JSON-Datei.
     *
     * @see JSONObject
     * @see JSONArray
     * @see FileWriter
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveHighscoreToJson() {
        JSONArray jsonArray = new JSONArray();
        for (HighscoreItem highscoreItem : highscoreList) {
            if (highscoreItem != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name" , highscoreItem.getName());
                jsonObject.put("score" , highscoreItem.getScore());
                jsonArray.put(jsonObject);
            }
        }
        File oldFile = new File("JavaQuizData/highscore.json");
        oldFile.delete();
        try (FileWriter file = new FileWriter("JavaQuizData/highscore.json")) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern der highscore.json-Datei" , "Fehler" , JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Diese Methode lädt die Bestenliste aus einer JSON-Datei.
     *
     * @see JSONObject
     * @see JSONArray
     * @see Files
     * @see Paths
     */
    public void loadHighscoreFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("JavaQuizData/highscore.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                int score = jsonObject.getInt("score");
                highscoreList[i] = new HighscoreItem(name , score);
            }
        } catch (IOException e) {
            File dir = new File("JavaQuizData");
            if (!dir.exists()) {
                if(!dir.mkdir()) {
                    JOptionPane.showMessageDialog(null, "Fehler beim Erstellen des JavaQuizData-Ordners" , "Fehler" , JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                    System.exit(1);
                }
                try (FileWriter file = new FileWriter("JavaQuizData/highscore.json")) {
                    file.write("[]");
                    file.flush();
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Fehler beim Erstellen der highscore.json-Datei" , "Fehler" , JOptionPane.ERROR_MESSAGE);
                    e2.printStackTrace();
                    System.exit(1);
                }
            }
            e.printStackTrace();
        }
    }
}
