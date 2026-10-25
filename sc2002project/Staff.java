package SC2002_Project.sc2002project;

public abstract class Staff {

    private int tasksUsed = 0;
    private final int maxTask = 1;

    public void resetForTurn() {
        tasksUsed = 0;
    }

    public boolean canAct() {
        return tasksUsed < maxTask;
    }

    protected void markTaskUsed() {
        if (!canAct()) {
            throw new IllegalStateException(
                "Staff has already completed their task this turn."
            );
        }

        tasksUsed++;
    }
}
