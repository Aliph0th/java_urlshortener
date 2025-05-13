<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="edit.profile.title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h2><fmt:message key="edit.profile.title" /></h2>
    <div class="avatar-preview">
        <img src="${not empty user.avatarUrl ? user.avatarUrl : '/images/default-avatar.png'}"
             alt="Current Avatar"
             class="avatar">
    </div>

    <form action="${pageContext.request.contextPath}/profile/edit" method="POST" enctype="multipart/form-data">
        <div class="form-group">
            <label for="newUsername"><fmt:message key="username.placeholder" />:</label>
            <input type="text" id="newUsername" name="newUsername" value="${user.username}" required>
        </div>

        <div class="form-group">
            <label for="newAvatar"><fmt:message key="avatar" />:</label>
            <input type="file" id="newAvatar" name="newAvatar" accept="image/*">
        </div>

        <button type="submit" class="btn-submit"><fmt:message key="save.button" /></button>
    </form>

    <form action="${pageContext.request.contextPath}/change-language" method="post" style="margin-top: 20px;">
        <input type="hidden" name="lang" value="${lang == 'ru' ? 'en' : 'ru'}" />
        <button type="submit">${lang == 'ru' ? 'EN' : 'RU'}</button>
    </form>
</div>
</body>
</html>