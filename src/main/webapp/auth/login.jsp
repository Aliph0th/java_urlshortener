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
    <style>
        .auth-container {
            max-width: 400px;
            margin: 60px auto;
            padding: 30px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .auth-title {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 10px;
        }
        .auth-subtitle {
            text-align: center;
            color: #7f8c8d;
            margin-bottom: 30px;
            font-size: 0.95em;
        }
        .auth-form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        .form-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        .form-group input {
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1em;
            transition: border-color 0.3s;
        }
        .form-group input:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 0 2px rgba(0,123,255,0.25);
        }
        .auth-button {
            background-color: #007bff;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 4px;
            font-size: 1em;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .auth-button:hover {
            background-color: #0056b3;
        }
        .auth-links {
            text-align: center;
            margin-top: 20px;
            color: #7f8c8d;
        }
        .auth-links a {
            color: #007bff;
            text-decoration: none;
        }
        .auth-links a:hover {
            text-decoration: underline;
        }
        .error-message {
            background-color: #fee;
            color: #dc3545;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
            font-size: 0.9em;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="auth-container">
    <h1 class="auth-title"><fmt:message key="login.title" /></h1>
    <p class="auth-subtitle"><fmt:message key="login.subtitle" /></p>
    
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    
    <form action="/login" method="POST" class="auth-form">
        <div class="form-group">
            <input type="text" name="username" 
                   placeholder="<fmt:message key="username.placeholder" />" 
                   required>
        </div>
        <div class="form-group">
            <input type="password" name="password" 
                   placeholder="<fmt:message key="password.placeholder" />" 
                   required>
        </div>
        <button type="submit" class="auth-button">
            <fmt:message key="login.link" />
        </button>
    </form>
    
    <div class="auth-links">
        <fmt:message key="no.account" /> 
        <a href="./register.jsp"><fmt:message key="register.link" /></a>
    </div>
</div>
</body>
</html>
