<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div id="item-${itemDetail.uuid}-obj" tabindex="0" onclick="editItemContent('${itemDetail.uuid}');return false;" class="editor-content-obj ui-state-active draggable ui-widget-content rotatable" style="width:${itemDetail.width}px;height:${itemDetail.height}px;top:${itemDetail.posX}px;left:${itemDetail.posY}px; z-index:${itemDetail.zIndex};background-color: transparent;">
        <img src="${pageContext.request.contextPath}/${itemDetail.imgUrl}" class="item-obj-img"/>
    </div>
