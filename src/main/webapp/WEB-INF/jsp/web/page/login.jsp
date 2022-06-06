<%-- 
    Document   : login
    Created on : 2022年6月1日, 下午06:07:01
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner">

            <header class="major">
                <h2><bean:message key="btn.login" bundle="ae21studio"/></h2>
            </header>

            <div class="row">
                <div class="col-6 col-12-small split left center">
                     <a href="https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com%2Fauth%2Fuserinfo.email+https://www.googleapis.com%2Fauth%2Fuserinfo.profile&amp;state=${isPropertyOwner}&amp;redirect_uri=${googleConfig.host}/auth/google/oauth2callback.html&amp;response_type=code&amp;client_id=${googleConfig.googleId}" class="login-google">
                         <img  src="${pageContext.request.contextPath}/images/google.png" alt=" <bean:message key="btn.social.google" bundle="ae21studio"/>"/>
                    </a>
                </div>
                <div class="col-6 col-12-small split">
                    <form action="${pageContext.request.contextPath}/auth/${langCode}/login/submit.html" method="POST" autocomplete="off" >
                        <div class="row">
                            <jsp:include page="../../warningMsg.jsp" flush="false"></jsp:include>
                                <div class="col-12">

                                    <input name="username" class="alt" id="username" placeholder="登錄名" type="text" required="" autocomplete="off" />
                                </div>
                                <div class="col-12">
                                    <label for="password"></label>
                                    <input class="alt" name="password" id="password" placeholder="Password" type="password"  required="" autocomplete="new-password"/>
                                </div>

                                <div class="col-12">
                                    <ul class="actions fit">

                                        <li><input type="submit" class="button primary" value="<bean:message key="btn.login" bundle="ae21studio"/>"/></li>
                                    <li><a href="${pageContext.request.contextPath}/index.html" class="button   "><bean:message key="btn.back"/></a></li>
                                </ul>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>
