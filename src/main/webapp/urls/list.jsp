<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://bsujava.com/functions" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <title>Your Shortened URLs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .urls-container {
            max-width: 800px;
            margin: 40px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .url-list {
            list-style: none;
            padding: 0;
        }
        .url-item {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        .url-item:last-child {
            border-bottom: none;
        }
        .url-item .short-url {
            font-size: 18px;
            color: #007bff;
            margin-bottom: 5px;
        }
        .url-item .original-url {
            color: #6c757d;
            word-break: break-all;
        }
        .url-item .stats {
            font-size: 14px;
            color: #28a745;
            margin-top: 5px;
        }
        .url-item .created-at {
            font-size: 12px;
            color: #6c757d;
        }
        .create-new {
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            margin-bottom: 20px;
        }
        .create-new:hover {
            background-color: #0056b3;
        }
        .no-urls {
            text-align: center;
            padding: 40px;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="urls-container">
            <h2>Your Shortened URLs</h2>
            
            <a href="${pageContext.request.contextPath}/urls/create.jsp" class="create-new">
                Create New Short URL
            </a>
            
            <c:choose>
                <c:when test="${empty urls}">
                    <div class="no-urls">
                        <p>You haven't created any shortened URLs yet.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="url-list">
                        <c:forEach items="${urls}" var="url">
                            <li class="url-item">
                                <div class="short-url">
                                    <a href="${pageContext.request.contextPath}/s/${url.shortCode}" target="_blank">
                                        ${pageContext.request.contextPath}/s/${url.shortCode}
                                    </a>
                                </div>
                                <div class="original-url">
                                    Original: <a href="${url.originalUrl}" target="_blank">${url.originalUrl}</a>
                                </div>
                                <div class="stats">
                                    Clicks: ${url.clickCount}
                                </div>
                                <div class="created-at">
                                    Created: ${fn:formatDateTime(url.createdAt)}
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html> 
