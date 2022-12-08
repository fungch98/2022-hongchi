<%-- 
    Document   : info
    Created on : 2022年8月14日, 下午07:21:19
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<div id="item-editor-info" class="editor-content-container ">
    <header class="alt">
        <h4><bean:message key="label.editor.info"/></h4>
    </header>
    <div class="row gtr-25">

         <div class="col-6 col-12-medium">
            <label for="photo-info-name"><bean:message key="label.editor.info.name"/></label>
            <input type="text" id="photo-info-name" name="photo-name" value="${editor.name}" />

            <input type="hidden" id="photo-info-folder" name="photo-info-folder" value="${folder}" />
        </div>
        <div class="col-6 col-12-medium">
                    <label for="hashtag"><bean:message key="label.tag.name"/></label>
                    <input type="text" id="hashtag" name="hashtag" value="${hashtag}" maxlength="1000" placeholder="<bean:message key="label.tag.desc" />"/>
                </div>
        <logic:notEmpty name="UserAuthorizedLogin" >
            <logic:equal name="UserAuthorizedLogin"  property="isAdmin" value="1">
                <div class="col-6 col-6-medium">
                    <label for="isShare"><bean:message key="label.share"/></label>
                    <select id="isShare" name="isShare">

                        <option value="1" <logic:equal name="photo" property="isShare" value="1">Selected</logic:equal> ><bean:message key="label.isShare.1" bundle="ae21studio"/></option>
                        <option value="0" <logic:equal name="photo" property="isShare" value="0">Selected</logic:equal>><bean:message key="label.isShare.0" bundle="ae21studio"/></option>
                        </select>
                    </div>
                    <div class="col-6 col-6-medium">
                        <label for="folder"><bean:message key="label.folder"/></label>
                    <select id="folder" name="folder">
                        <logic:notEmpty name="folderlist" scope="request">
                            <logic:iterate  name="folderlist" scope="request" id="cat" indexId="seq">
                                <option value="${cat.para.uuid}" <logic:equal name="cat" property="selected" value="true">selected</logic:equal> >${cat.para.name}</option>
                            </logic:iterate>
                        </logic:notEmpty>
                    </select>
                </div>

            </logic:equal>
        </logic:notEmpty>

        <div class="col-12">
            <label for="photo-desc"><bean:message key="label.editor.info.desc"/></label>
            <textarea id="photo-desc" name="photo-desc" rows="3">${editor.editorDesc}</textarea>
        </div>
       

    </div>
</div>
