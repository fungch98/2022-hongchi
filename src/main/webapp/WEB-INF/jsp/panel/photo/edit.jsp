<%-- 
    Document   : edit
    Created on : 2022年7月7日, 下午07:18:56
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner">

            <header class="config-header">
                <h2><bean:message key="label.photo.title" /><logic:notEmpty name="photo" scope="request"> - ${photo.name}</logic:notEmpty></h2>
                    <div class="config">
                    <logic:notEqual name="uuid" value="new">
                        <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/edit/delete.html" onclick="return showDeleteMessage('<bean:message key="confrim.delete.photo"/>');" class="icon delete solid fa-trash"></a>
                        <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/hashtag.html" class="icon solid fa-tags"></a>
                    </logic:notEqual>
                         <logic:equal name="photo" property="productCreateMethod" value="3">
                             <logic:notEmpty name="photo" property="editorUuid">
                            <a href="${pageContext.request.contextPath}/panel/editor/${langCode}/${photo.uuid}/${photo.editorUuid}/dashboard.html" class="icon solid fa-palette"></a>
                            </logic:notEmpty>
                        </logic:equal>
                        <a href="${pageContext.request.contextPath}/panel/${langCode}/dashboard.html" class="icon solid fa-times"></a>
                </div>
            </header>
                <logic:empty name="photo" scope="request">
                    <h3><bean:message key="ERROR.ACCESS" bundle="error"/></h3>
                </logic:empty>
                <logic:notEmpty name="photo" scope="request">
                    <form action="${pageContext.request.contextPath}/panel/photo/${langCode}/${uuid}/edit/save.html" method="POST" >
                        <jsp:include page="editForm.jsp"/>
                    </form>
                </logic:notEmpty>
        </div>
    </section>
</div>
                
<script src="${pageContext.request.contextPath}/assets/plugin/dropzone/dropzone.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/plugin/dropzone/upload.js" type="text/javascript"></script>
<script>
    <logic:notEqual name="photo" property="productCreateMethod" value="3">
        
                               
    Dropzone.autoDiscover = false;
    $(document).ready(function () {
        // showSnack("Testing");

        //openLink('#en-tab', 0);

        initUploadImageURL('#upload_photo', '#photo', '${pageContext.request.contextPath}/panel/upload/file/new/submit.html', '');
    });
    </logic:notEqual> 
</script>