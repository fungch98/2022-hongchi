<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notEmpty name="itemDetail">
    <jsp:include page="upload.jsp"/>
    @@SPLIT@@@
    <div id="item-${itemDetail.uuid}-obj" class="editor-content-obj">
    </div>
    @@SPLIT@@@
    <jsp:include page="item-list.jsp"/>
</logic:notEmpty>
