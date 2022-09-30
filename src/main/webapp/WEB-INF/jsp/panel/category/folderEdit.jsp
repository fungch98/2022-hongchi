<%-- 
    Document   : edit
    Created on : 2022年6月14日, 下午10:13:02
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner">

            <header class="config-header">
                <h2><bean:message key="title.panel.folder" /></h2>
                <div class="config">
                    <logic:notEmpty name="uuid">
                        <logic:notEqual name="uuid" value="new">
                             <logic:notEmpty name="UserAuthorizedLogin" >
                                 <logic:equal name="UserAuthorizedLogin"  property="isAdmin" value="1">
                                     <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/${uuid}/${parentUUID}/edit/delete.html" onclick="return showDeleteMessage('<bean:message key="confrim.delete.folder"/>');" class="icon solid fa-trash"></a>
                                     <logic:notEmpty name="isEmpty" >
                                         <logic:equal name="isEmpty"   value="Y">
                                             <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/${uuid}/${parentUUID}/edit/delete.html" onclick="return showDeleteMessage('<bean:message key="confrim.delete.folder"/>');" class="icon delete solid fa-trash"></a>
                                         </logic:equal>
                                     </logic:notEmpty>
                                     
                                 </logic:equal>
                             </logic:notEmpty>
                            
                        </logic:notEqual>
                    </logic:notEmpty>
                    <a href="${pageContext.request.contextPath}/panel/folder/${langCode}/${parentUUID}/view.html" class="icon solid fa-times"></a>
                </div>
            </header>
            <logic:notEmpty name="parent">
                            <div class="footer">
                                <span><b>上層目錄: </b><a href="${pageContext.request.contextPath}/panel/folder/${langCode}/$parentUUID}/view.html"> ${parent.name}</a></span>
                            </div>
                        </logic:notEmpty>
                
            <logic:empty name="category" scope="request">
                <h3><bean:message key="ERROR.ACCESS" bundle="error"/></h3>
            </logic:empty>
            <logic:notEmpty name="category" scope="request">
                 <form action="${pageContext.request.contextPath}/panel/folder/${langCode}/${uuid}/${parentUUID}/edit/save.html" method="POST" >
                    <div class="row">
                        <jsp:include page="../../warningMsg.jsp" flush="false"></jsp:include>
                    </div>
                    <div class="row">
                        <div class="col-6 col-6-medium col-12-small">
                            <label for="name">名稱</label>
                            <input name="name"  id="name" placeholder="名稱" type="text" required="" value="${category.name}" maxlength="100"  />
                        </div>
                        <div class="col-6 col-6-medium col-12-small">
                            <label for="url"><bean:message key="label.url" bundle="ae21studio"/><span class="required">*</span>(<bean:message key="label.url.desc" bundle="ae21studio"/>)</label>
                            <input type="text" name="url" id="url" class="small-plac" placeholder="<bean:message key="label.url.desc" bundle="ae21studio"/>" value="${category.url}" maxlength="100"/>
                       </div>
                         <div class="col-12">
                            <label for="desc">簡介</label>
                            <textarea id="desc" name="desc" rows="5">${category.desc}</textarea>
                        </div>    
                        <div class="col-12">
                            <ul class="actions fit">

                                <li><input type="submit" class="button primary" value="<bean:message key="btn.save" bundle="ae21studio"/>"/></li>
                            <li><a href="${pageContext.request.contextPath}/panel/folder/${langCode}/${parentUUID}/view.html" class="button   "><bean:message key="btn.back" bundle="ae21studio"/></a></li>
                        </ul>
                        </div>
                    </div>
                 </form>
            </logic:notEmpty>
        </div>
    </section>
</div>
