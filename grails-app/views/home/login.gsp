<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>mongo</title>
    <meta name="layout" content="none">
</head>

<body>
Need to Create an account? <a href="/signup">Signup</a>

<p>

<h2>Login</h2>

<form method="post">
    <table>
        <tr>
            <td class="label">
                Username
            </td>
            <td>
                <input type="text" name="username" value="${username}">
            </td>
            <td class="error">
            </td>
        </tr>

        <tr>
            <td class="label">
                Password
            </td>
            <td>
                <input type="password" name="password" value="">
            </td>
            <td class="error">
                ${login_error}

            </td>
        </tr>

    </table>

    <input type="submit">
</form>
</body>
</html>