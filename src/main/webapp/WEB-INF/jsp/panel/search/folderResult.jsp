
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner trim">
            <header class="config-header">
                <h2><bean:message key="label.search.photo" /><logic:notEqual name="uuid" value="root"> - ${family.current.name}</logic:notEqual></h2>
                    <div class="config">
                        <logic:notEqual name="uuid" value="root">
                            
                            <logic:notEmpty name="UserAuthorizedLogin" >
                                <logic:equal name="UserAuthorizedLogin"  property="isAdmin" value="1">
                                    <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/${family.current.uuid}/${family.parent.url}/edit.html" class="icon solid fa-edit"></a>
                                    <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/new/${family.current.url}/edit.html" class="icon solid fa-folder-plus"></a>
                                </logic:equal>
                            </logic:notEmpty>
                            
                        </logic:notEqual>
                        <logic:equal name="uuid" value="root">
                            <logic:notEmpty name="UserAuthorizedLogin" >
                                <logic:equal name="UserAuthorizedLogin"  property="isAdmin" value="1">
                                    <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/new/root/edit.html" class="icon solid fa-folder-plus"></a>
                                </logic:equal>
                            </logic:notEmpty>
                            
                        </logic:equal>
                    <logic:notEqual name="uuid" value="root">
                        
                        <logic:notEmpty name="UserAuthorizedLogin" >
                            <logic:equal name="UserAuthorizedLogin"  property="isAdmin" value="1">
                                <a href="${pageContext.request.contextPath}/panel/editor/${langCode}/new/new/dashboard.html?folder=${family.current.url}" class="icon solid fa-plus-circle"></a>
                            </logic:equal>
                        </logic:notEmpty>
                        <!--
                        <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/new/edit.html?folder=${family.current.url}" class="icon solid  fa-cloud-upload-alt"></a>
                        -->
                        </logic:notEqual>
                </div>
            </header>
                        <logic:notEmpty name="family">
                            <div class="footer">
                                <logic:notEmpty name="family" property="path">
                                    <span> <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/root/view.html">主目錄</a></span>
                                    <logic:iterate id="path" name="family" property="path">
                                        <span>/</span><span><a href="${pageContext.request.contextPath}/panel/folder/${langCode}/${path.url}/view.html"> ${path.name}</a></span>
                                    </logic:iterate>
                                </logic:notEmpty>
                            </div>
                        </logic:notEmpty>
                        <logic:equal name="uuid" value="root">
                            <div class="row">
                                <logic:notEmpty name="family">
                                    <logic:notEmpty name="family" property="subFolder">
                                        <logic:iterate id="folder"  name="family" property="subFolder">
                                            <div class="col-1 col-2-medium col-4-small">
                                    <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/${folder.url}/view.html"  class="filter-item cover-img-container"  >
                                        <div class="image fit">
                                            <img src="${pageContext.request.contextPath}/images/icon/folder.png"/>
                                        </div>
                                            <span class="folder-title">${folder.name}</span>
                                    </a>
                                </div>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </logic:notEmpty>
                            </div>
                        </logic:equal>     
                        <logic:notEqual name="uuid" value="root">
            <div class="row">
                <logic:notEmpty name="family">
                    <logic:notEmpty name="family" property="subFolder">
                        <div class="col-3 col-4-medium">

                            <ul class="alt">
                                <logic:iterate id="folder"  name="family" property="subFolder">
                                    <li><a href="${pageContext.request.contextPath}/panel/folder/${langCode}/${folder.url}/view.html"><i class="icon solid fa-folder"></i>  ${folder.name}</a></li>
                                    </logic:iterate>
                            </ul>

                        </div>
                    </logic:notEmpty>
                </logic:notEmpty>
                 <logic:notEmpty name="family">
                     <logic:notEmpty name="family" property="subFolder">
                         <div class="col-9 col-8-medium">
                     </logic:notEmpty>
                     <logic:empty name="family" property="subFolder">   
                         <div class="col-12">
                     </logic:empty>
                 </logic:notEmpty>
                
                    <div class="row gtr-25 photo-list-container">
                        <logic:notEmpty name="prodList"  scope="request" >
                            <logic:iterate id="photo"  name="prodList"  scope="request" >
                                <c:set var="photo" value="${photo}" scope="request"/>
                                <jsp:include page="../page/imageView.jsp" flush="false"/>
                            </logic:iterate>
                        </logic:notEmpty>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            
                                <logic:notEmpty name="SEARCH_FOLDER_RESULT" scope="session">
                                    <div class="search_footer">
                                        <logic:notEqual name="SEARCH_FOLDER_RESULT" property="curPage" scope="session" value="0">
                                            <a href="#" class="button primary"><bean:message key="btn.previous" bundle="ae21studio"/></a>
                                        </logic:notEqual>
                                        <logic:greaterThan name="SEARCH_FOLDER_RESULT" property="page" scope="session" value="0">
                                            <div class="page-container">
                                                <logic:notEmpty name="SEARCH_FOLDER_RESULT" property="pageList" scope="session">
                                                    <logic:iterate id="pageItemList" name="SEARCH_FOLDER_RESULT" property="pageList" scope="session" indexId="pageSeq">
                                                        <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/search/${pageSeq}/page.html" class="<logic:equal name="SEARCH_FOLDER_RESULT" property="curPage" scope="session" value="${pageSeq}">active</logic:equal>" >${(pageSeq+1)}</a>
                                                        
                                                        
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                            </div>
                                        </logic:greaterThan>
                                        <logic:lessThan name="SEARCH_FOLDER_RESULT" property="curPage" scope="session" value="${(SEARCH_FOLDER_RESULT.page-1)}">
                                            <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/search/next/page.html" class="button primary"><bean:message key="btn.next" bundle="ae21studio"/>  </a>
                                        </logic:lessThan>
                                    </div>
                                </logic:notEmpty>
                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
                        </logic:notEqual>
    </section>
</div>
