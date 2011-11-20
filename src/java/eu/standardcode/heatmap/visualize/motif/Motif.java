package eu.standardcode.heatmap.visualize.motif;

import eu.standardcode.heatmap.visualize.color.ColorScheme;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class Motif {

    ColorScheme cs;

    public void setColorScheme(ColorScheme cs) {
        this.cs = cs;
    }

    public int[][] getPixels(int[][] counts) {
        int width = counts[0].length;
        int height = counts.length;
        int[][] b = new int[height][width];
        createPixels(counts, b, width, height);
        return b;
    }

    protected void createPixels(int[][] counts, int[][] pixels, int width, int height) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixels[row][col] = cs.getColor(counts[row][col]);
            }
        }
    }
}
