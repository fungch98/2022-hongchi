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

   
<div class="row">
    <div class="col-12">
        <div class="search_footer">
            <logic:notEmpty name="SEARCH_EDITOR_RESULT" scope="session" >
                <logic:notEqual name="SEARCH_EDITOR_RESULT" property="curPage" scope="session" value="0">
                    <a href="#" onclick="photoSearchPage(${(SEARCH_EDITOR_RESULT.curPage-1)});return false;"; class="button primary"><bean:message key="btn.previous" bundle="ae21studio"/></a>
                </logic:notEqual>


                <logic:lessThan name="SEARCH_EDITOR_RESULT" property="curPage" scope="session" value="${(SEARCH_EDITOR_RESULT.page-1)}">
                    <a href="#" onclick="photoSearchPage(${(SEARCH_EDITOR_RESULT.curPage+1)});return false;";  class="button primary"><bean:message key="btn.next" bundle="ae21studio"/>  </a>
                </logic:lessThan>
            </logic:notEmpty>
            <logic:empty name="userPhotoList" scope="request" >
                <bean:message key="label.no_found" bundle="ae21studio"/>
            </logic:empty>
        </div>
        <br><br>
    </div>
</div>
