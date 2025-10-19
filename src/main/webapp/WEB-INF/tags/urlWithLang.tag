<%-- src/main/webapp/WEB-INF/tags/urlWithLang.tag --%>
<%@ tag description="Append ?lang= to URL if missing" pageEncoding="UTF-8" body-content="empty" %>
<%@ attribute name="value" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
  <c:when test="${fn:contains(value, 'lang=')}">
    <c:url var="__u" value="${value}" />
  </c:when>
  <c:otherwise>
    <c:url var="__u" value="${value}">
      <c:param name="lang" value="${requestScope.lang}" />
    </c:url>
  </c:otherwise>
</c:choose>
${__u}
