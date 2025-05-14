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
            position: relative;
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
        .delete-btn {
            position: absolute;
            top: 15px;
            right: 15px;
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .delete-btn:hover {
            background-color: #c82333;
        }
        .toast {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #28a745;
            color: white;
            padding: 15px 25px;
            border-radius: 4px;
            display: none;
            z-index: 1000;
            animation: slideIn 0.3s ease-out;
        }
        @keyframes slideIn {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }
    </style>
</head>
<body>
    <nav class="nav-bar">
        <div class="nav-container">
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
                <a href="${pageContext.request.contextPath}/urls" class="nav-link active">My URLs</a>
                <a href="${pageContext.request.contextPath}/urls/create.jsp" class="nav-link">Shorten URL</a>
                <a href="${pageContext.request.contextPath}/profile" class="nav-link">Profile</a>
            </div>
        </div>
    </nav>

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
                            <li class="url-item" data-short-code="${url.shortCode}">
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
                                <button class="delete-btn" onclick="deleteUrl('${url.shortCode}')">Delete</button>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div id="toast" class="toast"></div>

    <script>
        function deleteUrl(shortCode) {
            if (confirm('Are you sure you want to delete this URL? This action cannot be undone.')) {
                fetch('${pageContext.request.contextPath}/urls/delete/' + shortCode, {
                    method: 'POST',
                })
                .then(response => {
                    if (response.ok) {
                        // Remove the URL item from the list
                        const urlItem = document.querySelector(`[data-short-code="\${shortCode}"]`);
                        if (urlItem) {
                            urlItem.remove();
                        }
                        showToast('URL deleted successfully');
                        
                        // If no more URLs, show the empty state
                        const urlList = document.querySelector('.url-list');
                        if (urlList && urlList.children.length === 0) {
                            location.reload();
                        }
                    } else {
                        throw new Error('Failed to delete URL');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showToast('Error deleting URL', true);
                });
            }
        }

        function showToast(message, isError = false) {
            const toast = document.getElementById('toast');
            toast.textContent = message;
            toast.style.backgroundColor = isError ? '#dc3545' : '#28a745';
            toast.style.display = 'block';
            
            setTimeout(() => {
                toast.style.display = 'none';
            }, 3000);
        }
    </script>
</body>
</html> 
