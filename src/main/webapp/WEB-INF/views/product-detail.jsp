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
  <a class="btn btn-link px-0" href="<t:urlWithLang value='/home?lang=${lang}'/>">← <fmt:message key="btn.back.home"/></a>

  <c:choose>
    <c:when test="${p == null}">
      <div class="alert alert-danger"><fmt:message key="product.notfound"/></div>
    </c:when>
    <c:otherwise>
      <!--  Các nút cập nhật và xóa -->
      <div class="d-flex justify-content-end gap-2 mb-3">
        <a class="btn btn-primary" href="<t:urlWithLang value='/products/edit?id=${p.product.productId}'/>">
          <fmt:message key="btn.edit"/>
        </a>
        <form method="post" action="<t:urlWithLang value='/products/delete'/>" class="d-inline">
          <input type="hidden" name="id" value="${p.product.productId}"/>
          <button type="submit" class="btn btn-danger"
                  onclick="return confirm('<fmt:message key="product.delete.confirm"/>')">
            <fmt:message key="btn.delete"/>
          </button>
        </form>
      </div>

      <div class="card p-4">
        <h3 class="mb-1">${p.productName}</h3>
        <div class="text-secondary mb-3">
          <fmt:message key="product.category"/>:
          <c:choose>
            <c:when test="${not empty p.product.productCategory.currentLanguageName}">
              ${p.product.productCategory.currentLanguageName}
            </c:when>
            <c:otherwise>
              N/A
            </c:otherwise>
          </c:choose>
          &nbsp;|&nbsp;
          <fmt:message key="product.code"/>: #${p.product.productId}
        </div>

        <div class="d-flex gap-4">
          <p class="mb-0">
            <fmt:message key="product.price"/>:
            <strong class="text-danger">₫${p.product.price}</strong>
          </p>
          <p class="mb-0">
            <fmt:message key="product.weight"/>:
            <strong>⚖ ${p.product.weight} g</strong>
          </p>
        </div>

        <hr/>

        <p><c:out value="${p.productDescription}"/></p>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>