

import java.awt.Color;

/**
 * Project: Seam Carving
 * Author: CtheSky
 * Create Date: 2017/2/2
 * Description:
 * All rights reserved.
 */
public class SeamCarver {
    private int width;
    private int height;
    private int[][] colors;

    public SeamCarver(Picture picture) {
        // create a seam carver object based on the given picture
        if (picture == null) throw new NullPointerException();

        width = picture.width();
        height = picture.height();
        colors = new int[height][width];

        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                colors[row][col] = picture.get(col, row).getRGB();
    }

    public Picture picture() {
        // current picture
        Picture picture = new Picture(width, height);

        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                picture.set(col, row, new Color(colors[row][col]));

        return picture;
    }

    public int width() {
        // width of current picture
        return width;
    }

    public int height() {
        // height of current picture
        return height;
    }

    public  double energy(int x, int y) {
        // energy of pixel at column x and row y
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IndexOutOfBoundsException();

        if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
            return 1000;

        int top = colors[y - 1][x];
        int down = colors[y + 1][x];
        int left = colors[y][x - 1];
        int right = colors[y][x + 1];

        double deltaX2 = Math.pow(getRed(right) - getRed(left), 2) +
                Math.pow(getGreen(right) - getGreen(left), 2) +
                Math.pow(getBlue(right) - getBlue(left), 2);
        double deltaY2 = Math.pow(getRed(down) - getRed(top), 2) +
                Math.pow(getGreen(down) - getGreen(top), 2) +
                Math.pow(getBlue(down) - getBlue(top), 2);

        return Math.sqrt(deltaX2 + deltaY2);
    }

    public int[] findHorizontalSeam() {
        // sequence of indices for horizontal seam
        int[][] edgeTo = new int[height][width];
        double[][] distTo = new double[height][width];
        resetDist(distTo);

        for (int row = 0; row < height; row++)
            distTo[row][0] = 1000;

        for (int col = 0; col < width - 1; col++)
            for (int row = 0; row < height; row++)
                relaxHorizontal(row, col, edgeTo, distTo);

        double minDist = Double.MAX_VALUE;
        int minRow = 0;
        for (int row = 0; row < height; row++)
            if (minDist > distTo[row][width - 1]) {
                minDist = distTo[row][width - 1];
                minRow = row;
            }

        int[] indices = new int[width];
        for (int col = width - 1, row = minRow; col >= 0; col--) {
            indices[col] = row;
            row -= edgeTo[row][col];
        }
        indices[0]=indices[1];


        return indices;
    }

    public int[] findVerticalSeam() {
        // sequence of indices for vertical seam
        int[][] edgeTo = new int[height][width];
        double[][] distTo = new double[height][width];
        resetDist(distTo);

        for (int col = 0; col < width; col++)
            distTo[0][col] = 1000;

        for (int row = 0; row < height - 1; row++)
            for (int col = 0; col < width; col++)
                relaxVertical(row, col, edgeTo, distTo);

        double minDist = Double.MAX_VALUE;
        int minCol = 0;
        for (int col = 0; col < width; col++)
            if (minDist > distTo[height - 1][col]) {
                minDist = distTo[height - 1][col];
                minCol = col;
            }

        int[] indices = new int[height];
        for (int row = height -1, col = minCol; row >= 0; row--) {
            indices[row] = col;
            col -= edgeTo[row][col];
        }
        indices[0]=indices[1];

        return indices;
    }

    public void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from current picture
        if (seam == null) throw new NullPointerException();
        if (seam.length != width || height <= 1)
            throw new IllegalArgumentException();

        for (int i = 0; i < seam.length; i++)
            if (seam[i] < 0 || seam[i] >= height ||
                    i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException();

        for (int col = 0; col < width; col++)
            for (int row = seam[col]; row < height - 1; row++)
                colors[row][col] = colors[row + 1][col];

        height--;
    }

    public void removeVerticalSeam(int[] seam) {
        // remove vertical seam from current picture
        if (seam == null) throw new NullPointerException();
        if (seam.length != height || width <= 1)
            throw new IllegalArgumentException();

        for (int i = 0; i < seam.length; i++)
            if (seam[i] < 0 || seam[i] >= width ||
                    i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException();

        for (int row = 0; row < height; row++)
            for (int col = seam[row]; col < width - 1; col++)
                colors[row][col] = colors[row][col + 1];

        width--;
    }

    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    private void relaxVertical(int row, int col, int[][] edgeTo, double[][] distTo) {
        int nextRow = row + 1;
        for (int i = -1; i <= 1; i++) {
            int nextCol = col + i;
            if (nextCol < 0 || nextCol >= width) continue;
            if (distTo[nextRow][nextCol] > distTo[row][col] + energy(nextCol, nextRow)) {
                distTo[nextRow][nextCol] = distTo[row][col] + energy(nextCol, nextRow);
                edgeTo[nextRow][nextCol] = i;
            }
        }
    }

    private void relaxHorizontal(int row, int col, int[][] edgeTo, double[][] distTo) {
        int nextCol = col + 1;
        for (int i = -1; i <= 1; i++) {
            int nextRow = row + i;
            if (nextRow < 0 || nextRow >= height) continue;
            if (distTo[nextRow][nextCol] > distTo[row][col]  + energy(nextCol, nextRow)) {
                distTo[nextRow][nextCol] = distTo[row][col]  + energy(nextCol, nextRow);
                edgeTo[nextRow][nextCol] = i;
            }
        }
    }

    private void resetDist(double[][] distTo) {
        for (int i = 0; i < distTo.length; i++)
            for (int j = 0; j < distTo[i].length; j++)
                distTo[i][j] = Double.MAX_VALUE;
    }
}
