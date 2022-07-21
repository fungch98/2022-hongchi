<%-- 
    Document   : dashboard
    Created on : 2022年6月1日, 下午07:24:33
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner trim">

            
            <header class="config-header">
                <h2><bean:message key="system.title" /></h2>
                    <div class="config">
                        <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/new/edit.html" class="icon solid fa-plus-circle"></a>
                </div>
            </header>
                
                <div class="row">
                    <div class="col-12">
                        <input name="search"  id="search" placeholder="<bean:message key="label.search.photo" />" type="text"  value="" maxlength="250"  />
                    </div>
                </div>
                    <br>
                    <div class="row gtr-25 photo-list-container">
                        <logic:notEmpty name="prodList"  scope="request" >
                            <logic:iterate id="photo"  name="prodList"  scope="request" >
                                <div class="col-2 col-3-medium col-6-small ">
                                    <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/view.html" class="photo-preview-container">
                                        <div class="image fit">
                                            <logic:equal name="photo" property="productCreateMethod" value="1">
                                                <img src="${pageContext.request.contextPath}${photo.productUrl}" id="photo-preview" onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'" />
                                            </logic:equal>
                                            <logic:equal name="photo" property="productCreateMethod" value="2">
                                                <img src="${photo.productUrl}" id="photo-preview" onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'" />
                                            </logic:equal>
                                        </div>
                                        <div class="overlay">
                                            <div class="text">${photo.name}
                                                <logic:notEmpty name="photo" property="productCat">
                                                    <br><br>${photo.productCat}
                                                </logic:notEmpty>
                                            </div>
                                          </div>
                                        </a>
                                </div>
                            </logic:iterate>
                        </logic:notEmpty>
                    </div>
        </div>
    </section>
</div>
