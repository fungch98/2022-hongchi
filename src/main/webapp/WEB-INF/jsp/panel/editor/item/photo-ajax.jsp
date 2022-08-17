<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notEmpty name="itemDetail">
    <jsp:include page="photo.jsp"/>
    @@SPLIT@@@
    <jsp:include page="photo-view.jsp"/>
    @@SPLIT@@@
    <jsp:include page="item-list.jsp"/>
    @@SPLIT@@@
    ${itemDetail.uuid}
</logic:notEmpty>

