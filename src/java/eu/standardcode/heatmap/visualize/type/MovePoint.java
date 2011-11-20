package eu.standardcode.heatmap.visualize.type;

import eu.standardcode.heatmap.model.Mousemove;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class MovePoint extends Type {

    public MovePoint() {
        list.add(Mousemove.class);
    }
}
