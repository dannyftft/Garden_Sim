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
        this.beds = new ArrayList<>();
        this.player = new Player(10);

        for (int i = 0; i < 8; i++) {
            beds.add(new GardenBed(i));
        }
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
        return player;
    }

    public ArrayList<GardenBed> getBeds() {
        return beds;
    }

    public GameData getGameData() {
        return null;
    }
}
