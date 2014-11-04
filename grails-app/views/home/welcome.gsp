<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Mongo</title>
    <meta name="layout" content="none">
</head>

<body>
Welcome ${session.username}
<p>
<ul>
    <li><a href="/">Goto Blog Home</a></li>
    <li>
        <a href="/logout">Logout</a>
    </li>
    <li>
        <a href="/newpost">Create a New Post</a>
    </li>
</ul>
</body>
</html>