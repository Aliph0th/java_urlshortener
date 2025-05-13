<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="profile.title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h2><fmt:message key="profile.title" /></h2>
    <div class="profile-info">
        <img src="${not empty user.avatarUrl ? user.avatarUrl : '/images/default-avatar.png'}"
             alt="Avatar"
             class="avatar"
        >
        <p><strong><fmt:message key="username.placeholder" />:</strong> ${user.username}</p>
        <p><strong><fmt:message key="email.placeholder" />:</strong> ${user.email}</p>
    </div>
    <a href="${pageContext.request.contextPath}/profile/edit" class="btn-edit"><fmt:message key="edit.profile.title" /></a>

    <form action="${pageContext.request.contextPath}/change-language" method="post" style="margin-top: 20px;">
        <input type="hidden" name="lang" value="${lang == 'ru' ? 'en' : 'ru'}" />
        <button type="submit">${lang == 'ru' ? 'EN' : 'RU'}</button>
    </form>
</div>
</body>
</html>