package game;

public class BoardState {

    private boolean[][] hasTile, color;

    /**
     * Constructs a BoardState given arrays for the state of the board.
     * @param hasTile a 2d array representing which tiles have pieces.
     * @param color a 2d array representing the color of the pieces of the tiles.
     */
    public BoardState(boolean[][] hasTile, boolean[][] color) {
        this.hasTile = hasTile.clone();
        this.color = color.clone();
    }

    /**
     * Returns a 2d array representing which tiles have pieces.
     * @return a {@code boolean[][]} representing which tiles have pieces.
     */
    public boolean[][] getHasTile() {
        return hasTile;
    }

    /**
     * Returns an array representing which tiles are which color.
     *
     * White tiles are represented by {@code true}, and Black tiles are represented by {@code false}.
     *
     * If a tile is empty, its state is not guaranteed.
     *
     * @return a {@code boolean[][]} representing the colors of the tiles.
     */
    public boolean[][] getColor() {
        return color;
    }

}
