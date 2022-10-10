<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="item-character-view" class="item-detail-container">
    <section>
        <header>
            <h3>校本角色</h3>
        </header>
        <div id='character-main-container' class='item-character-container active'>
            <div class='row gtr-25'>
            <logic:notEmpty name="charList" scope="request">
                <logic:iterate id="charItem" name="charList" scope="request">
                    
                    <div class='col-4 col-6-large col-6-medium col-6-small'>
                        <a href="#" onclick="addItem('role','${charItem.id}');return false;" class="align-center">
                        <div class="item-cover-container role">
                            <div class='item-cover ' style='background-image: url("${pageContext.request.contextPath}/images/role/${charItem.str02}");'>
                            </div>
                            
                        </div>
                            <p>${charItem.str01}</p>
                        </a>
                    </div>
                    
                </logic:iterate>
            </logic:notEmpty>
            </div>
        </div>
        <div id='character-emontion-container' class='item-character-container'>
            <div id='character-emontion-container-result' class='item-list-image-container'>

            </div>
            <ul class="actions special">
                <li>
                    <a href="#" onclick="backToRole();return false;" class="button">返回選擇角色</a>
                </li>
                
            </ul>
        </div>
        
    </section>
</div>
<div id="item-image-view" class="item-detail-container active">
    <div class="item-list-container">
        <div class="search_row alt">
            <input name="key"  id="search-ajax-key" placeholder="<bean:message key="label.search.photo" />" type="text"  value="" maxlength="250" onchange="photoSearch(this.value);"  />
            <button class="primary "  onclick="photoSearchTarget('search-ajax-key');"><i class="icon solid fa-search"></i></button>
        </div>
        <a href="#" onclick="accordionTag();return false;" class="alt4 accordion" ><i class="icon solid fa-tags"></i> <span class="open"><bean:message key="label.show.all" bundle="ae21studio"/></span><span class="close"><bean:message key="label.show.some" bundle="ae21studio"/></span></a>
        <div id="accordion-action-panel" class="accordion-panel alt">
            <div class="tag-container">
                <logic:notEmpty name="catList" >
                    <logic:iterate id="cat" name="catList">
                        <a href="#" onclick="photoSearch('${cat.name}');return false;" class="alt3">#${cat.name}</a>
                    </logic:iterate>
                </logic:notEmpty>

            </div>
        </div>
    </div>
    <hr>

    <div id="search-photo-result-container" class="item-list-image-container">
        <jsp:include page="search/imageResult.jsp"/>
    </div>
</div>
    <div id="item-material-view" class="item-detail-container">
        <section>
            <header>
                <h3>物件</h3>
            </header>
            <div id='material-main-container'>
                <div class='row gtr-25'>
                    <logic:notEmpty name="objList" scope="request">
                        <logic:iterate id="meterial" name="objList" scope="request">

                            <div class='col-3 col-6-large col-6-medium col-6-small'>
                                <a href="#" onclick="addItem('material','${meterial.id}');return false;">
                                <div class="item-cover-container">
                                    <div class='item-cover' style='background-image: url("${pageContext.request.contextPath}/images/material/${meterial.str02}");'>
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
