<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notEmpty name="itemDetail">
    <jsp:include page="photo.jsp"/>
    @@SPLIT@@@
    <div id="item-${itemDetail.uuid}-obj" class="editor-content-obj ui-state-active draggable ui-widget-content">
        <img src="${pageContext.request.contextPath}${itemDetail.imgUrl}" class="item-obj-img"/>
    </div>
    @@SPLIT@@@
    <jsp:include page="item-list.jsp"/>
    @@SPLIT@@@
    ${itemDetail.uuid}
</logic:notEmpty>

