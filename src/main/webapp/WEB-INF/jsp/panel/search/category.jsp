<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner">

            <header class="config-header">
                <h2><bean:message key="menu.photo.search.cat" /></h2>
                <div class="config">
                    <a href="${pageContext.request.contextPath}/panel/${langCode}/dashboard.html" class="icon solid fa-times"></a>
                </div>
            </header>
                <div class="row">
                    <div class="col-12">
                        <input name="search"  id="search" placeholder="<bean:message key="label.search.tag" />" type="text"  value="" maxlength="250" onkeydown="filterCatList(this.value);"  />
                    </div>
                </div>
                    <br>
                    
                        <div class="row">
                        <logic:notEmpty name="tagList" scope="request">
                            <logic:iterate id="hashtag"   name="tagList" scope="request">
                                <div class="col-2 col-4-medium col-6-small">
                                    <a href="${pageContext.request.contextPath}/panel/${langCode}/search/query.html?key=${hashtag.name}"  class="filter-item cover-img-container"  >
                                        <div class="image fit">
                                            <img src="${pageContext.request.contextPath}/images/icon/folder.png"/>
                                        </div>
                                            <span>${hashtag.name}</span>
                                    </a>
                                </div>
                            </logic:iterate>
                        </logic:notEmpty>
                    </div>
        </div>
    </section>
</div>

