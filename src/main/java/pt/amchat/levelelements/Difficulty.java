package pt.amchat.levelelements;

public enum Difficulty {
    EASY(100),
    MEDIUM(80),
    HARD(60),
    LEGEND(40);

    private int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
