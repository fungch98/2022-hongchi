<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div id="editor-content-container">
    <jsp:include page="item/info.jsp"/>
        <logic:notEmpty name="itemList"  scope="request">
            <logic:iterate id="item" name="itemList"  scope="request">
                <c:set var="itemDetail" value="${item}" scope="request"/>
                <logic:equal name="item" property="itemType" value="bg">
                    <jsp:include page="item/bg.jsp"/>
                    <script>
                        $(document).ready(function () {
                            document.getElementById("${itemDetail.uuid}-bg-color").onchange();
                            //console.log("#${itemDetail.uuid}-bg-color"+":"+$("#${itemDetail.uuid}-bg-color").val());
                            //setLayerBackground($("#${itemDetail.uuid}-bg-color").val());
                        });
                    </script>
                </logic:equal>
                <logic:equal name="item" property="itemType" value="photo">
                    <jsp:include page="item/photo.jsp"/>
                </logic:equal>
                <logic:equal name="item" property="itemType" value="text">
                    <jsp:include page="item/text.jsp"/>
                </logic:equal>
                <logic:equal name="item" property="itemType" value="material">
                    <jsp:include page="item/material.jsp"/>
                </logic:equal>
                <logic:equal name="item" property="itemType" value="role">
                    <jsp:include page="item/role.jsp"/>
                    <script>
                        $(document).ready(function () {
                            updRoleOption('${itemDetail.uuid}', ''+$("#${itemDetail.uuid}-upUUID").val());
                            //alert("Update Action");
                        });
                    </script>
                </logic:equal>
            </logic:iterate>
        </logic:notEmpty>
</div>


