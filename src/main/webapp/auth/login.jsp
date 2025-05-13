<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="login.title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h1><fmt:message key="login.title" /></h1>
    <p><fmt:message key="login.subtitle" /></p>
    <c:if test="${not empty error}">
        <div id="error" style="color:red;">${error}</div>
    </c:if>
    <form action="/login" method="POST">
        <input type="text" name="username" placeholder="<fmt:message key="username.placeholder" />" required>
        <input type="password" name="password" placeholder="<fmt:message key="password.placeholder" />" required>
        <button type="submit"><fmt:message key="login.link" /></button>
    </form>
    <p class="no-account"><fmt:message key="no.account" /> <a href="./register.jsp"><fmt:message key="register.link" /></a></p>
</div>
</body>
</html>