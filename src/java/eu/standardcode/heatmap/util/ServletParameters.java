package eu.standardcode.heatmap.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class ServletParameters {

    private final Map<String, String[]> param;

    public ServletParameters(HttpServletRequest request) {
        param = new HashMap<>(request.getParameterMap());
    }

    public String get(String parameter) {
        String[] val;
        return (val = param.get(parameter)) != null && val.length > 0 ? val[0] : null;
    }
}
