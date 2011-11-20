package eu.standardcode.heatmap.visualize.color;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public abstract class ColorScheme {

    public void setMax(int max) {
    }

    public abstract int getColor(int count);
}
