package bed;

import plant.RegularPlant;

public class GardenBed {

    private int bedIndex;
    private RegularPlant plant;

    public GardenBed(int bedIndex) {
        this.bedIndex = bedIndex;
        this.plant = null; // no plant when the bed is first created
    }

    public boolean isEmpty() {
        return plant == null; // null means nothing is planted here
    }

    public void plantSeed(RegularPlant newPlant) {
        this.plant = newPlant; // stores the plant in this bed
    }

    public RegularPlant getPlant() {
        return plant;
    }

    public void clearPlant() {
        this.plant = null; // removes the plant after it is sold or withered away
    }

    public int getBedIndex() {
        return bedIndex;
    }
}