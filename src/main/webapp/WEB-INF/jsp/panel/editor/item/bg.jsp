<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>



    <div id="item-${itemDetail.uuid}" class="editor-content-container">
        <header  class="alt">
            <h4><bean:message key="label.editor.bg"/>${itemDetail.bgColor}-${itemDetail.opacityVal}</h4>
        </header>
        
        <input type="hidden" id="${itemDetail.uuid}-itemType" name="itemType" value="${itemDetail.itemType}"/>
        <input type="hidden" id="${itemDetail.uuid}-uuid" name="uuid" value="${itemDetail.uuid}"/>
        <input type="hidden" id="${itemDetail.uuid}-seq" name="seq" value="${itemDetail.seq}"/>
        <input id="${itemDetail.uuid}-bg-color" name="bg-color" value="${itemDetail.bgColor}${itemDetail.opacityVal}" data-jscolor="{}" onchange="setLayerBackground(this.value);">
        
        <input type="hidden" id="${itemDetail.uuid}-color" name="color" value="${itemDetail.color}"/>
        <input type="hidden" id="${itemDetail.uuid}-width" name="width" value="${itemDetail.width}"/>
        <input type="hidden" id="${itemDetail.uuid}-height" name="height" value="${itemDetail.height}"/>
        <input type="hidden" id="${itemDetail.uuid}-imgSrc" name="imgSrc" value="${itemDetail.imgSrc}"/>
         <input type="hidden" id="${itemDetail.uuid}-upSrc" name="upSrc" value="${itemDetail.imgUploadSrc}"/>
        <input type="hidden" id="${itemDetail.uuid}-upUUID" name="upUUID" value="${itemDetail.imgUploadUuid}"/>
        <input type="hidden" id="${itemDetail.uuid}-imgURL" name="imgURL" value="${itemDetail.imgUrl}"/>
        <input type="hidden" id="${itemDetail.uuid}-imgUUID" name="imgUUID" value="${itemDetail.imgUuid}"/>
        <input type="hidden" id="${itemDetail.uuid}-posx" name="posx" value="${itemDetail.posX}"/>
        <input type="hidden" id="${itemDetail.uuid}-posy" name="posy" value="${itemDetail.posY}"/>
        
        <input type="hidden" id="${itemDetail.uuid}-text" name="text" value="${itemDetail.text}"/>
        <input type="hidden" id="${itemDetail.uuid}-textDesc" name="textDesc" value="${itemDetail.textDesc}"/>
        <input type="hidden" id="${itemDetail.uuid}-zIndex" name="zIndex" value="${itemDetail.zIndex}"/>
        
        <input type="hidden" id="${itemDetail.uuid}-name" name="name" value="${itemDetail.name}"  />
        
        <input type="hidden" id="${itemDetail.uuid}-fontSize" name="fontSize" value="${itemDetail.fontSize}"  />
        <input type="hidden" id="${itemDetail.uuid}-fontName" name="fontName" value="${itemDetail.fontName}"  />
        <input type="hidden" id="${itemDetail.uuid}-rotate" name="rotate" value="${itemDetail.rotate}"  />
        
        <input type="hidden" id="${itemDetail.uuid}-textAlign" name="textAlign" value="${itemDetail.textAlign}"  />
        <input type="hidden" id="${itemDetail.uuid}-textBold" name="textBold" value="${itemDetail.textBold}"  />
        <input type="hidden" id="${itemDetail.uuid}-textItalic" name="textItalic" value="${itemDetail.textItalic}"  />
        
    </div>
