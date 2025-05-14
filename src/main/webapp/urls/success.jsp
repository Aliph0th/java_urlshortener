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
