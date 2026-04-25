package game;

import bed.GardenBed;
import data.BedSaveData;
import data.SaveData;
import plant.PlantData;
import plant.RegularPlant;

import java.util.ArrayList;

public class Game {

    private Player player;
    private ArrayList<GardenBed> beds;
    private GameData gameData;
    private int specialRefreshOffset;

    public Game() {
    }

    public Game(SaveData saveData) {
    }

    public boolean buyAndPlant(int bedIndex, String plantId) {
        return false;
    }

    public int sellPlant(int bedIndex) {
        return 0;
    }

    public void addDebugMoney(int amount) {
    }

    public void debugAdvanceStage(int bedIndex) {
    }

    public void debugForceWither(int bedIndex) {
    }

    public void debugRefreshSpecials() {
    }

    public int getSpecialRefreshOffset() {
        return 0;
    }

    public Player getPlayer() {
        return null;
    }

    public ArrayList<GardenBed> getBeds() {
        return null;
    }

    public GameData getGameData() {
        return null;
    }
}
