<%-- 
    Document   : edit
    Created on : 2022年7月26日, 下午05:09:35
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner">

            <header class="config-header">
                <h2><bean:message key="label.tag.edit.title" /><logic:notEmpty name="tag" scope="request"> - ${tag.name}</logic:notEmpty></h2>
                    <div class="config">
                    <logic:notEqual name="uuid" value="new">
                        <logic:notEmpty name="prodList" scope="request">
                            <a href="${pageContext.request.contextPath}/panel/tag/${langCode}/${tag.uuid}/edit/delete.html" onclick="return showDeleteMessage('<bean:message key="confrim.delete.tag.exist"/>');" class="icon delete solid fa-trash"></a>
                        </logic:notEmpty>
                        <logic:empty name="prodList" scope="request">
                            <a href="${pageContext.request.contextPath}/panel/tag/${langCode}/${tag.uuid}/edit/delete.html" onclick="return showDeleteMessage('<bean:message key="confrim.delete.tag"/>');" class="icon delete solid fa-trash"></a>
                        </logic:empty>
                        
                    </logic:notEqual>
                        <a href="${pageContext.request.contextPath}/panel/tag/${langCode}/list.html" class="icon solid fa-times"></a>
                </div>
            </header>
                <logic:notEmpty name="tag" scope="request">
                    <div class="footer">
                        <span><b><bean:message key="label.photo.modify"/>:</b> ${tag.modifyUser.displayName} (<fmt:formatDate pattern = "yyyy-MM-dd HH:mm"  value = "${tag.modifyDate}" />)</span>
                        <span><b><bean:message key="label.photo.create"/>:</b> ${tag.createUser.displayName} (<fmt:formatDate pattern = "yyyy-MM-dd HH:mm"  value = "${tag.createDate}" />)</span>
                    </div>
                    </logic:notEmpty>
                
                <logic:empty name="tag" scope="request">
                    <h3><bean:message key="ERROR.ACCESS" bundle="error"/></h3>
                </logic:empty>
                <logic:notEmpty name="tag" scope="request">
                    
                    <form action="${pageContext.request.contextPath}/panel/tag/${langCode}/${uuid}/edit/save.html" method="POST" >
                    
                     <div class="row">
                            <jsp:include page="../../warningMsg.jsp" flush="false"></jsp:include>

                            <div class="col-8 col-12-medium">
                                <label for="tag-name"><bean:message key="label.tag.name"/></label>
                            <input type="text" id="tag-name" name="tag-name" value="${tag.name}" maxlength="100" required=""/>
                            </div>
                            <div class="col-12">
                                <ul class="actions">
                                    <li><input type="submit" class="button primary" value="<bean:message key="btn.save" bundle="ae21studio"/>"/></li>
                                    <li><a href="${pageContext.request.contextPath}/panel/tag/${langCode}/list.html" class="button   "><bean:message key="btn.back" bundle="ae21studio"/></a></li>
                                </ul>
                            </div>

                     </div>
                    </form>
                    <logic:notEmpty name="prodList" scope="request">
                        <hr>
                        <header>
                            <h3><bean:message key="label.tag.photo"/></h3>
                        </header>
                        <div class="row gtr-25">
                        <logic:iterate id="prod" name="prodList" scope="request">
                            <c:set var="photo" value="${prod}" scope="request"/>
                            <jsp:include page="../page/imageView.jsp" flush="false"/>
                        </logic:iterate>
                        </div>
                    </logic:notEmpty>
                    
                    
                </logic:notEmpty>
        </div>
    </section>
</div>