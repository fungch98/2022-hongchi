<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div class="model-form-container">
 <logic:empty name="photo" scope="request">
    <h3><bean:message key="ERROR.ACCESS" bundle="error"/></h3>
</logic:empty>
<logic:notEmpty name="photo" scope="request">
    <form id="editor-upload-form" action="${pageContext.request.contextPath}/panel/editor/${langCode}/item/upload/save.html" onsubmit="saveUploadPhoto();return false;" method="POST" >
        <jsp:include page="../../photo/editForm.jsp"/>
    </form>
</logic:notEmpty>
    </div>
    
<script>
    Dropzone.autoDiscover = false;
    $(document).ready(function () {
        initUploadImageURL('#upload_photo', '#photo', '${pageContext.request.contextPath}/panel/upload/file/new/submit.html', '');
    });
    $("#btn-close").click(function() {closeModal();return false; });
</script>
