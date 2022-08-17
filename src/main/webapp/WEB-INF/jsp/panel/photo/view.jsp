<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner">

            <header class="config-header">
                <h2><bean:message key="label.photo.title" /><logic:notEmpty name="photo" scope="request"> - ${photo.name}</logic:notEmpty></h2>
                    <div class="config">
                        <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/new/edit.html" class="icon solid fa-plus-circle"></a>
                    <logic:notEmpty name="photo" scope="request">
                        <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/hashtag.html" class="icon solid fa-tags"></a>
                        <logic:equal name="photo" property="productCreateMethod" value="1">
                            <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/edit.html" class="icon solid fa-edit"></a>
                        </logic:equal>
                        
                    </logic:notEmpty>
                        
                        <a href="${pageContext.request.contextPath}/panel/${langCode}/dashboard.html" class="icon solid fa-times"></a>
                    </div>
                
            </header>
                    <logic:notEmpty name="photo" scope="request">
                    <div class="footer">
                        <span><b><bean:message key="label.photo.modify"/>:</b> ${photo.modifyUser.displayName} (<fmt:formatDate pattern = "yyyy-MM-dd HH:mm"  value = "${photo.modifyDate}" />)</span>
                        <span><b><bean:message key="label.photo.create"/>:</b> ${photo.createUser.displayName} (<fmt:formatDate pattern = "yyyy-MM-dd HH:mm"  value = "${photo.createDate}" />)</span>
                        
                        
                        
                    </div>
                    </logic:notEmpty>
                    
                <logic:empty name="photo" scope="request">
                    <h3><bean:message key="ERROR.ACCESS" bundle="error"/></h3>
                </logic:empty>
                <logic:notEmpty name="photo" scope="request">
                    <div class="row">
                        <div class="col-7 col-6-medium col-12-small">
                            <div class="image fit display-view">
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
                        <div class="col-5 col-6-medium col-12-small">
                            <logic:notEmpty name="photo" property="desc">
                                <b><bean:message key="label.photo.desc"/>:</b>
                            <pre>${photo.desc}</pre>
                            </logic:notEmpty>
                            
                            <b><bean:message key="label.photo.category"/>:</b>
                            <div class="photo-category-container">
                                <logic:notEmpty name="catList">
                                    <logic:iterate id="cat" name="catList" scope="request">
                                        <a href="${pageContext.request.contextPath}/panel/${langCode}/search/query.html?key=${cat.name}">#${cat.name}</a>
                                    </logic:iterate>
                                </logic:notEmpty>
                                
                            </div>
                            
                            <div class="tag-container">
                                <logic:notEmpty name="hashList">
                                    <logic:iterate id="tag" name="hashList" scope="request">
                                        <a href="#">#${tag.name}</a>
                                    </logic:iterate>
                                </logic:notEmpty>
                                
                            </div>
                            <ul class="actions stacked fit">
                                <li><a href="${pageContext.request.contextPath}/panel/upload/file/${photo.productUUID}/download.html" class="button primary"><i class="icons fa solid fa-download"></i> <bean:message key="btn.download" bundle="ae21studio"/></a></li>
				
                            </ul>
                        </div>
                    </div>
                </logic:notEmpty>
        </div>
    </section>
</div>