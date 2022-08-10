<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="item-list-container">
    <div class="search_row alt">
        <input name="key"  id="search-ajax-key" placeholder="<bean:message key="label.search.photo" />" type="text"  value="" maxlength="250" onchange="photoSearch(this.value);"  />
        <button class="primary "  onclick="photoSearchTarget('search-ajax-key');"><i class="icon solid fa-search"></i></button>
    </div>
    
    <div class="tag-container">
        <logic:notEmpty name="catList" >
            <logic:iterate id="cat" name="catList">
                <a href="#" onclick="photoSearch('${cat.name}');return false;" class="alt3">#${cat.name}</a>
            </logic:iterate>
        </logic:notEmpty>
        
    </div>
</div>
<hr>

<div id="search-photo-result-container" class="item-list-image-container">
    <jsp:include page="search/imageResult.jsp"/>
</div>