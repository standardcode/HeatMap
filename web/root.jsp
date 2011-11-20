<% int port = request.getLocalPort();%>
var root = '<%= request.getScheme() + "://" + request.getLocalName() + ((port == 80) ? "" : ':' + String.valueOf(port))%>';