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
        this.id = data.id;
        this.name = data.name;
        this.description = data.description;
        this.seedCost = data.seedCost;
        this.basePrice = data.basePrice;
        this.witheredPrice = data.witheredPrice;
        this.growthDurationSeconds = data.growthDurationSeconds;
        this.witherDurationSeconds = data.witherDurationSeconds;
        this.stageCount = data.stageCount;
    }

    public abstract int getCurrentStage();

    public abstract boolean isWithered();

    public abstract boolean isFullyGrown();

    public abstract int getCurrentPrice();

    public abstract String getEstimatedTimeLeft();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSeedCost() {
        return seedCost;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getWitheredPrice() {
        return witheredPrice;
    }

    public int getStageCount() {
        return stageCount;
    }
}
