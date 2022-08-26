<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notEmpty name="itemDetail">
    <logic:equal name="itemDetail" property="itemType" value="bg">
        
    </logic:equal>
    <logic:equal name="itemDetail" property="itemType" value="upload">
        <jsp:include page="item/upload-ajax.jsp"/>
    </logic:equal>
    <logic:equal name="itemDetail" property="itemType" value="photo">
        <jsp:include page="item/photo-ajax.jsp"/>
    </logic:equal>
    <logic:equal name="itemDetail" property="itemType" value="text">
        <jsp:include page="item/text-ajax.jsp"/>
    </logic:equal>
</logic:notEmpty>