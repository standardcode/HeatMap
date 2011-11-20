package eu.standardcode.heatmap.visualize;

import eu.standardcode.heatmap.util.Parse;
import eu.standardcode.heatmap.util.ServletParameters;
import eu.standardcode.heatmap.visualize.color.ColorScale;
import eu.standardcode.heatmap.visualize.color.ColorScheme;
import eu.standardcode.heatmap.visualize.color.GrayScale;
import eu.standardcode.heatmap.visualize.motif.Motif;
import eu.standardcode.heatmap.visualize.motif.Stars;
import eu.standardcode.heatmap.visualize.time.Between;
import eu.standardcode.heatmap.visualize.time.Last;
import eu.standardcode.heatmap.visualize.time.Point;
import eu.standardcode.heatmap.visualize.time.TimeScope;
import eu.standardcode.heatmap.visualize.type.Click;
import eu.standardcode.heatmap.visualize.type.MovePoint;
import eu.standardcode.heatmap.visualize.type.Type;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
class Options extends ServletParameters {

    private final URL url;

    Options(HttpServletRequest request) throws MalformedURLException {
        super(request);
        this.url = new URL(get("url"));
    }

    public URL getUrl() {
        return url;
    }

    public int getUserId() {
        return Parse.parseInt(get("user"));
    }

    public Motif getMotif() {
        String type = get("motif");
        Motif t = null;
        if (type != null) {
            switch (type) {
                case "pixel":
                    t = new Motif();
                    break;
                case "star":
                    t = new Stars();
                    break;
            }
        }
        if (t == null) {
            t = new Motif();
        }
        return t;
    }

    public ColorScheme getColorScheme() {
        String type = get("color");
        ColorScheme t = null;
        if (type != null) {
            switch (type) {
                case "gray":
                    t = new GrayScale();
                    break;
                case "color":
                    t = new ColorScale();
                    break;
            }
        }
        if (t == null) {
            t = new GrayScale();
        }
        return t;
    }

    public Type getType() {
        String type = get("type");
        Type t = null;
        if (type != null) {
            switch (type) {
                case "movepoints":
                    t = new MovePoint();
                    break;
                case "clicks":
                    t = new Click();
                    break;
            }
        }
        if (t == null) {
            t = new MovePoint();
        }
        return t;
    }

    public TimeScope getTimeScope() {
        long[] time = time("from", "to");
        if (time != null) {
            return new Between(time[0], time[1]);
        }
        time = time("point", "radius");
        if (time != null) {
            return new Point(time[0], time[1]);
        }
        String last = get("last");
        if (last != null) {
            try {
                int l = Integer.parseInt(last);
                if (l > 0) {
                    return new Last(l);
                }
            } catch (NumberFormatException e) {
            }
        }
        return new Last(100);
    }

    private long[] time(String a, String b) {
        String from = get(a);
        String to = get(b);
        if (from != null && to != null) {
            try {
                long f = Long.parseLong(from);
                long t = Long.parseLong(to);
                return new long[]{f, t};
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }
}
