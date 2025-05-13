<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="email.confirmation.title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h2><fmt:message key="email.confirmation.title" /></h2>
    <p><fmt:message key="email.confirmation.message" /></p>
    <p><fmt:message key="email.spam" /></p>
    <p><a href="login.jsp"><fmt:message key="login.link" /></a></p>
</div>
</body>
</html>