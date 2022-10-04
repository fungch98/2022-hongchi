<%-- 
    Document   : editor_action_container
    Created on : 2022年6月28日, 上午11:06:07
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!--
<div class="editor-menu-action">
    <div class="editor-menu-action-item">
        <a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_text.png"/>文字</a>
    </div>
    <div class="editor-menu-action-item">
        <a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_image.png"/>圖片</a>
    </div>
    <div class="editor-menu-action-item">
        <a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_bg.png"/>校本角色</a>
    </div>
    <div class="editor-menu-action-item">
        <a href="#" onclick="addItem('upload','');return false;"><img src="${pageContext.request.contextPath}/images/icon/action_upload.png"/>上載</a>
    </div>
</div>
-->
<ul class="editor-menu-action">
    <li><a href="#" onclick="showItemDetail('image');return false;"><img src="${pageContext.request.contextPath}/images/icon/action_image.png"/><p>圖片</p></a></li>
    <li><a href="#"  onclick="addItem('text','');return false;"><img src="${pageContext.request.contextPath}/images/icon/action_text.png"/><p>文字</p></a></li>
    
    <li><a href="#" onclick="showItemDetail('character');return false;"><img src="${pageContext.request.contextPath}/images/icon/action_bg.png"/><p>校本角色</p></a></li>
    <li><a href="#" onclick="showItemDetail('material');return false;"><img src="${pageContext.request.contextPath}/images/icon/action_material.png"/><p>物件</p></a></li>
    <li><a href="#" onclick="uploadPhoto();return false;"><img src="${pageContext.request.contextPath}/images/icon/action_upload.png"/><p>上載</p></a></li>
    <li><a href="#" onclick="editorSave();return false;"><img src="${pageContext.request.contextPath}/images/icon/save.png"/><p><bean:message key="btn.save" bundle="ae21studio" /></p></a></li>
</ul>