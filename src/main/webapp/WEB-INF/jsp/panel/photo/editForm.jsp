<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div class="row">
                            <jsp:include page="../../warningMsg.jsp" flush="false"></jsp:include>

                                <div class="col-6 col-6-medium col-12-small">
                                    <div class="row gtr-25">
                                        <div class="col-12">
                                            <label for="photo-name"><bean:message key="label.photo.name"/></label>
                                        <input type="text" id="photo-name" name="photo-name" value="${photo.name}" maxlength="100"/>
                                    </div>
                                        <logic:notEmpty name="UserAuthorizedLogin" >
                                            <logic:equal name="UserAuthorizedLogin"  property="isAdmin" value="1">
                                                <div class="col-4 col-6-medium">
                                                    <label for="status"><bean:message key="label.status" bundle="ae21studio"/></label>
                                                    <select id="status" name="status">

                                                        <option value="1" <logic:equal name="photo" property="status" value="1">Selected</logic:equal> ><bean:message key="label.status.1" bundle="ae21studio"/></option>
                                                        <option value="0" <logic:equal name="photo" property="status" value="0">Selected</logic:equal>><bean:message key="label.status.0" bundle="ae21studio"/></option>
                                                        </select>
                                                    </div>
                                                    <div class="col-4 col-6-medium">
                                                        <label for="isShare"><bean:message key="label.share"/></label>
                                                    <select id="isShare" name="isShare">

                                                        <option value="1" <logic:equal name="photo" property="isShare" value="1">Selected</logic:equal> ><bean:message key="label.isShare.1" bundle="ae21studio"/></option>
                                                        <option value="0" <logic:equal name="photo" property="isShare" value="0">Selected</logic:equal>><bean:message key="label.isShare.0" bundle="ae21studio"/></option>
                                                        </select>
                                                    </div>
                                                    <div class="col-4 col-6-medium">
                                                        <label for="folder"><bean:message key="label.folder"/></label>
                                                    <select id="folder" name="folder">
                                                        <logic:notEmpty name="catList" scope="request">
                                                            <logic:iterate  name="catList" scope="request" id="cat" indexId="seq">
                                                                <option value="${cat.para.uuid}" <logic:equal name="cat" property="selected" value="true">selected</logic:equal> >${cat.para.name}</option>
                                                            </logic:iterate>
                                                        </logic:notEmpty>
                                                    </select>
                                                </div>
                                            </logic:equal>
                                        </logic:notEmpty>
                                    <div class="col-12 col-12-medium">
                                        <label for="desc"><bean:message key="label.photo.desc"/></label>
                                        <textarea id="desc" name="desc" rows="3">${photo.desc}</textarea>
                                
                                    </div>   
                                    <div class="col-12">
                                            <label for="hashtag"><bean:message key="label.tag.name"/></label>
                                            <input type="text" id="hashtag" name="hashtag" value="${hashtag}" maxlength="1000" placeholder="<bean:message key="label.tag.desc" />"/>
                                    </div>
                                    
                                  
                                    
                                   
                                    <logic:notEqual name="photo" property="productCreateMethod" value="3">
                                    <div class="col-12">
                                        <br>
                                        <div id="upload_photo" class="dropzone needsclick">
                                            <div class="fallback"></div>
                                            <div class="dz-message needsclick">
                                                <span class="note needsclick"><bean:message key="label.upload.caption" bundle="ae21studio"/></span>
                                            </div>
                                        </div>
                                    </div>
                                    </logic:notEqual>
                                    
                                     <div class="col-12">
                                        <input type="hidden" id="photo" name="photo" value="${photo.productUrl}" onchange="refreshImage(this);"  placeholder="" />
                                        <input type="hidden" id="photo-uuid" name="photo-uuid" value="${photo.productUUID}"   placeholder="" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-6 col-6-medium col-12-small">
                                <div class="row">
                                    <br>
                                    <div class="col-12 col-12-medium">
                                        <div class="image fit">
                                            <logic:equal name="photo" property="productCreateMethod" value="1">
                                                    <img src="${pageContext.request.contextPath}${photo.productUrl}" id="photo-preview" onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'" />
                                            </logic:equal>
                                            <logic:equal name="photo" property="productCreateMethod" value="2">
                                                <img src="${photo.productUrl}" id="photo-preview" onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'" />
                                            </logic:equal>
                                            <logic:equal name="photo" property="productCreateMethod" value="3">
                                                    <img src="${pageContext.request.contextPath}${photo.productUrl}" id="photo-preview" onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'" />
                                            </logic:equal>
                                        </div>
                                    </div>   
                                </div>
                                
                                
                                
                            </div>
                                    <%/*
                            <div class="col-12">
                                <label>圖片分類</label>
                                <logic:notEmpty name="catList" scope="request">
                                <div class="row">
                                    <logic:iterate  name="catList" scope="request" id="cat" indexId="seq">
                                        <bean:define name="cat" property="para" id="folder"/>
                                        <div class="col-2 col-3-medium col-4-small">
                                            <input type="checkbox" id="cat-${seq}" name="cat" value="${cat.para.uuid}" <logic:equal name="cat" property="selected" value="true">checked</logic:equal>>
                                            <label for="cat-${seq}" class="folder-label-${cat.para.parentId}" >${cat.para.name}</label>
                                        </div>
                                    </logic:iterate>
                                </div>
                                </logic:notEmpty>
                            </div>
                                   */ %>
                            <div class="col-12">
                                <ul class="actions fit">
                                    <li><input type="submit" class="button primary" value="<bean:message key="btn.save" bundle="ae21studio"/>"/></li>
                                    <li><a id="btn-close" href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/view.html" class="button   "><bean:message key="btn.back" bundle="ae21studio"/></a></li>
                                </ul>
                            </div>
                        </div>