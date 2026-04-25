package plant;

public abstract class Plant {

    protected String id;
    protected String name;
    protected String description;
    protected int seedCost;
    protected int basePrice;
    protected int witheredPrice;
    protected long growthDurationSeconds;
    protected long witherDurationSeconds;
    protected int stageCount;

    public Plant(PlantData data) {
    }

    public abstract int getCurrentStage();

    public abstract boolean isWithered();

    public abstract boolean isFullyGrown();

    public abstract int getCurrentPrice();

    public abstract String getEstimatedTimeLeft();

    public String getId() {
        return null;
    }

    public String getName() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public int getSeedCost() {
        return 0;
    }

    public int getBasePrice() {
        return 0;
    }

    public int getWitheredPrice() {
        return 0;
    }

    public int getStageCount() {
        return 0;
    }
}
