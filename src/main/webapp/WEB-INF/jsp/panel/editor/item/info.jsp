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
                
                <div class="col-12">
                    <label for="photo-info-name"><bean:message key="label.editor.info.name"/></label>
                    <input type="text" id="photo-info-name" name="photo-name" value="${editor.name}" />
                    
                    <input type="hidden" id="photo-info-folder" name="photo-info-folder" value="${folder}" />
                </div>
                
                <div class="col-12">
                    <label for="photo-desc"><bean:message key="label.editor.info.desc"/></label>
                    <textarea id="photo-desc" name="photo-desc" rows="3">${editor.editorDesc}</textarea>
                </div>
            </div>
</div>
