
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

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
