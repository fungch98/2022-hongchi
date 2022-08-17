
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div class="col-2 col-3-medium col-6-small ">
    <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/view.html" class="photo-preview-container">
        <article class="album">
            <logic:equal name="photo" property="productCreateMethod" value="1">
                <div class="cover" style="background-image: url('${pageContext.request.contextPath}${photo.productUrl}');">
                    
                </div>
                
            </logic:equal>
            <logic:equal name="photo" property="productCreateMethod" value="2">
                <div class="cover" style="background-image: url('${photo.productUrl}');">
                    
                </div>
            </logic:equal>
             <logic:equal name="photo" property="productCreateMethod" value="3">
                <div class="cover" style="background-image: url('${pageContext.request.contextPath}${photo.productUrl}');">
                    
                </div>
                
            </logic:equal>
        </article>
        
        <div class="overlay">
            <div class="text">${photo.name}
                <logic:notEmpty name="photo" property="productCat">
                    <br><br>${photo.productCat}
                </logic:notEmpty>
            </div>
          </div>
        </a>
</div>
