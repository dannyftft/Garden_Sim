package data;

import game.Game;

public class SaveManager {

    private static String getSavePath() {
        return "";
    }

    public static boolean hasSave() {
        return false;
    }

    public static boolean save(Game game) {
        return false;
    }

    public static SaveData load() {
        return null;
    }

    public static boolean deleteSave() {
        return false;
    }
}
