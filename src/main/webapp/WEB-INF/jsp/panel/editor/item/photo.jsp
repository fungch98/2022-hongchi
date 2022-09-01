<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<div id="item-${itemDetail.uuid}" class="editor-content-container ">
        <header>
            <h4><bean:message key="label.editor.photo"/></h4>
        </header>
            <div class="row gtr-25">
                
                <div class="col-3 col-6-medium">
                    <label for="${itemDetail.uuid}-posx"><bean:message key="label.editor.item.x"/></label>
                    <input type="text" id="${itemDetail.uuid}-posx" name="posx" value="${itemDetail.posX}"  onchange="changePos('${itemDetail.uuid}');"/>
                </div>
                <div class="col-3 col-6-medium">
                    <label for="${itemDetail.uuid}-posy"><bean:message key="label.editor.item.y"/></label>
                    <input type="text" id="${itemDetail.uuid}-posy" name="posy" value="${itemDetail.posY}" onchange="changePos('${itemDetail.uuid}');" />
                </div>
                <div class="col-3 col-6-medium">
                    <label for="${itemDetail.uuid}-width"><bean:message key="label.editor.item.width"/></label>
                    <input type="text" id="${itemDetail.uuid}-width" name="width" value="${itemDetail.width}"  onchange="changeSize('${itemDetail.uuid}');" />
                </div>
                <div class="col-3 col-6-medium">
                    <label for="${itemDetail.uuid}-height"><bean:message key="label.editor.item.height"/></label>
                    <input type="text" id="${itemDetail.uuid}-height" name="height" value="${itemDetail.height}" onchange="changeSize('${itemDetail.uuid}');" />
                </div>
                <input type="hidden" id="${itemDetail.uuid}-rotate" name="rotate" value="${itemDetail.rotate}"  />
                
                
                
                <div class="col-12">
                    <label for="${itemDetail.uuid}-name"><bean:message key="label.editor.item.name"/></label>
                    <input type="text" id="${itemDetail.uuid}-name" name="name" value="${itemDetail.name}"  onchange="changeName('${itemDetail.uuid}',this.value);" maxlength="100"/>
                </div>
            </div>
        
        <input type="hidden" id="${itemDetail.uuid}-color" name="color" value="${itemDetail.color}"/>
        <input type="hidden" id="${itemDetail.uuid}-itemType" name="itemType" value="${itemDetail.itemType}"/>
        <input type="hidden" id="${itemDetail.uuid}-uuid" name="uuid" value="${itemDetail.uuid}"/>
        <input type="hidden" id="${itemDetail.uuid}-seq" name="seq" value="${itemDetail.seq}"/>
        <input type="hidden" name="bg-color" value="${itemDetail.bgColor}${itemDetail.opacityVal}">
        
        
        
        <input type="hidden" id="${itemDetail.uuid}-imgSrc" name="imgSrc" value="${itemDetail.imgSrc}"/>
        <input type="hidden" id="${itemDetail.uuid}-upSrc" name="upSrc" value="${itemDetail.imgUploadSrc}"/>
        <input type="hidden" id="${itemDetail.uuid}-upUUID" name="upUUID" value="${itemDetail.imgUploadUuid}"/>
        <input type="hidden" id="${itemDetail.uuid}-imgURL" name="imgURL" value="${itemDetail.imgUrl}"/>
        <input type="hidden" id="${itemDetail.uuid}-imgUUID" name="imgUUID" value="${itemDetail.imgUuid}"/>
       
        
        <input type="hidden" id="${itemDetail.uuid}-text" name="text" value="${itemDetail.text}"/>
        <input type="hidden" id="${itemDetail.uuid}-textDesc" name="textDesc" value="${itemDetail.textDesc}"/>
        <input type="hidden" id="${itemDetail.uuid}-zIndex" name="zIndex" value="${itemDetail.zIndex}"/>
      
        <input type="hidden" id="${itemDetail.uuid}-fontSize" name="fontSize" value="${itemDetail.fontSize}"  />
        <input type="hidden" id="${itemDetail.uuid}-fontName" name="fontName" value="${itemDetail.fontName}"  />
        
        <input type="hidden" id="${itemDetail.uuid}-textAlign" name="textAlign" value="${itemDetail.textAlign}"  />
        <input type="hidden" id="${itemDetail.uuid}-textBold" name="textBold" value="${itemDetail.textBold}"  />
        <input type="hidden" id="${itemDetail.uuid}-textItalic" name="textItalic" value="${itemDetail.textItalic}"  />
        
    </div>
