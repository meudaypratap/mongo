<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Linksharing</title>
    <meta name="layout" content="none">
</head>

<body>
<g:if test="${session.username}">
    Welcome ${session.username} <a href="/logout">Logout</a> | <a href="/newpost">New Post</a>

    <p>
</g:if>
<a href="/">Blog Home</a><br><br>

<h2>${post["title"]}</h2>
Posted ${post["date"]}<i>By ${post["author"]}</i><br>
<hr>
${post["body"]}
<p>
    <em>Filed Under</em>:
<g:if test="${post["tags"]}">
    ${post["tags"].join(", ")}
</g:if>

<p>
    Comments:
<ul>

    <g:if test="${post["comments"]}">
        <g:each in="${post["comments"]}" var="comment">
            Author: ${comment["author"]}<br>
            <br>
            ${comment["body"]}<br>
            <hr>
        </g:each>
    </g:if>
    <h3>Add a comment</h3>

    <form action="/newcomment" method="POST">
        <input type="hidden" name="permalink" value="${post["permalink"]}"/>
        <b>Name</b> (required)<br>
        <input type="text" name="commentName" size="60" value="${comment["name"]}"><br>
        <b>Email</b> (optional)<br>
        <input type="text" name="commentEmail" size="60" value="${comment["email"]}"><br>
        <b>Comment</b><br>
        <textarea name="commentBody" cols="60" rows="10">${comment["body"]}</textarea><br>
        <input type="submit" value="Submit">
    </form>
</ul>
</body>
</html>