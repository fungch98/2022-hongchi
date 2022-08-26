<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<li class="editor nav valid"><a href="#" onclick="editorSave();"><i class="icon solid fa-save"></i><bean:message key="btn.save" bundle="ae21studio" /></a></li>
<li  class="editor nav <logic:notEmpty name="uuid" scope="request"><logic:notEqual name="uuid" scope="request" value="new">valid</logic:notEqual></logic:notEmpty>" ><a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${editor.prodId.uuid}/view.html" id="preview-editor-btn" target="_blank" ><i class="icon solid fa-eye"></i><bean:message key="btn.preview" bundle="ae21studio" /></a></li>

<li class="editor nav <logic:notEmpty name="uuid" scope="request"><logic:notEqual name="uuid" scope="request" value="new">valid</logic:notEqual></logic:notEmpty>"><a href="${pageContext.request.contextPath}/panel/upload/editor/${editor.uuid}/download.html" target="_blank"  id="download-editor-btn" ><i class="icon solid fa-download"></i><bean:message key="btn.download" bundle="ae21studio" /></a></li>