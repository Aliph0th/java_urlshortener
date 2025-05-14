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
    <style>
        .profile-container {
            max-width: 500px;
            margin: 60px auto;
            padding: 30px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .profile-title {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
        }
        .avatar-preview {
            text-align: center;
            margin-bottom: 30px;
        }
        .avatar {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #007bff;
            padding: 3px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #2c3e50;
            font-weight: 500;
        }
        .form-group input[type="text"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1em;
            transition: border-color 0.3s;
        }
        .form-group input[type="file"] {
            width: 100%;
            padding: 8px;
            border: 1px dashed #ddd;
            border-radius: 4px;
            background: #f8f9fa;
        }
        .form-group input:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 0 2px rgba(0,123,255,0.25);
        }
        .btn-submit {
            width: 100%;
            background-color: #007bff;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 4px;
            font-size: 1em;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .btn-submit:hover {
            background-color: #0056b3;
        }
        .language-switch {
            text-align: center;
            margin-top: 20px;
        }
        .language-switch button {
            background-color: transparent;
            border: 1px solid #007bff;
            color: #007bff;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.3s;
        }
        .language-switch button:hover {
            background-color: #007bff;
            color: white;
        }
    </style>
</head>
<body>
<div class="profile-container">
    <h2 class="profile-title"><fmt:message key="edit.profile.title" /></h2>
    <div class="avatar-preview">
        <img src="${not empty user.avatarUrl ? user.avatarUrl : '/images/default-avatar.png'}"
             alt="Current Avatar"
             class="avatar">
    </div>

    <form action="${pageContext.request.contextPath}/profile/edit" method="POST" enctype="multipart/form-data">
        <div class="form-group">
            <label for="newUsername"><fmt:message key="username.placeholder" /></label>
            <input type="text" id="newUsername" name="newUsername" value="${user.username}" required>
        </div>

        <div class="form-group">
            <label for="newAvatar"><fmt:message key="avatar" /></label>
            <input type="file" id="newAvatar" name="newAvatar" accept="image/*">
        </div>

        <button type="submit" class="btn-submit"><fmt:message key="save.button" /></button>
    </form>

    <div class="language-switch">
        <form action="${pageContext.request.contextPath}/change-language" method="post">
            <input type="hidden" name="lang" value="${lang == 'ru' ? 'en' : 'ru'}" />
            <button type="submit">${lang == 'ru' ? 'EN' : 'RU'}</button>
        </form>
    </div>
</div>
</body>
</html>
