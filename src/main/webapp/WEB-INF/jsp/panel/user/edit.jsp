<%-- 
    Document   : edit
    Created on : 2022年6月23日, 下午08:55:46
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
                <h2><bean:message key="title.panel.user" /><logic:notEmpty name="editUser" scope="request"> - ${editUser.displayName}</logic:notEmpty></h2>
                    <div class="config">
                        <a href="${pageContext.request.contextPath}/panel/user/${langCode}/index.html" class="icon solid fa-times"></a>
                </div>
            </header>

            <logic:empty name="editUser" scope="request">
                <h3><bean:message key="ERROR.ACCESS" bundle="error"/></h3>
            </logic:empty>
            <logic:notEmpty name="editUser" scope="request">

                <div class="row">
                    <logic:notEmpty name="SAVE_RESULT_SRC">
                        <logic:equal name="SAVE_RESULT_SRC" value="ACTION">
                             <jsp:include page="../../warningMsg.jsp" flush="false"></jsp:include>
                        </logic:equal>
                    </logic:notEmpty>
                    <logic:notEmpty name="SUCCESS.AUTH.EDIT">
                        <div class="msg success">
                            <b><bean:message key="label.success" bundle="error"/></b> <bean:message key="SUCCESS.OK" bundle="error"/>
                        </div>
                    </logic:notEmpty>
                   
                    </div>
                    <div class="row">
                        <div class="col-6 col-12-medium split left">
                            <header class="subtitle">
                                <h3><bean:message key="label.user.info" bundle="ae21studio"/></h3>
                            </header>
                        <form action="${pageContext.request.contextPath}/panel/user/${langCode}/info/${uuid}/edit/save.html" method="POST" >

                            <div class="row alt">
                                <logic:notEmpty name="SAVE_RESULT_SRC">
                                    <logic:equal name="SAVE_RESULT_SRC" value="INFO">
                                         <jsp:include page="../../warningMsg.jsp" flush="false"></jsp:include>
                                    </logic:equal>
                                </logic:notEmpty>
                                <div class="col-12">
                                    <label for="username"><bean:message key="label.user.google" bundle="ae21studio"/>:</label> <span>${editUser.email} (${editUser.socialId})</span>
                                </div>
                                <div class="col-12">
                                    <label for="username"><bean:message key="label.user.username" bundle="ae21studio"/></label>
                                    <input name="username"  id="username" placeholder="<bean:message key="label.user.username" bundle="ae21studio"/>" type="text" required="" value="${editUser.username}" maxlength="100"  />
                                </div>
                                <div class="col-12">
                                    <label for="display"><bean:message key="label.user.display" bundle="ae21studio"/></label>
                                    <input name="display"  id="display" placeholder="<bean:message key="label.user.display" bundle="ae21studio"/>" type="text" required="" value="${editUser.displayName}" maxlength="100"  />
                                </div> 
                                <logic:equal name="UserAuthorizedLogin" property="isAdmin" value="1">
                                <div class="col-12">
                                    <label for="isAdmin"><bean:message key="label.user.isAdmin" bundle="ae21studio"/></label>
                                    <select id="isAdmin" name="isAdmin" required="">
                                        <option value="1" <logic:equal name="editUser" property="isAdmin" value="1">Selected="selected"</logic:equal>><bean:message key="label.user.isAdmin.1" bundle="ae21studio"/></option>
                                        <option value="0" <logic:equal name="editUser" property="isAdmin" value="0">Selected="selected"</logic:equal>><bean:message key="label.user.isAdmin.0" bundle="ae21studio"/></option>
                                    </select>
                                </div>
                                </logic:equal>
                                <div class="col-12">
                                    <ul class="actions fit">
                                        <li><input type="submit" class="button primary" value="<bean:message key="btn.save" bundle="ae21studio"/>"/></li>
                                        <li><a href="${pageContext.request.contextPath}/panel/user/${langCode}/index.html" class="button   "><bean:message key="btn.back" bundle="ae21studio"/></a></li>
                                    </ul>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-6 col-12-medium split">
                        <header class="subtitle">
                            <h3><bean:message key="label.user.changePwd" bundle="ae21studio"/></h3>
                        </header>
                        <form action="${pageContext.request.contextPath}/panel/user/${langCode}/password/${uuid}/edit/save.html" method="POST" >

                            <div class="row alt">
                                <logic:notEmpty name="SAVE_RESULT_SRC">
                                    <logic:equal name="SAVE_RESULT_SRC" value="PWD">
                                         <jsp:include page="../../warningMsg.jsp" flush="false"></jsp:include>
                                    </logic:equal>
                                </logic:notEmpty>
                                <div class="col-12">
                                    <label for="password"><bean:message key="label.user.password" bundle="ae21studio"/></label>
                                    <input name="password"  id="password" placeholder="<bean:message key="label.user.password" bundle="ae21studio"/>" type="password" required="" value="" maxlength="100"  />
                                </div>
                                <div class="col-12">
                                    <label for="confrim"><bean:message key="label.user.password.confrim" bundle="ae21studio"/></label>
                                    <input name="confrim"  id="confrim" placeholder="<bean:message key="label.user.password.confrim" bundle="ae21studio"/>" type="password" required="" value="" maxlength="100"  />
                                </div>
                              
                                <div class="col-12">
                                    <ul class="actions fit">
                                        <li><input type="submit" class="button primary" value="<bean:message key="btn.change" bundle="ae21studio"/>"/></li>
                                        <li><a href="${pageContext.request.contextPath}/panel/user/${langCode}/index.html" class="button   "><bean:message key="btn.back" bundle="ae21studio"/></a></li>
                                    </ul>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </logic:notEmpty>
        </div>
    </section>
</div>