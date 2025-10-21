<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="t"   tagdir="/WEB-INF/tags"  %>

<fmt:setLocale value="${empty lang ? 'vi' : lang}"/>
<fmt:setBundle basename="app_i18n.messages"/>

<!doctype html>
<html lang="${empty lang ? 'vi' : lang}">
<head>
  <meta charset="utf-8">
  <title>Thêm sản phẩm</title>
</head>
<body>

<%@ include file="/WEB-INF/views/_layout.jspf" %>

<div class="container my-4">
  <h3 class="mb-4">➕ Thêm sản phẩm mới</h3>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <form method="post" action="<t:urlWithLang value='/products/add'/>">
    <div class="mb-3">
      <label>Giá (₫)</label>
      <input type="number" step="0.01" name="price" class="form-control" required>
    </div>

    <div class="mb-3">
      <label>Khối lượng (gram)</label>
      <input type="number" step="0.01" name="weight" class="form-control" required>
    </div>

    <div class="mb-3">
      <label>Danh mục</label>
      <select name="categoryId" class="form-select" required>
        <option value="">-- Chọn danh mục --</option>
        <c:forEach var="c" items="${categories}">
          <option value="${c.id.productCategoryId}">${c.categoryName}</option>
        </c:forEach>
      </select>
    </div>

    <hr>
    <h5>Bản dịch tiếng Việt</h5>
    <input name="name_vi" class="form-control mb-2" placeholder="Tên sản phẩm (VI)">
    <textarea name="desc_vi" class="form-control mb-3" placeholder="Mô tả (VI)"></textarea>

    <h5>Bản dịch tiếng Anh</h5>
    <input name="name_en" class="form-control mb-2" placeholder="Product name (EN)">
    <textarea name="desc_en" class="form-control mb-3" placeholder="Description (EN)"></textarea>

    <h5>Bản dịch tiếng Pháp</h5>
    <input name="name_fr" class="form-control mb-2" placeholder="Nom du produit (FR)">
    <textarea name="desc_fr" class="form-control mb-3" placeholder="Description (FR)"></textarea>

    <button type="submit" class="btn btn-success w-100 mt-3">Thêm sản phẩm</button>
  </form>
</div>

</body>
</html>
