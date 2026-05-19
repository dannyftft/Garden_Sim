package game;

public class Player {

    private int money;

    public Player(int startingMoney) {
        this.money = startingMoney; // sets the starting balance
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        this.money = this.money + amount; // adds to the current balance when selling a plant
    }

    /**
     * deducts the amount from the balance if there is enough money
     * @param amount the amount to spend
     * @return true if the purchase went through and false if the balance was too low
     */
    public boolean spendMoney(int amount) {
        if (amount > money) {
            return false; // not enough money, purchase blocked
        }
        this.money = this.money - amount; // deducts the cost from the balance
        return true; // purchase went through
    }
}