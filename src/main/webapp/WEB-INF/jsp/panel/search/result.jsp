
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner trim">

            
            <header class="config-header">
                <h2><bean:message key="label.search.photo" /></h2>
                    <div class="config">
                        <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/new/edit.html" class="icon solid fa-plus-circle"></a>
                </div>
            </header>
                
                <div class="row">
                    <div class="col-12">
                        <form id="search_form" action="${pageContext.request.contextPath}/panel/${langCode}/search/query.html" method="POST">
                        <div class="search_row">
                            
                            <input name="key"  id="key" placeholder="<bean:message key="label.search.photo" />" type="text"  value="${key}" maxlength="250"  />
                            <button class="primary"  onclick="search('#search_form');"><i class="icon solid fa-search"></i></button>
                            
                        </div>
                            </form>
                        
                    </div>
                </div>
                    <br>
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
                            
                                <logic:notEmpty name="SEARCH_RESULT" scope="session">
                                    <div class="search_footer">
                                        <logic:notEqual name="SEARCH_RESULT" property="curPage" scope="session" value="0">
                                            <a href="#" class="button primary"><bean:message key="btn.previous" bundle="ae21studio"/></a>
                                        </logic:notEqual>
                                        <logic:greaterThan name="SEARCH_RESULT" property="page" scope="session" value="0">
                                            <div class="page-container">
                                                <logic:notEmpty name="SEARCH_RESULT" property="pageList" scope="session">
                                                    <logic:iterate id="pageItemList" name="SEARCH_RESULT" property="pageList" scope="session" indexId="pageSeq">
                                                        <a href="${pageContext.request.contextPath}/panel/${langCode}/search/${pageSeq}/page.html" class="<logic:equal name="SEARCH_RESULT" property="curPage" scope="session" value="${pageSeq}">active</logic:equal>" >${(pageSeq+1)}</a>
                                                        
                                                        
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                            </div>
                                        </logic:greaterThan>
                                        <logic:lessThan name="SEARCH_RESULT" property="curPage" scope="session" value="${(SEARCH_RESULT.page-1)}">
                                            <a href="${pageContext.request.contextPath}/panel/${langCode}/search/next/page.html" class="button primary"><bean:message key="btn.next" bundle="ae21studio"/>  </a>
                                        </logic:lessThan>
                                    </div>
                                </logic:notEmpty>
                           
                        </div>
                    </div>
        </div>
    </section>
</div>