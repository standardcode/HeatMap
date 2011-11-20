package eu.standardcode.heatmap.visualize.motif;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class Stars extends Motif {

    @Override
    protected void createPixels(int[][] counts, int[][] pixels, int width, int height) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (counts[row][col] > 0) {
                    int color = cs.getColor(counts[row][col]);
                    int pale = ((color & 0xff0000 >> 1) & 0xff0000) | ((color & 0x00ff00 >> 1) & 0x00ff00) | ((color & 0x0000ff >> 1) & 0x0000ff);
                    pixels[row > 0 ? row - 1 : row][col] += pale;
                    pixels[row][col < width - 1 ? col + 1 : col] += pale;
                    pixels[row < height - 1 ? row + 1 : row][col] += pale;
                    pixels[row][col > 0 ? col - 1 : col] += pale;
                    pixels[row][col] += color;
                }

            }
        }
    }
}
