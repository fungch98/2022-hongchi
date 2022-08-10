<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div id="editor-content-container">
        <logic:notEmpty name="editor" property="editorItemList" scope="request">
            <logic:iterate id="item" name="editor" property="editorItemList" scope="request">
                <c:set var="itemDetail" value="${item}" scope="request"/>
                <logic:equal name="item" property="itemType" value="bg">
                    <jsp:include page="item/bg.jsp"/>
                </logic:equal>
            </logic:iterate>
        </logic:notEmpty>
</div>


<script>
    $(document).ready(function () {
        document.getElementById("${itemDetail.uuid}-bg-color").onchange();
    });
</script>