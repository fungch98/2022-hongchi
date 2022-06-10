<%-- 
    Document   : mainFrame
    Created on : 2022年5月31日, 下午06:15:55
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html>
    <head>
        <logic:notEmpty name="frameObj" property="title">
            <title> ${frameObj.title} | <bean:message key="title"/> </title>
        </logic:notEmpty>
        <logic:empty name="frameObj" property="title">
            <title><bean:message key="title"/></title>
        </logic:empty>
        <meta charset="utf-8" />
        <logic:notEmpty name="frameObj" property="desc">
            <meta name="description" itemprop="description" content="${frameObj.desc}" />
        </logic:notEmpty>
        <logic:notEmpty name="frameObj" property="keyword">
            <meta name="keywork" content="${frameObj.keyword}">
        </logic:notEmpty>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/common.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom.css" />
        <!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
        <!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->

        <meta name="author" content="<bean:message key="copyright" bundle="ae21studio"/>">
        <meta name="Copyright" content="&copy;${frameObj.year} <bean:message key="copyright" bundle="ae21studio"/>. All Rights Reserved">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon" />
        <link rel='icon' href='${pageContext.request.contextPath}/favicon.ico' type='image/x-icon'/>
        <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/favicon.ico">

        <link href="${pageContext.request.contextPath}/assets/plugin/dropzone/dropzone.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/assets/plugin/dropzone/upload.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/plugin/datatables/datatables.min.css"/>

        <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
        <script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
    </head>
    <body class="is-preload">

        <!-- Wrapper -->
        <div id="wrapper">

            <!-- Header -->
            <header id="header">

                <!-- Logo -->
                <div class="logo">
                    <a href="index.html" class="title"><img src="${pageContext.request.contextPath}/images/school_logo.png" alt="<bean:message key="title"/>"></a>
                </div>

                <!-- Nav -->
                <nav id="nav">
                    <ul>
                        <li><a href="index.html">主頁</a></li>
                        <logic:notEmpty name="UserAuthorizedLogin" >
                        <li>
                            <a href="#" class="dropdown">圖庫</a>
                            <ul>
                                <li><a href="#">創建圖片</a></li>
                                <li><a href="#">圖庫</a></li>
                            </ul>
                        </li>
                         <li>
                            <a href="#" class="dropdown">控制台</a>
                            <ul>
                                <li><a href="generic.html">用戶管理</a></li>
                                <li><a href="${pageContext.request.contextPath}/panel/category/${langCode}/index.html"><bean:message key="title.panel.category" /></a></li>
                                <li><a href="generic.html">HashTag管理</a></li>
                                <li><a href="generic.html">Generic</a></li>
                                <li><a href="elements.html">Elements</a></li>
                            </ul>
                         </li>
                       </logic:notEmpty>
                        <li>
                            <a href="#" class="dropdown"><bean:message key="lang.label" bundle="ae21studio"/></a>
                            <ul>
                                <logic:equal name="org.apache.struts.action.LOCALE" value="en" scope="session">
                                    
                                </logic:equal>
                                <logic:equal name="org.apache.struts.action.LOCALE" value="zh_TW" scope="session">
                                    
                                </logic:equal>
                                <logic:equal name="org.apache.struts.action.LOCALE" value="zh_CN" scope="session">
                                    
                                </logic:equal>
                                <li><a href="${pageContext.request.contextPath}/${pagePrefix}zh/${pageLink}.html"><bean:message key="lang.zh.full" bundle="ae21studio"/></a></li>
                                <li><a href="${pageContext.request.contextPath}/${pagePrefix}en/${pageLink}.html"><bean:message key="lang.en.full" bundle="ae21studio"/></a></li>
                            </ul>
                        </li>
                        <li>
                            <logic:notEmpty name="UserAuthorizedLogin" >
                                <a href="${pageContext.request.contextPath}/auth/${langCode}/logout.html">
                                    <logic:notEmpty name="UserAuthorizedLogin" >
                                        
                                    </logic:notEmpty>
                                    <bean:message key="btn.logout" bundle="ae21studio"/>
                                </a>
                            </logic:notEmpty>
                            <logic:empty name="UserAuthorizedLogin" >
                                <a href="${pageContext.request.contextPath}/auth/${langCode}/login.html"><bean:message key="btn.login" bundle="ae21studio"/></a>
                            </logic:empty>
                            
                        </li>
                    </ul>
                </nav>

            </header>

            <logic:notEmpty name="frameObj"  property="jspPath">
                <jsp:include page="${frameObj.jspPath}" flush="false"></jsp:include>
            </logic:notEmpty>

            <!-- Footer -->
            <footer id="footer">
                <div class="inner">
                    <section>
                        <h2><bean:message key="support.title" bundle="ae21studio"/></h2>
                        <div class="row">
                            <div class="col-6 col-12-small split left">
                                <p><bean:message key="support.office-hour" bundle="ae21studio"/></p>
                            </div>
                            <div class="col-6 col-12-small split">
                                <ul class="contact">
                                    <li class="icon solid fa-envelope"><bean:message key="support.email" bundle="ae21studio"/></li>
                                    <li class="icon solid fa-phone"><bean:message key="support.phone" bundle="ae21studio"/></li>
                                    <li class="icon brands fa-whatsapp"><bean:message key="support.wts" bundle="ae21studio"/></li>
                                    <li class="icon solid fa-home"><bean:message key="support.address" bundle="ae21studio"/></li>
                                </ul>
                            </div>
                        </div>
                        
                        
                       
                    </section>
                    
                </div>
                <div class="copyright">
                    <p><bean:message key="copyright.statement" bundle="ae21studio"/></p>
                </div>
            </footer>

        </div>

        <input type="hidden" id="rootpath" value="${pageContext.request.contextPath}/"/>

        <input type="hidden" id="upload_image_msg1"  value="<bean:message key="image.upload.msg1" bundle="ae21studio"/>" />
        <input type="hidden" id="upload_image_msg2"  value="<bean:message key="image.upload.msg2" bundle="ae21studio"/>"/>
        <input type="hidden" id="upload_image_msg3"  value="<bean:message key="image.upload.msg3" bundle="ae21studio"/>"/>
        <input type="hidden" id="upload_image_msg4"  value="<bean:message key="image.upload.msg4" bundle="ae21studio"/>"/>

        <!-- The Modal -->
        <div id="popup-modal" class="modal-container">

            <!-- Modal content -->
            <div class="modal-content">
                <span class="modal-close"><i class="icon fa-times-circle"></i></span>
                <div id="popup-modal-div" class="modal-content-div">
                    Somthing HTML code Here
                </div>
            </div>

        </div>           


        <!-- The actual snackbar -->
        <div id="snackbar"></div>     

        <!-- Scripts -->
        <!--<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>-->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.dropotron.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/browser.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/breakpoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/util.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

    </body>
</html>
