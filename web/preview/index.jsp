<%@page import="java.net.URI"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.File"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.List"%>
<%@page import="java.nio.file.Files"%>
<%@page import="eu.standardcode.heatmap.util.FindFilePattern"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Path"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    String getPath(Path startingDir, String patter, JspWriter out, String error) {
        String path = "";
        List<Path> files;
        try {
            if (startingDir == null) {
                out.println("Cannot find jQuery directory");
                return path;
            }
            files = new FindFilePattern(startingDir, patter).getFiles(3);
            if (files.isEmpty()) {
                out.println(error);
            } else {
                path = files.get(0).toString();
            }
        } catch (IOException e) {//return "" at least
        }
        return path;
    }

    String relativie(URI uri, String path) {
        return uri.relativize(new File(path).toURI()).getPath();
    }
%>
<%
    ServletContext sc = getServletConfig().getServletContext();
    Path path = new File(sc.getRealPath("jquery-ui/")).toPath();
    String jQueryUiCss = getPath(path, "jquery-ui*.css", out, "Cannot find jQuery UI CSS file.");
    String jQueryUiJs = getPath(path, "jquery-ui*.js", out, "Cannot find jQuery UI JavaScript file.");
    if (jQueryUiCss.isEmpty() || jQueryUiJs.isEmpty()) {
        return;
    }
    URI uri = new File("").toURI();
    jQueryUiCss = "../jquery-ui/" + relativie(uri, jQueryUiCss);
    jQueryUiJs = "../jquery-ui/" + relativie(uri, jQueryUiJs);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>HeatMap 0.2.0</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="<%=jQueryUiCss%>"/>
        <link  rel="stylesheet" type="text/css" href="toolbar.css"/>
        <script src="../jquery.js" type="text/javascript"></script>
        <script src="<%=jQueryUiJs%>" type="text/javascript"></script>
        <script src="toolbar.js" type="text/javascript"></script>
    </head>
    <body>
        <form name="options" id="options">
            <div id="basic">
                <label for="url">URL:</label><input type="text" name="url" id="url" value="" size="100" />
                <input type="submit" value="Go" />
                <input type="checkbox" id="render" checked="checked"/>
                <label for="render">Show heat map</label>
            </div>
            <div id="visual">
                <label for="type">Type:</label>
                <select name="type" id="type">
                    <option value="movepoints">Move points</option>
                    <option value="clicks">Clicks</option>
                </select>
                <label for="motif">Motif:</label>
                <select name="motif" id="motif">
                    <option value="star">Stars</option>
                    <option value="pixel">Pixels</option>
                </select>
                <label for="color">Colors:</label>
                <select name="color" id="color">
                    <option value="color">Colors</option>
                    <option value="gray">Gray scale</option>
                </select>
            </div>
            <div id="timeSelect">
                <fieldset>
                    <legend>
                        <input type="radio" name="timeType" id="dateRange"/>
                        <label for="dateRange">Date range</label>
                    </legend>
                    <label for="from">From:</label>
                    <input type="text" class="time" id="from" value="" />
                    <label for="to">To:</label>
                    <input type="text" class="time" id="to" value="" />
                </fieldset>
                <fieldset>
                    <legend>
                        <input type="radio" name="timeType" id="pointTime"/>
                        <label for="point">Point +/-</label>
                    </legend>
                    <label for="point">Date:</label>
                    <input type="text" class="time" id="point" value="" />
                    <label for="radius">Milliseconds:</label>
                    <input type="text" class="number" id="radius" value="" />
                </fieldset>
                <fieldset>
                    <legend>
                        <input type="radio" name="timeType" id="lastItems" checked="checked"/>
                        <label for="lastItems">Last</label>
                    </legend>
                    <input type="text" class="number" id="last" value="1000" />
                    <label for="last">points</label>
                </fieldset>
            </div>
            <div id="other">
                <label for="user">User ID:</label>
                <input type="text" class="number" id="user" name="user" value="" /><br/><br/>
                <input type="button" id="about" value="About"/>
            </div>
        </form>
        <div id="opacity"></div>
        <iframe width="100" height="100" src="startpage.html" name="page" id="page" title="Web page with heatmap cover"></iframe>
    <noframes>
        <noscript>
        <div>JavaScript is disabled on your browser.</div>
        </noscript>
        <h2>Frame Alert</h2>
        <p>This document is designed to be viewed using the frames feature. If you see this message, you are using a non-frame-capable web client.</p>
    </noframes>
    <div id="cover"></div>
</body>
</html>
