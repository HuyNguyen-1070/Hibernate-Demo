<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="t"   tagdir="/WEB-INF/tags"  %>
<!doctype html>
<html lang="${empty lang ? 'vi' : lang}">
<head>
  <meta charset="utf-8">
  <title><fmt:message key="app.title"/></title>
</head>
<body>

<fmt:setLocale value="${empty lang ? 'vi' : lang}"/>
<fmt:setBundle basename="app_i18n.messages"/>

<%@ include file="/WEB-INF/views/_layout.jspf" %>

<div class="container my-4">
  <h4 class="mb-3"><fmt:message key="categories.title"/></h4>

  <c:choose>
    <c:when test="${empty categories}">
      <div class="alert alert-info"><fmt:message key="categories.empty"/></div>
    </c:when>
    <c:otherwise>
      <ul class="list-group bg-white rounded-4">
        <c:forEach var="citem" items="${categories}">
          <li class="list-group-item d-flex justify-content-between align-items-center">
            <a class="text-decoration-none" href="<t:urlWithLang value='/home?cat=${citem.id.productCategoryId}'/>"> ${citem.categoryName} </a>
            </li>
        </c:forEach>
      </ul>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>