package game;

public class BoardState {

    private boolean[][] hasDisk, color;

    /**
     * Constructs a BoardState given arrays for the state of the board.
     * @param hasDisk a 2d array representing which tiles have disks.
     * @param color a 2d array representing the color of the disks of the tiles.
     */
    public BoardState(boolean[][] hasDisk, boolean[][] color) {
        this.hasDisk = hasTile.clone();
        this.color = color.clone();
    }

    /**
     * Returns a 2d array representing which tiles have disks.
     * @return a {@code boolean[][]} representing which tiles have disks.
     */
    public boolean[][] getHasTile() {
        return hasDisk;
    }

    /**
     * Returns an array representing which tiles are which color.
     *
     * White tiles are represented by {@code true}, and Black tiles are represented by {@code false}.
     *
     * If a tile has no disk, its state is not guaranteed.
     *
     * @return a {@code boolean[][]} representing the color of the tiles.
     */
    public boolean[][] getColor() {
        return color;
    }

}
