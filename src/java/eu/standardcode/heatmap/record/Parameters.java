package eu.standardcode.heatmap.record;

import eu.standardcode.heatmap.util.ServletParameters;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
class Parameters extends ServletParameters {

    private final URL referrer;
    private final Cookie[] cookies;

    Parameters(HttpServletRequest request) throws MalformedURLException {
        super(request);
        referrer = new URL(request.getHeader("referer"));
        cookies = request.getCookies();
    }

    public URL getReferrer() {
        return referrer;
    }

    public String getUserCookie() {
        for (Cookie c : cookies) {
            if ("hm".equals(c.getName())) {
                return c.getValue();
            }
        }
        return "";
    }
}
