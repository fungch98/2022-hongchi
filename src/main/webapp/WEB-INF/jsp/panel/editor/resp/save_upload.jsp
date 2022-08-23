<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notEmpty name="result" scope="request">
    {"code":${result.code},"msg":"<bean:message key="${result.msg}" bundle="error" /> (${result.code})","uuid":"${result.obj.uuid}"}
</logic:notEmpty>
<logic:empty name="result" scope="request">
    {"code":99,"msg":"","uuid":"new"}
</logic:empty>
