package data;

import com.google.gson.Gson;
import game.Game;
import bed.GardenBed;
import plant.RegularPlant;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SaveManager {

    private static String SAVE_FILE = "Garden_Sim_save.json"; // saved in the project root folder

    private static File getSaveFile() {
        return new File(SAVE_FILE); // points to the save file in the run directory
    }

    public static boolean hasSave() {
        return getSaveFile().exists(); // returns true if the save file is on disk
    }

    public static boolean save(Game game) {
        SaveData data = new SaveData();
        data.money = game.getPlayer().getMoney(); // stores the player's current balance

        for (int i = 0; i < game.getBeds().size(); i++) {
            GardenBed bed = game.getBeds().get(i);
            BedSaveData bedData = new BedSaveData();

            if (bed.isEmpty()) {
                bedData.isEmpty = true; // marks the bed as empty, nothing else needed
            } else {
                RegularPlant plant = bed.getPlant();
                bedData.isEmpty = false;
                bedData.plantId = plant.getId();           // which plant is in this bed
                bedData.plantedAt = plant.getPlantedAt();  // when it was planted so growth continues
                bedData.priceJitter = plant.getPriceJitter(); // saves the rolled jitter so price doesnt change on load
            }

            data.beds.add(bedData);
        }

        try {
            FileWriter writer = new FileWriter(getSaveFile());
            Gson gson = new Gson();
            writer.write(gson.toJson(data)); // converts the SaveData object to json and writes it
            writer.close();
            return true;
        } catch (Exception e) {
            return false; // something went wrong writing the file
        }
    }

    public static SaveData load() {
        File file = getSaveFile();
        if (!file.exists()) {
            return null; // no save file found
        }
        try {
            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            SaveData data = gson.fromJson(reader, SaveData.class); // reads the json back into a SaveData object
            reader.close();
            return data;
        } catch (Exception ex) {
            return null; // something went wrong reading the file
        }
    }

    public static boolean deleteSave() {
        return getSaveFile().delete(); // removes the save file from disk
    }
}