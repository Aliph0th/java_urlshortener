<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><fmt:message key="register.title" /></title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
  <h1><fmt:message key="register.title" /></h1>
  <p><fmt:message key="register.subtitle" /></p>
  <c:if test="${not empty error}">
    <div id="error" style="color:red;">${error}</div>
  </c:if>
  <form id="registerForm" action="/register" method="POST">
    <input type="text" name="username" placeholder="<fmt:message key="username.placeholder" />" required>
    <input type="email" name="email" placeholder="<fmt:message key="email.placeholder" />" required>
    <input type="password" name="password" placeholder="<fmt:message key="password.placeholder" />" required>
    <input type="password" name="repeatPassword" placeholder="<fmt:message key="repeat.password" />" required>
    <button type="submit"><fmt:message key="register.button" /></button>
  </form>
  <p class="already-account"><fmt:message key="already.account" /> <a href="./login.jsp"><fmt:message key="login.link" /></a></p>
</div>
<script>
  document.getElementById("registerForm").addEventListener("submit", function(e){
    let errorDiv = document.getElementById("error");
    if (!errorDiv) {
      errorDiv = document.createElement("div");
      errorDiv.id = "error";
      errorDiv.style.color = "red";
      this.prepend(errorDiv);
    }
    errorDiv.textContent = "";
    const password = this.password.value;
    const repeatPassword = this.repeatPassword.value;
    if(password !== repeatPassword){
      errorDiv.textContent = "<fmt:message key="password.mismatch" />";
      e.preventDefault();
    }
  });
</script>
</body>
</html>
