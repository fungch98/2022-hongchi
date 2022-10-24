<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<div id="item-${itemDetail.uuid}" class="editor-content-container ">
        <header>
            <h4><bean:message key="label.editor.photo"/></h4>
        </header>
            <div class="row gtr-25">
                
        <input type="hidden" id="${itemDetail.uuid}-textDesc" name="textDesc" value="${itemDetail.textDesc}"/>
        
        <div class="col-5 col-12-small" >
            <div class="row gtr-25">
                <div class="col-12">
                    <label for="${itemDetail.uuid}-text"><bean:message key="label.editor.text.content"/></label>
                    <textarea id="${itemDetail.uuid}-text" name="text" onchange="setTextStyle('${itemDetail.uuid}','text', this.value)" onkeyup="setTextStyle('${itemDetail.uuid}','text', this.value)" rows="4">${itemDetail.textDesc}</textarea>
                </div>
                <!--
                <div class="col-12">
                    <label for="${itemDetail.uuid}-name"><bean:message key="label.editor.item.name"/></label>
                    <input type="text" id="${itemDetail.uuid}-name" name="name" value="${itemDetail.name}"  onchange="changeName('${itemDetail.uuid}',this.value);" onkeyup="changeName('${itemDetail.uuid}',this.value);" maxlength="100"/>
                </div>
                -->
            </div>
        </div>
        <div class="col-7 col-12-small" >
            <div class="row gtr-25">
                <div class="col-4 col-6-medium col-4-small" >
                    <label for="${itemDetail.uuid}-fontSize"><bean:message key="label.editor.text.size"/></label>
                    <input type="number" id="${itemDetail.uuid}-fontSize" name="fontSize" value="${itemDetail.fontSize}" onchange="setTextStyle('${itemDetail.uuid}','size', this.value);"  />
                </div>
                 <div class="col-6" >
                    <label for="${itemDetail.uuid}-fontName"><bean:message key="label.editor.text.font"/></label>
                    <select id="${itemDetail.uuid}-fontName" name="fontName" onchange="setTextStyle('${itemDetail.uuid}','font', this.value)">
                        <option value="times" <logic:notEmpty name="itemDetail" property="fontName"><logic:equal name="itemDetail" property="fontName" value="times">selected="selected"</logic:equal></logic:notEmpty>   >Times New Roman</option>
                        <option value="arial" <logic:notEmpty name="itemDetail" property="fontName"><logic:equal name="itemDetail" property="fontName" value="arial">selected="selected"</logic:equal></logic:notEmpty>>Arial</option>
                        <option value="noto" <logic:notEmpty name="itemDetail" property="fontName"><logic:equal name="itemDetail" property="fontName" value="noto">selected="selected"</logic:equal></logic:notEmpty>>Noto Sans HK</option>
                        <option value="hk" <logic:notEmpty name="itemDetail" property="fontName"><logic:equal name="itemDetail" property="fontName" value="hk">selected="selected"</logic:equal></logic:notEmpty>>香港字庫</option>
                        <option value="SimSun" <logic:notEmpty name="itemDetail" property="fontName"><logic:equal name="itemDetail" property="fontName" value="SimSun">selected="selected"</logic:equal></logic:notEmpty>>宋體</option>
                        
                        
                    </select>
                </div>
                    <!--
                 <div class="col-4 col-6-medium col-4-small" >
                    <label for="${itemDetail.uuid}-textAlign"><bean:message key="label.editor.text.align"/></label>
                    <select id="${itemDetail.uuid}-textAlign" name="textAlign" onchange="setTextStyle('${itemDetail.uuid}','align', this.value)">
                        <option value="left" <logic:notEmpty name="itemDetail" property="textAlign"><logic:equal name="itemDetail" property="textAlign" value="left">selected="selected"</logic:equal></logic:notEmpty>   >Left</option>
                        <option value="center" <logic:notEmpty name="itemDetail" property="textAlign"><logic:equal name="itemDetail" property="textAlign" value="center">selected="selected"</logic:equal></logic:notEmpty>>Center</option>
                        <option value="right" <logic:notEmpty name="itemDetail" property="textAlign"><logic:equal name="itemDetail" property="textAlign" value="right">selected="selected"</logic:equal></logic:notEmpty>>Right</option>
                    </select>
                </div>
                    -->
                 <div class="col-4 col-6-medium col-4-small" >
                    <label for="${itemDetail.uuid}-textBold"><bean:message key="label.editor.text.bold"/></label>
                    <select id="${itemDetail.uuid}-textBold" name="textBold" onchange="setTextStyle('${itemDetail.uuid}','bold', this.value)">
                        <option value="0" <logic:notEmpty name="itemDetail" property="textBold"><logic:equal name="itemDetail" property="textBold" value="0">selected="selected"</logic:equal></logic:notEmpty>   ><bean:message key="label.editor.text.normal"/></option>
                        <option value="1" <logic:notEmpty name="itemDetail" property="textBold"><logic:equal name="itemDetail" property="textBold" value="1">selected="selected"</logic:equal></logic:notEmpty>><bean:message key="label.editor.text.bold"/></option>
                        
                    </select>
                </div>
                  <div class="col-4 col-6-medium col-4-small" >
                    <label for="${itemDetail.uuid}-textItalic"><bean:message key="label.editor.text.italic"/></label>
                    <select id="${itemDetail.uuid}-textItalic" name="textItalic" onchange="setTextStyle('${itemDetail.uuid}','italic', this.value)">
                        <option value="0" <logic:notEmpty name="itemDetail" property="textItalic"><logic:equal name="itemDetail" property="textItalic" value="0">selected="selected"</logic:equal></logic:notEmpty>   ><bean:message key="label.editor.text.normal"/></option>
                        <option value="1" <logic:notEmpty name="itemDetail" property="textItalic"><logic:equal name="itemDetail" property="textItalic" value="1">selected="selected"</logic:equal></logic:notEmpty>><bean:message key="label.editor.text.italic"/></option>
                        
                    </select>
                </div>       
                <input type="hidden" id="${itemDetail.uuid}-textAlign" name="textAlign" value="${itemDetail.textAlign}"  />
               
                <div class="col-4 col-6-medium col-4-small" >
                    <label for="${itemDetail.uuid}-color"><bean:message key="label.editor.text.color"/></label>
                    <input type="text"  id="${itemDetail.uuid}-color" name="color" value="${itemDetail.color}" data-jscolor="{}" onchange="setTextStyle('${itemDetail.uuid}','color',this.value);">
                </div>
            </div>
        </div>
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
        
        
               
                
                 
                
      
               
            </div>
       <input type="hidden" id="${itemDetail.uuid}-name" name="name" value="${itemDetail.name}"/>
        <input type="hidden" id="${itemDetail.uuid}-rotate" name="rotate" value="${itemDetail.rotate}"  />
        <input type="hidden" id="${itemDetail.uuid}-itemType" name="itemType" value="${itemDetail.itemType}"/>
        <input type="hidden" id="${itemDetail.uuid}-uuid" name="uuid" value="${itemDetail.uuid}"/>
        <input type="hidden" id="${itemDetail.uuid}-seq" name="seq" value="${itemDetail.seq}"/>
        <input type="hidden" name="bg-color" value="${itemDetail.bgColor}${itemDetail.opacityVal}">
        
        
        
        <input type="hidden" id="${itemDetail.uuid}-imgSrc" name="imgSrc" value="${itemDetail.imgSrc}"/>
        <input type="hidden" id="${itemDetail.uuid}-upSrc" name="upSrc" value="${itemDetail.imgUploadSrc}"/>
        <input type="hidden" id="${itemDetail.uuid}-upUUID" name="upUUID" value="${itemDetail.imgUploadUuid}"/>
        <input type="hidden" id="${itemDetail.uuid}-imgURL" name="imgURL" value="${itemDetail.imgUrl}"/>
        <input type="hidden" id="${itemDetail.uuid}-imgUUID" name="imgUUID" value="${itemDetail.imgUuid}"/>
       <input type="hidden" id="${itemDetail.uuid}-zIndex" name="zIndex" value="${itemDetail.zIndex}"/>
        
        
    </div>
