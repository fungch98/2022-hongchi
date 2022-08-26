<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div id="item-${itemDetail.uuid}-obj" onclick="editItemContent('${itemDetail.uuid}');return false;" class="editor-content-obj ui-state-active draggable ui-widget-content rotatable ${itemDetail.fontName} ${itemDetail.textAlign}  <logic:notEmpty name="itemDetail"><logic:equal name="itemDetail" property="textBold" value="1">bold</logic:equal><logic:equal name="itemDetail" property="textItalic" value="1">italic</logic:equal></logic:notEmpty>  " style="width:${itemDetail.width}px;height:${itemDetail.height}px;top:${itemDetail.posX}px;left:${itemDetail.posY}px; z-index:${itemDetail.zIndex};color:${itemDetail.color}; background-color:${itemDetail.rgb};font-size:${itemDetail.fontSize}pt;">
    <pre>${itemDetail.textDesc}</pre>
</div>
