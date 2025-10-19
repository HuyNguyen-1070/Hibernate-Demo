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
  <h4 class="mb-3"><fmt:message key="products.title"/></h4>

  <c:choose>
    <c:when test="${empty products}">
      <div class="alert alert-info"><fmt:message key="products.empty"/></div>
    </c:when>
    <c:otherwise>
      <table class="table table-hover bg-white rounded-4">
        <thead>
          <tr>
            <th><fmt:message key="col.id"/></th>
            <th><fmt:message key="col.name"/></th>
            <th><fmt:message key="col.price"/></th>
            <th><fmt:message key="col.weight"/></th>
            <th><fmt:message key="col.category"/></th>
            <th><fmt:message key="col.description"/></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${products}">
          <tr>
            <td>${p.product.productId}</td> <td>${p.productName}</td> <td>${p.product.price}</td> <td>${p.product.weight}</td> <td>${p.product.productCategory.productCategoryId}</td> <td><c:out value="${p.productDescription}"/></td> <td>
              <a class="btn btn-sm btn-outline-primary"
                 href="<t:urlWithLang value='/products/detail?id=${p.product.productId}'/>"> <fmt:message key="btn.details"/>
              </a>
              <a class="btn btn-sm btn-outline-secondary"
                   href="<t:urlWithLang value='/products/edit?id=${p.product.productId}'/>"> <fmt:message key="btn.edit"/>
                </a>

                <form method="post"
                      action="<t:urlWithLang value='/products/delete'/>"
                      style="display:inline"
                      onsubmit="return confirm('Bạn có chắc muốn xóa sản phẩm #${p.product.productId}?');"> <input type="hidden" name="id" value="${p.product.productId}"/> <button class="btn btn-sm btn-outline-danger">
                    <fmt:message key="btn.delete"/>
                  </button>
                </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>