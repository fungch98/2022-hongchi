<%-- 
    Document   : warningMsg
    Created on : 2021年4月2日, 下午10:28:42
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notEmpty name="SAVE_RESULT" scope="request">
<div class="col-12">
    <logic:equal name="SAVE_RESULT" property="code" value="-9999">
        <div class="msg danger">
            <b><bean:message key="label.danger" bundle="error"/></b><bean:message key="${SAVE_RESULT.msg}" bundle="error"/>(<bean:message key="label.error.code" bundle="error"/>: ${SAVE_RESULT.code})
        </div>
    </logic:equal>
    <logic:notEqual name="SAVE_RESULT" property="code" value="-9999">
        <div class="msg warning">
            <b><bean:message key="label.warning" bundle="error"/></b><bean:message key="${SAVE_RESULT.msg}" bundle="error"/>(<bean:message key="label.error.code" bundle="error"/>: ${SAVE_RESULT.code})
        </div>
    </logic:notEqual>
</div>
</logic:notEmpty>