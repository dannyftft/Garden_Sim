package game;

import plant.PlantData;

import java.util.ArrayList;

public class GameData {

    public PlayerConfig player;
    public EconomyConfig economy;
    public ArrayList<PlantData> plants;

    public static GameData load(String resourcePath) {
        return null;
    }

    public PlantData findPlant(String id) {
        return null;
    }
}
