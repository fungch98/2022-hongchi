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
                        <a href="${pageContext.request.contextPath}/panel/${langCode}/dashboard.html" class="icon solid fa-times"></a>
                </div>
            </header>
                <logic:empty name="photo" scope="request">
                    <h3><bean:message key="ERROR.ACCESS" bundle="error"/></h3>
                </logic:empty>
                <logic:notEmpty name="photo" scope="request">
                    <form action="${pageContext.request.contextPath}/panel/photo/${langCode}/${uuid}/edit/save.html" method="POST" >
                        <div class="row">
                            <jsp:include page="../../warningMsg.jsp" flush="false"></jsp:include>

                                <div class="col-6 col-6-medium col-12-small">
                                    <div class="row gtr-25">
                                        <div class="col-8 col-12-medium">
                                            <label for="photo-name"><bean:message key="label.photo.name"/></label>
                                        <input type="text" id="photo-name" name="photo-name" value="${photo.name}" maxlength="100"/>
                                    </div>
                                        <div class="col-4 col-12-medium">
                                            <label for="status"><bean:message key="label.status" bundle="ae21studio"/></label>
                                            <select id="status" name="status">
                                                
                                                <option value="1" <logic:equal name="photo" property="status" value="1">Selected</logic:equal> ><bean:message key="label.status.1" bundle="ae21studio"/></option>
                                                <option value="0" <logic:equal name="photo" property="status" value="0">Selected</logic:equal>><bean:message key="label.status.0" bundle="ae21studio"/></option>
                                            </select>
                                        </div>
                                    <div class="col-12">
                                        <label for="desc"><bean:message key="label.photo.desc"/></label>
                                        <textarea id="desc" name="desc" rows="3">${photo.desc}</textarea>
                                    </div>    
                                    <div class="col-12">
                                        <input type="hidden" id="photo" name="photo" value="${photo.productUrl}" onchange="refreshImage(this);"  placeholder="" />
                                        <input type="hidden" id="photo-uuid" name="photo-uuid" value="${photo.productUUID}"   placeholder="" />
                                    </div>
                                    <div class="col-12">
                                        <br>
                                        <div id="upload_photo" class="dropzone needsclick">
                                            <div class="fallback"></div>
                                            <div class="dz-message needsclick">
                                                <span class="note needsclick"><bean:message key="label.upload.caption" bundle="ae21studio"/></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6 col-6-medium col-12-small">
                                <div class="image fit">
                                    <logic:equal name="photo" property="productCreateMethod" value="1">
                                            <img src="${pageContext.request.contextPath}${photo.productUrl}" id="photo-preview" onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'" />
                                    </logic:equal>
                                    <logic:equal name="photo" property="productCreateMethod" value="2">
                                        <img src="${photo.productUrl}" id="photo-preview" onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'" />
                                    </logic:equal>
                                    
                                </div>
                                <br>
                            </div>
                            <div class="col-12">
                                <label>圖片分類</label>
                                <logic:notEmpty name="catList" scope="request">
                                <div class="row">
                                    <logic:iterate  name="catList" scope="request" id="cat" indexId="seq">
                                        <div class="col-3 col-4-medium col-6-small">
                                            <input type="checkbox" id="cat-${seq}" name="cat" value="${cat.para.uuid}" <logic:equal name="cat" property="selected" value="true">checked</logic:equal>>
                                            <label for="cat-${seq}">${cat.para.name}</label>
                                        </div>
                                    </logic:iterate>
                                </div>
                                </logic:notEmpty>
                            </div>
                            <div class="col-12">
                                <ul class="actions fit">
                                    <li><input type="submit" class="button primary" value="<bean:message key="btn.save" bundle="ae21studio"/>"/></li>
                                    <li><a href="${pageContext.request.contextPath}/panel/${langCode}/dashboard.html" class="button   "><bean:message key="btn.back" bundle="ae21studio"/></a></li>
                                </ul>
                            </div>
                        </div>
                    </form>
                </logic:notEmpty>
        </div>
    </section>
</div>
                
<script src="${pageContext.request.contextPath}/assets/plugin/dropzone/dropzone.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/plugin/dropzone/upload.js" type="text/javascript"></script>
<script>
                                Dropzone.autoDiscover = false;
                                $(document).ready(function () {
                                    // showSnack("Testing");

                                    //openLink('#en-tab', 0);
                                    
                                    initUploadImageURL('#upload_photo', '#photo', '${pageContext.request.contextPath}/panel/upload/file/new/submit.html', '');
                                });
</script>