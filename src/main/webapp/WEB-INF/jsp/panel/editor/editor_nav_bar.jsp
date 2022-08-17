<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<li><a href="#" onclick="editorSave();"><i class="icon solid fa-save"></i><bean:message key="btn.save" bundle="ae21studio" /></a></li>
<li><a href="#" onclick="savePhoto();"><i class="icon solid fa-eye"></i><bean:message key="btn.preview" bundle="ae21studio" /></a></li>
<li><a href="#" onclick="downloadPhoto();"><i class="icon solid fa-download"></i><bean:message key="btn.download" bundle="ae21studio" /></a></li>