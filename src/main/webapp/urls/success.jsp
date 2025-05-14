<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <title>URL Shortened Successfully</title>
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
        .success-container {
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            text-align: center;
        }
        .short-url {
            margin: 20px 0;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 4px;
            font-size: 18px;
            word-break: break-all;
        }
        .copy-btn {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin: 10px 0;
        }
        .copy-btn:hover {
            background-color: #218838;
        }
        .navigation {
            margin-top: 30px;
        }
        .navigation a {
            margin: 0 10px;
            color: #007bff;
            text-decoration: none;
        }
        .navigation a:hover {
            text-decoration: underline;
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
                <a href="${pageContext.request.contextPath}/profile" class="nav-link">Profile</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="success-container">
            <h2>URL Shortened Successfully!</h2>
            
            <div class="short-url">
                <p>Your shortened URL:</p>
                <a href="${baseUrl}/s/${shortUrl.shortCode}" target="_blank">
                    ${baseUrl}/s/${shortUrl.shortCode}
                </a>
            </div>
            
            <button class="copy-btn" onclick="copyToClipboard()">
                Copy to Clipboard
            </button>
            
            <div class="navigation">
                <a href="${pageContext.request.contextPath}/urls/create.jsp">Create Another</a>
                <a href="${pageContext.request.contextPath}/urls">View All URLs</a>
            </div>
        </div>
    </div>
    
    <script>
        function copyToClipboard() {
            const url = '${baseUrl}/s/${shortUrl.shortCode}';
            navigator.clipboard.writeText(url).then(function() {
                alert('URL copied to clipboard!');
            }).catch(function(err) {
                console.error('Failed to copy URL: ', err);
            });
        }
    </script>
</body>
</html> 
