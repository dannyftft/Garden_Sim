package plant;

import game.EconomyConfig;

public class RegularPlant extends Plant {

    private long plantedAt;
    private int priceJitter;
    private int priceRoundTo;
    private double[] stageMultipliers;

    public RegularPlant(PlantData data, EconomyConfig economy) {
        super(data);
    }

    public RegularPlant(PlantData data, EconomyConfig economy, long plantedAt, int priceJitter) {
        super(data);
    }

    public long getPlantedAt() {
        return 0;
    }

    public int getPriceJitter() {
        return 0;
    }

    public int getCurrentStage() {
        return 0;
    }

    public boolean isWithered() {
        return false;
    }

    public boolean isFullyGrown() {
        return false;
    }

    public int getCurrentPrice() {
        return 0;
    }

    public String getEstimatedTimeLeft() {
        return null;
    }
}
