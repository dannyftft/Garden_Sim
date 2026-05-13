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

    public Game(SaveData saveData) {
        this.gameData = GameData.load("/plants.json");
        this.beds = new ArrayList<>();

        if (saveData == null) {
            this.player = new Player(gameData.player.startingMoney); // fresh start
            for (int i = 0; i < gameData.player.startingBedCount; i++) {
                beds.add(new GardenBed(i));
            }
        } else {
            this.player = new Player(saveData.money); // restored balance
            for (int i = 0; i < saveData.beds.size(); i++) {
                BedSaveData bedData = saveData.beds.get(i);
                GardenBed bed = new GardenBed(i);
                if (!bedData.isEmpty) {
                    PlantData plantData = gameData.findPlant(bedData.plantId);
                    if (plantData != null) {
                        RegularPlant plant = new RegularPlant(plantData, gameData.economy, bedData.plantedAt, bedData.priceJitter); // restores jitter from save
                        bed.plantSeed(plant);
                    }
                }
                beds.add(bed);
            }
        }
    }

    public boolean buyAndPlant(int bedIndex, String plantId) {
        PlantData plantData = gameData.findPlant(plantId); // looks up the plant by id
        if (plantData == null) {
            return false; // plant id not found in the json
        }
        boolean paid = player.spendMoney(plantData.seedCost); // tries to deduct the seed cost
        if (!paid) {
            return false; // not enough money
        }
        RegularPlant plant = new RegularPlant(plantData, gameData.economy, System.currentTimeMillis(), -1); // -1 means roll a fresh jitter
        beds.get(bedIndex).plantSeed(plant);
        return true;
    }

    public int sellPlant(int bedIndex) {
        GardenBed bed = beds.get(bedIndex);
        if (bed.isEmpty()) {
            return 0; // nothing to sell
        }
        int price = bed.getPlant().getCurrentPrice(); // gets the current sell value
        player.addMoney(price); // adds the money to the player's balance
        bed.clearPlant(); // removes the plant from the bed
        return price;
    }


    public void addDebugMoney(int amount) {
        player.addMoney(amount);
    }

    public void debugAdvanceStage(int bedIndex) {
        GardenBed bed = beds.get(bedIndex);
        if (bed.isEmpty()) {
            return; // nothing to advance
        }
        RegularPlant plant = bed.getPlant();
        long timePerStage = (plant.getGrowthDurationSeconds() / plant.getStageCount()) * 1000; // milliseconds per stage
        plant.shiftPlantedAt(-timePerStage); // shifts time back by one stage worth
    }

    public void debugForceWither(int bedIndex) {
        GardenBed bed = beds.get(bedIndex);
        if (bed.isEmpty()) {
            return;
        }
        RegularPlant plant = bed.getPlant();
        long totalTime = (plant.getGrowthDurationSeconds() + plant.getWitherDurationSeconds() + 60) * 1000;
        plant.shiftPlantedAt(-totalTime); // shifts time back far enough to guarantee withered state
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<GardenBed> getBeds() {
        return beds;
    }

    public GameData getGameData() {
        return gameData;
    }
}