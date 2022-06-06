<%-- 
    Document   : index
    Created on : 2022年6月6日, 下午10:35:30
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
                <h2><bean:message key="title.panel.category" /></h2>
                <div class="config">
                    <a href="${pageContext.request.contextPath}/panel/${langCode}/dashboard.html" class="icon solid fa-times"></a>
                </div>
            </header>
        </div>
    </section>
</div>
