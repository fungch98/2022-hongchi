<%-- 
    Document   : imageResult
    Created on : 2022年8月8日, 下午11:19:09
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div class="row gtr-25">
                <logic:notEmpty name="userPhotoList">
                    <logic:iterate id="uPhoto" name="userPhotoList" scope="request" indexId="seq">
                        <div class="col-3 col-3-medium col-3-small">
                            <a href="#" class="alt" onclick="addItem('photo','${uPhoto.uuid}');return false;">
                            <logic:equal name="uPhoto" property="productCreateMethod" value="1">
                                <img src="${pageContext.request.contextPath}${uPhoto.productUrl}" class="images fit"/>
                             </logic:equal>
                             <logic:equal name="uPhoto" property="productCreateMethod" value="2">
                                 <img src="${uPhoto.productUrl}" class="images fit"/>
                             </logic:equal>
                             </a>
                        </div>
                    </logic:iterate>
                </logic:notEmpty>
               
            </div>