package eu.standardcode.heatmap.visualize.color;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class GrayScale extends ColorScheme {

    double byteRate;

    @Override
    public void setMax(int max) {
        byteRate = 256.0 / max;
    }

    @Override
    public int getColor(int count) {
        count *= byteRate;
        return count | count << 8 | count << 16;
    }
}
