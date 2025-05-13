<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><fmt:message key="app.title" /></title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css">
</head>
<body class="contact-page">
<div class="contact-wrapper">
  <h2><fmt:message key="contacts.title" /></h2>
  <form action="${pageContext.request.contextPath}/change-language" method="post" style="float: right;">
    <input type="hidden" name="lang" value="${lang == 'ru' ? 'en' : 'ru'}" />
    <button type="submit">${lang == 'ru' ? 'EN' : 'RU'}</button>
  </form>
  <form action="${pageContext.request.contextPath}/contacts/add" method="post">
    <div class="contact-form-group">
      <input type="text" name="name" placeholder="<fmt:message key='name.placeholder' />" required>
      <input type="text" name="phone" placeholder="<fmt:message key='phone.placeholder' />" required>
      <input type="email" name="email" placeholder="<fmt:message key='email.placeholder' />" required>
      <button type="submit" class="btn-contact-add"><fmt:message key="add.button" /></button>
    </div>
  </form>
  <c:if test="${not empty contacts}">
    <table class="contact-table">
      <thead>
      <tr>
        <th><fmt:message key="name.placeholder" /></th>
        <th><fmt:message key="phone.placeholder" /></th>
        <th><fmt:message key="email.placeholder" /></th>
        <th><fmt:message key="actions" /></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="contact" items="${contacts}">
        <tr>
          <td>${contact.name}</td>
          <td>${contact.phone}</td>
          <td>${contact.email}</td>
          <td>
            <form action="${pageContext.request.contextPath}/contacts/delete" method="post" style="display:inline;">
              <input type="hidden" name="contactId" value="${contact.id}">
              <button type="submit" class="btn-contact-delete"><fmt:message key="delete.button" /></button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <c:if test="${totalPages > 1}">
      <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="i">
          <a href="?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
        </c:forEach>
      </div>
    </c:if>
  </c:if>
</div>
</body>
</html>
