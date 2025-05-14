<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <title>Create Short URL</title>
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
        .url-form {
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .error-message {
            color: #dc3545;
            margin-bottom: 15px;
        }
        .btn-submit {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-submit:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <nav class="nav-bar">
        <div class="nav-container">
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
                <a href="${pageContext.request.contextPath}/urls" class="nav-link">My URLs</a>
                <a href="${pageContext.request.contextPath}/urls/create.jsp" class="nav-link active">Shorten URL</a>
                <a href="${pageContext.request.contextPath}/profile" class="nav-link">Profile</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="url-form">
            <h2>Create Short URL</h2>
            
            <c:if test="${not empty error}">
                <div class="error-message">
                    ${error}
                </div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/shorten" method="post">
                <div class="form-group">
                    <label for="url">Enter URL to shorten:</label>
                    <input type="url" id="url" name="url" required 
                           placeholder="https://example.com"
                           pattern="https?://.*"
                    >
                </div>
                
                <button type="submit" class="btn-submit">Shorten URL</button>
            </form>
            
            <p style="margin-top: 20px;">
                <a href="${pageContext.request.contextPath}/urls">View your shortened URLs</a>
            </p>
        </div>
    </div>
</body>
</html> 
