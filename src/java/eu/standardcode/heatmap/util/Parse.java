package eu.standardcode.heatmap.util;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class Parse {

    private Parse() {
    }

    public static int parseInt(String integer) {
        if (integer == null) {
            return 0;
        }
        try {
            return Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
