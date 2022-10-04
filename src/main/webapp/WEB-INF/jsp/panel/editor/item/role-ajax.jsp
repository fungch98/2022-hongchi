<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notEmpty name="itemDetail">
    <jsp:include page="role.jsp"/>
    @@SPLIT@@@
    <jsp:include page="role-view.jsp"/>
    @@SPLIT@@@
    <jsp:include page="item-list.jsp"/>
    @@SPLIT@@@
    ${itemDetail.uuid}
</logic:notEmpty>

