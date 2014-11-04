<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Mongo</title>
    <meta name="layout" content="none">
</head>

<body>
<g:if test="${session.username}">
    Welcome ${session.username} <a href="/logout">Logout</a> | <a href="/newpost">New Post</a>

    <p>
</g:if>

<h1>My Blog</h1>

<g:each in="${posts}" var="post">
    <h2><a href="/post/${post["permalink"]}">${post["title"]}</a></h2>
    Posted ${post["date"]} <i>By ${post["author"]}</i><br>
    Comments:
    ${post['comments'] ? post['comments'].size() : 0}

    <a href="/post/${post["permalink"]}">${numComments}</a>
    <hr>
    ${post["body"] ?: ''}
    <p>

    <p>
        <em>Filed Under</em>:
    <g:if test="${post["tags"]}">
        ${post["tags"].join(", ")}
    </g:if>

    <p>
</g:each>
</body>
</html>