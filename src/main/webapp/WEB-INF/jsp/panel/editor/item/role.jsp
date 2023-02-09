<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<div id="item-${itemDetail.uuid}" class="editor-content-container ">
        <header>
            <h4><bean:message key="label.editor.role"/></h4>
        </header>
            <div class="row gtr-25">
                <div class="col-3 col-6-medium">
                    <label for="${itemDetail.uuid}-role-emotion"><bean:message key="label.editor.item.role.emotion"/></label>
                    <select id="${itemDetail.uuid}-role-emotion" name="role-emotion" onchange="chageRole('${itemDetail.uuid}');">
                        <logic:notEmpty name="emotionList">
                            <logic:iterate id="emotion" name="emotionList">
                                <option value="${emotion.str02}">${emotion.str01}</option>
                            </logic:iterate>
                        </logic:notEmpty>
                    </select>
                </div>
                <div class="col-3 col-6-medium">
                    <label for="${itemDetail.uuid}-role-emotion"><bean:message key="label.editor.item.role.action"/></label>
                    <select  id="${itemDetail.uuid}-role-action" name="role-emotion" onchange="chageRole('${itemDetail.uuid}');">
                        <logic:notEmpty name="actionList">
                            <logic:iterate id="action" name="actionList">
                                <option value="${action.str02}">${action.str01}</option>
                            </logic:iterate>
                        </logic:notEmpty>
                    </select>
                </div>
                    <div class="col-3 col-6-medium">
                        <label for="${itemDetail.uuid}-isFilp"><bean:message key="label.editor.item.filp"/></label>
                        <select id="${itemDetail.uuid}-isFilp" name="isFilp" onchange="filp('${itemDetail.uuid}',this.value);return false;">
                            <option value="0" <logic:notEmpty name="itemDetail" property="isFilp"><logic:equal name="itemDetail" property="isFilp" value="0">selected="selected"</logic:equal></logic:notEmpty>   ><bean:message key="label.editor.text.normal"/></option>
                            <option value="1" <logic:notEmpty name="itemDetail" property="isFilp"><logic:equal name="itemDetail" property="isFilp" value="1">selected="selected"</logic:equal></logic:notEmpty>><bean:message key="label.editor.item.filp"/></option>
                        </select>
                    </div>
                        <div class="col-3 col-6-medium">
                            
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
                <input type="hidden" id="${itemDetail.uuid}-rotate" name="rotate" value="${itemDetail.rotate}"  />
                
                
                
                <div class="col-12">
                    <label for="${itemDetail.uuid}-name"><bean:message key="label.editor.item.name"/></label>
                    <input type="text" id="${itemDetail.uuid}-name" name="name" value="${itemDetail.name}"  onchange="changeName('${itemDetail.uuid}',this.value);" maxlength="100"/>
                </div>
            </div>
        <input type="hidden" id="${itemDetail.uuid}-textUnder" name="textUnder" value="${itemDetail.textUnder}"/>
        <input type="hidden" id="${itemDetail.uuid}-isHidden" name="isHidden" value="${itemDetail.isHidden}"/>
         
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
