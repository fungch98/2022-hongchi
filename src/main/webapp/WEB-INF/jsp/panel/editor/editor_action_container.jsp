<%-- 
    Document   : editor_action_container
    Created on : 2022年6月28日, 上午11:06:07
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<ul>
    <li><a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_bg.png"/><p>背景</p></a></li>
    <li><a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_text.png"/><p>文字</p></a></li>
    <li><a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_material.png"/><p>素材</p></a></li>
    <li><a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_image.png"/><p>圖片</p></a></li>
    <li><a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_bg.png"/><p>校本角色</p></a></li>
    <li><a href="#"><img src="${pageContext.request.contextPath}/images/icon/action_upload.png"/><p>上載</p></a></li>
    
</ul>