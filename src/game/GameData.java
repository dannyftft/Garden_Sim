package game;

import com.google.gson.Gson;
import plant.PlantData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameData {

    public PlayerConfig player;
    public EconomyConfig economy;
    public ArrayList<PlantData> plants;

    // reads plants.json from the resources folder and returns a filled GameData object
    public static GameData load(String resourcePath) {
        try {
            InputStream stream = GameData.class.getResourceAsStream(resourcePath);
            InputStreamReader reader = new InputStreamReader(stream);
            Gson gson = new Gson();
            return gson.fromJson(reader, GameData.class);
        } catch (Exception ex) {
            return null; // something went wrong reading the file
        }
    }

    // loops through all plants and returns the one matching the given id
    public PlantData findPlant(String id) {
        for (int i = 0; i < plants.size(); i++) {
            PlantData plant = plants.get(i);
            if (plant.id.equals(id)) {
                return plant; // found it
            }
        }
        return null; // no plant with that id exists
    }
}