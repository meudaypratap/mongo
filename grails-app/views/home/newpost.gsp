<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Mongo</title>
    <meta name="layout" content="none">
</head>

<body>
<g:if test="${session.username}">
    Welcome ${session.username} <a href="/logout">Logout</a> | <a href="/">Blog Home</a>

    <p>
</g:if>
<form action="/newpost" method="POST">
    ${errors}
    <h2>Title</h2>
    <input type="text" name="subject" size="120" value="${subject ?: ''}"><br>

    <h2>Blog Entry
    </h2>
    <textarea name="body" cols="120" rows="20">${post ?: ''}</textarea><br>

    <h2>Tags</h2>
    Comma separated, please<br>
    <input type="text" name="tags" size="120" value="${tags ?: ''}"><br>

    <p>
        <input type="submit" value="Submit">
    </p>
</form>
</body>
</html>