<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Number Doubler</title>
    <style>
        .container {
            width: 50%;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        input[type="number"] {
            padding: 5px;
            margin-left: 10px;
        }
        button {
            padding: 8px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .result {
            margin-top: 20px;
            padding: 10px;
            background-color: #f0f0f0;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Number Doubler</h2>
        
        <form action="double" method="post">
            <div class="form-group">
                <label for="number">Enter a number:</label>
                <input type="number" id="number" name="number" required>
            </div>
            <button type="submit">Double it!</button>
        </form>

        <%-- Display result if available --%>
        <% 
            String result = (String) request.getAttribute("result");
            String originalNumber = (String) request.getAttribute("originalNumber");
            if (result != null && originalNumber != null) {
        %>
            <div class="result">
                <p>Original number: <%= originalNumber %></p>
                <p>Doubled result: <%= result %></p>
            </div>
        <% } %>
    </div>
</body>
</html> 
