package pt.amchat.levelelements;

public enum Difficulty {
    EASY(150),
    MEDIUM(100),
    HARD(75),
    LEGEND(50);

    private int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
