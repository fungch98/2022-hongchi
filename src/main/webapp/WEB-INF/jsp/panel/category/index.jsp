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
                
                <table id="cat-list" class="common-table"  data-page-length="25">
                    <thead>
                        <tr>
                            <td></td>
                            <td class="action-bar"></td>
                        </tr>
                    </thead>
                    <tbody>
                        <logic:notEmpty name="catList" scope="request">
                            <logic:iterate id="cat" name="catList" scope="request">                               
                                <tr>
                                    <td>
                                        ${cat.name}
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/panel/document/${cat.uuid}/edit.html" class="icon solid fa-edit blue"></a>
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
        $('#cat-list').DataTable({
            responsive: true,
            searching: true,
            "bLengthChange": false,
            "order": [],
            "columns": [{"width": "calc(100%-6em)"},{"width": "6em"}],
            "columnDefs": [
                {"className": "dt-center", "targets": [1]}
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
