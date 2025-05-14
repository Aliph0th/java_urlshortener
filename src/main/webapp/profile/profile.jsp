<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytags" uri="http://mytags" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="profile.title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .nav-bar {
            background-color: #343a40;
            padding: 1rem;
            margin-bottom: 2rem;
        }
        .nav-container {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .nav-links {
            display: flex;
            gap: 20px;
        }
        .nav-links a {
            color: white;
            text-decoration: none;
            padding: 0.5rem 1rem;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .nav-links a:hover {
            background-color: #495057;
        }
        .nav-links a.active {
            background-color: #007bff;
        }
        .btn-signout {
            background-color: #dc3545;
            color: white;
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
        }
        .btn-signout:hover {
            background-color: #c82333;
        }
        .action-buttons {
            margin-top: 20px;
            display: flex;
            gap: 10px;
            align-items: center;
        }
    </style>
</head>
<body>
    <nav class="nav-bar">
        <div class="nav-container">
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
                <a href="${pageContext.request.contextPath}/urls" class="nav-link">My URLs</a>
                <a href="${pageContext.request.contextPath}/urls/create.jsp" class="nav-link">Shorten URL</a>
                <a href="${pageContext.request.contextPath}/profile" class="nav-link active">Profile</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <h2><fmt:message key="profile.title" /></h2>
        <div class="profile-info">
            <mytags:avatar 
                src="${not empty user.avatarUrl ? user.avatarUrl : '/images/default-avatar.png'}"
                alt="Avatar"
                className="avatar"
            />
            <p><strong><fmt:message key="username.placeholder" />:</strong> ${user.username}</p>
            <p><strong><fmt:message key="email.placeholder" />:</strong> ${user.email}</p>
        </div>
        
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/profile/edit" class="btn-edit">
                <fmt:message key="edit.profile.title" />
            </a>
            
            <form action="${pageContext.request.contextPath}/signout" method="post" style="display: inline;">
                <button type="submit" class="btn-signout">
                    <fmt:message key="signout.button" />
                </button>
            </form>
        </div>

        <form action="${pageContext.request.contextPath}/change-language" method="post" style="margin-top: 20px;">
            <input type="hidden" name="lang" value="${lang == 'ru' ? 'en' : 'ru'}" />
            <button type="submit">${lang == 'ru' ? 'EN' : 'RU'}</button>
        </form>
    </div>
</body>
</html>
