package eu.standardcode.heatmap.visualize.color;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class ColorScale extends ColorScheme {

    double intRate;

    @Override
    public void setMax(int max) {
        intRate = (double) 0x00700000 / max;
    }

    @Override
    public int getColor(int count) {
        count *= intRate;
        return Integer.reverse(count);
    }
}
