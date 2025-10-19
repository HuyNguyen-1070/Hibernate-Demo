<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="t"   tagdir="/WEB-INF/tags"  %>

<!doctype html>
<html lang="${empty lang?'vi':lang}">
<head>
  <meta charset="utf-8">
  <!-- THÊM: Meta tags cho UTF-8 -->
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Edit</title>
</head>
<body>
<fmt:setLocale value="${empty lang?'vi':lang}"/>
<fmt:setBundle basename="app_i18n.messages"/>
<%@ include file="/WEB-INF/views/_layout.jspf" %>

<div class="container my-4">
  <h4>Sửa sản phẩm #${coreProduct.productId}</h4>

  <!-- THÊM: accept-charset="UTF-8" vào form -->
  <form method="post" action="<t:urlWithLang value='/products/edit'/>"
        class="row g-3" accept-charset="UTF-8">
    <input type="hidden" name="id" value="${coreProduct.productId}"/>

    <div class="col-md-4">
      <label class="form-label">Giá</label>
      <input class="form-control" name="price" value="${coreProduct.price}">
    </div>

    <div class="col-md-4">
      <label class="form-label">Khối lượng</label>
      <input class="form-control" name="weight" value="${coreProduct.weight}">
    </div>

    <div class="col-md-4">
      <label class="form-label">Danh mục</label>
      <select class="form-select" name="categoryId">
        <c:forEach var="c" items="${categories}">
          <option value="${c.id.productCategoryId}"
            ${c.id.productCategoryId == coreProduct.productCategory.productCategoryId ? 'selected' : ''}>
            ${c.categoryName}
          </option>
        </c:forEach>
      </select>
    </div>

    <hr/>

    <div class="col-md-4">
      <label class="form-label">Tên (VI)</label>
      <input class="form-control" name="name_vi" value="${vi.productName}">
      <label class="form-label mt-2">Mô tả (VI)</label>
      <textarea class="form-control" name="desc_vi" rows="3"><c:out value="${vi.productDescription}"/></textarea>
    </div>

    <div class="col-md-4">
      <label class="form-label">Name (EN)</label>
      <input class="form-control" name="name_en" value="${en.productName}">
      <label class="form-label mt-2">Description (EN)</label>
      <textarea class="form-control" name="desc_en" rows="3"><c:out value="${en.productDescription}"/></textarea>
    </div>

    <div class="col-md-4">
      <label class="form-label">Nom (FR)</label>
      <input class="form-control" name="name_fr" value="${fr.productName}">
      <label class="form-label mt-2">Description (FR)</label>
      <textarea class="form-control" name="desc_fr" rows="3"><c:out value="${fr.productDescription}"/></textarea>
    </div>

    <div class="col-12 d-flex gap-2 mt-3">
      <button type="submit" class="btn btn-primary">Lưu</button>
      <a class="btn btn-secondary" href="<t:urlWithLang value='/products/detail?id=${coreProduct.productId}'/>">Hủy</a>
    </div>
  </form>
</div>
</body>
</html>