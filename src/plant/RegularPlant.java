package plant;

import game.EconomyConfig;

import java.util.ArrayList;

public class RegularPlant extends Plant {

    private long plantedAt;
    private int priceJitter;
    private int priceRoundTo;
    private ArrayList<Double> stageMultipliers; // price multiplier for each stage loaded from growthStyle

    public RegularPlant(PlantData data, EconomyConfig economy, long plantedAt) {
        super(data);
        this.plantedAt = plantedAt; // passed in so save/load and fresh planting both work the same way
        this.stageMultipliers = loadMultipliers(data.growthStyle, economy);
        this.priceJitter = calculateJitter(data.basePrice, economy.priceJitterPercent); // random price variation rolled once at planting
        this.priceRoundTo = economy.priceRoundTo;
    }

    // returns how many seconds have passed since the seed was planted
    private long elapsedSeconds() {
        return (System.currentTimeMillis() - plantedAt) / 1000;
    }


    // returns the current growth stage as a 0-based index (0 = just planted, stageCount-1 = last stage)
    public int getCurrentStage() {
        if (isFullyGrown()) {
            return stageCount - 1; // cap at the last stage so it never goes out of bounds
        }
        double progress = (double) elapsedSeconds() / growthDurationSeconds; // 0.0 to 1.0
        int stage = (int) (progress * stageCount); // maps progress to a stage index
        if (stage >= stageCount) {
            stage = stageCount - 1; // safety cap in case of floating point overshoot
        }
        return stage;
    }

    // returns true once the plant has been growing for its full growthDurationSeconds
    public boolean isFullyGrown() {
        return elapsedSeconds() >= growthDurationSeconds;
    }

    // returns true if the plant has been left too long past its grow time
    public boolean isWithered() {
        return elapsedSeconds() >= growthDurationSeconds + witherDurationSeconds;
    }

    // returns the sell price based on the current stage multiplier plus the jitter rolled at planting
    public int getCurrentPrice() {
        if (isWithered()) {
            return witheredPrice; // flat low price once withered
        }
        int stage = getCurrentStage();
        double multi = stageMultipliers.get(stage); // gets the price multiplier for the current stage
        double raw = basePrice * multi + priceJitter;
        return roundToNearest((int) raw, priceRoundTo);
    }

    // gives the player a rough idea of how long is left without showing an exact countdown
    public String getEstimatedTimeLeft() {
        long secondsLeft = growthDurationSeconds - elapsedSeconds();
        if (secondsLeft <= 0) {
            return "Ready!";
        }
        if (secondsLeft < 120) {
            return "Almost ready"; // under 2 minutes
        }
        if (secondsLeft < 600) {
            return "A few minutes"; // under 10 minutes
        }
        if (secondsLeft < 3600) {
            return "Less than an hour";
        }
        long hours = secondsLeft / 3600;
        if (hours == 1) {
            return "About 1 hour";
        }
        return "About " + hours + " hours";
    }

    // pulls the multiplier list for this plant's growth style out of the economy config
    private ArrayList<Double> loadMultipliers(String growthStyle, EconomyConfig economy) {
        return economy.growthStyles.get(growthStyle);
    }

    // rolls a random price jitter
    private int calculateJitter(int basePrice, double jitterPercent) {
        int max = (int) (basePrice * jitterPercent);
        int random = (int) (Math.random() * (max * 2 + 1));
        return random - max;
    }

    // rounds to the nearest multiple of n so prices end in 0 or 5
    private int roundToNearest(int value, int n) {
        return ((value + n / 2) / n) * n;
    }

    public long getPlantedAt() {
        return plantedAt;
    }

    public int getPriceJitter() {
        return priceJitter;
    }
}