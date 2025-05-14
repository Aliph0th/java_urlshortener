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
    <style>
        .auth-container {
            max-width: 400px;
            margin: 60px auto;
            padding: 30px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .auth-title {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .confirmation-message {
            color: #2c3e50;
            margin-bottom: 15px;
            line-height: 1.6;
        }
        .spam-notice {
            color: #7f8c8d;
            font-size: 0.9em;
            margin-bottom: 20px;
        }
        .auth-link {
            display: inline-block;
            color: #007bff;
            text-decoration: none;
            padding: 10px 20px;
            border: 1px solid #007bff;
            border-radius: 4px;
            transition: all 0.3s ease;
        }
        .auth-link:hover {
            background-color: #007bff;
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="auth-container">
    <h2 class="auth-title"><fmt:message key="email.confirmation.title" /></h2>
    <p class="confirmation-message"><fmt:message key="email.confirmation.message" /></p>
    <p class="spam-notice"><fmt:message key="email.spam" /></p>
    <a href="login.jsp" class="auth-link"><fmt:message key="login.link" /></a>
</div>
</body>
</html>
