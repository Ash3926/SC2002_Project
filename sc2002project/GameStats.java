package SC2002_Project.sc2002project;

public class GameStats {
    
    private int currentTurn = 1;
    private int paidCustomers = 0;
    private int servedCustomers = 0;
    private int unhappyDepartures = 0;
    private int totalSatisfaction = 0;
    private int totalRevenue = 0;

    public GameStats() {}

    public int getCurrTurn() {
        return currentTurn;
    }

    public int getPaidCustomers() {
        return paidCustomers;
    }

    public int getTServedCustomers() {
        return servedCustomers;
    }

    public int getTUnhappyDepartures() {
        return unhappyDepartures;
    }

    public int getTSatisfaction() {
        return totalSatisfaction;
    }

    public int getTRevenue() {
        return totalRevenue;
    }

    public void incrementTurn() {
        currentTurn += 1;
    }

    public void incrementPaidCustomers() {
        paidCustomers += 1;
    }

    public void incrementServedCustomers() {
        servedCustomers += 1;
    }

    public void incrementUnhappyDepartures() {
        unhappyDepartures += 1;
    }

    public void updateTSatisfaction(int num) {
        totalSatisfaction += num;
    }

    public void increaseRevenue(int num) {
        totalRevenue += num;
    }

    public int calcAvgSatisfaction() {
        if (getPaidCustomers() == 0) {
            return 0;
        }
        return getTSatisfaction()/getPaidCustomers();
    }
}
