package eu.standardcode.heatmap.visualize.type;

import eu.standardcode.heatmap.model.Mousedown;
import eu.standardcode.heatmap.model.Mouseup;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class Click extends Type {

    public Click() {
        list.add(Mousedown.class);
        list.add(Mouseup.class);
    }
}
