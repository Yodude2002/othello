package game;

public class BoardState {

    private boolean[][] hasDisk, color;

    /**
     * Constructs a BoardState given arrays for the state of the board.
     * @param hasDisk a 2d array representing which tiles have disks.
     * @param color a 2d array representing the color of the disks of the tiles.
     */
    public BoardState(boolean[][] hasDisk, boolean[][] color) {
        this.hasDisk = hasDisk.clone();
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
     * @return a {@code boolean[][]} representing the colors of the tiles.
     */
    public boolean[][] getColor() {
        return color;
    }

    public void defaultSetup() {
        int r1 = color.length/2-1;
        int c1 = r1;
        int r2 = r1+1;
        int c2 = c1+1;
        hasDisk[r1][c1] = true;
        hasDisk[r1][c2] = true;
        hasDisk[r2][c1] = true;
        hasDisk[r2][c2] = true;
        color[r1][c1] = true;
        color[r2][c2] = true;
    }

}
