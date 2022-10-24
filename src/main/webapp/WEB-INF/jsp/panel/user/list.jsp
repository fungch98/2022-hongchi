<%-- 
    Document   : list
    Created on : 2022年6月23日, 下午06:15:19
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner">

            <header class="config-header">
                <h2><bean:message key="title.panel.user" /></h2>
                <div class="config">
                    <a href="${pageContext.request.contextPath}/panel/${langCode}/dashboard.html" class="icon solid fa-times"></a>
                </div>
            </header>
                
                <table id="user-list" class="common-table"  data-page-length="25" width="100%">
                    <thead>
                        <tr>
                            <th><bean:message key="label.user.username" bundle="ae21studio" /></th>
                            <th><bean:message key="label.user.google" bundle="ae21studio" /></th>
                            <th><bean:message key="label.user.display" bundle="ae21studio" /></th>
                            <th><bean:message key="label.status" bundle="ae21studio" /></th>
                            <th><bean:message key="label.user.loginDate" bundle="ae21studio" /></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <logic:notEmpty name="userList" scope="request">
                            <logic:iterate id="user" name="userList" scope="request">                               
                                <tr>
                                    <td>
                                        ${user.username}
                                    </td>
                                   
                                    <td>
                                        ${user.email} 
                                    </td>
                                     <td>
                                        ${user.displayName}
                                    </td>
                                    <td>
                                        <bean:message key="label.status.user.${user.userStatus}" bundle="ae21studio" />
                                    </td>
                                     <td>
                                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${user.loginDate}"/>
                                    </td>
                                    <td class="action-bar">
                                        <a href="${pageContext.request.contextPath}/panel/user/${langCode}/info/${user.uuid}/edit.html" class="icon solid fa-edit blue"></a>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </logic:notEmpty>
                        
                    </tbody>
                </table>
        </div>
    </section>
</div>
                
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/plugin/datatables/datatables.min.js"></script>
<script type='text/javascript'>
    $(document).ready(function () {
        $('#user-list').DataTable({
            responsive: true,
            searching: true,
            "bLengthChange": false,
            "order": [],
            "columns": [{"width": "20%"},{"width": "20%"},{"width": "20%"},null,null,{"width":"4em"}],
            "columnDefs": [
                {"className": "dt-right", "targets": [5]},
                {"className": "dt-center", "targets": [3,4]}
            ],
            language: {
                "info": "<bean:message key="dataTable.info"  bundle="ae21studio"/>",
                "lengthMenu": "<bean:message key="dataTable.length"  bundle="ae21studio"/>",
                "zeroRecords": "<bean:message key="dataTable.noRecord"  bundle="ae21studio"/>",
                "emptyTable": "<bean:message key="dataTable.empty"  bundle="ae21studio"/>",
                "infoEmpty": "<bean:message key="dataTable.search"  bundle="ae21studio"/>",
                "infoFiltered": "<bean:message key="dataTable.search"  bundle="ae21studio"/>",
                "paginate": {
                    "first": "<bean:message key="dataTable.first"  bundle="ae21studio"/>",
                    "last": "<bean:message key="dataTable.last"  bundle="ae21studio"/>",
                    "next": "<bean:message key="dataTable.next"  bundle="ae21studio"/>",
                    "previous": "<bean:message key="dataTable.previous"  bundle="ae21studio"/>"
                },
                search: "<bean:message key="dataTable.search"  bundle="ae21studio"/>"
            }
        });

    });
</script>  