package plant;

public class Plant {

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
        return -1;
    }

    public String getEstimatedTimeLeft() {
        return "Unknown";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStageCount() {
        return stageCount;
    }

    public long getGrowthDurationSeconds() {
        return growthDurationSeconds;
    }

    public long getWitherDurationSeconds() {
        return witherDurationSeconds;
    }
}
