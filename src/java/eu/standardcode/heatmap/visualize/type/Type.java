package eu.standardcode.heatmap.visualize.type;

import eu.standardcode.heatmap.model.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public abstract class Type {

    final List<Class<? extends MouseEvent>> list;

    Type() {
        list = new ArrayList<>(1);
    }

    public List<Class<? extends MouseEvent>> getClasses() {
        return list;
    }
}
