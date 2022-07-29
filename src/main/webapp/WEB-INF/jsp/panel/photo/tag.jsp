<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div id="main">
    <!-- Content -->
    <section class="main">
        <div class="inner">

            <header class="config-header">
                <h2><bean:message key="label.photo.hashtag" /><logic:notEmpty name="photo" scope="request"> - ${photo.name}</logic:notEmpty></h2>
                    <div class="config">
                    
                    <logic:notEmpty name="photo" scope="request">
                        <a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/view.html" class="icon solid fa-times"></a>
                    </logic:notEmpty>
                    </div>
            </header>
                    <div class="row gtr-25">
                        <div class="col-7 col-12-medium">
                            <div class="edit-tag-container list">
                                <header>
                                    <h3><bean:message key="label.photo.hashtag" /></h3>
                                </header>
                                <div class="row">
                                    <div class="col-12">
                                        
                                            <!--<label for="tag-input">加入Hashtag</label>-->
                                            <div class="search_row">
                                                <input type="text" id="tag-input" name="tag-input" value="" class="wbtn" maxlength="100" placeholder="<bean:message key="label.tag.add"/> <bean:message key="label.tag.desc"/>"/>
                                                <button class="primary"  onclick="addTagList('#tag-input','#tag-template','#tag-list-container')"><i class="icon solid fa-plus"></i></button>
                                            </div>
                                            
                                        
                                    </div>
                                    
                                </div>
                                <form id="tag-edit-form" action="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/hashtag/save.html" method="post" >
                                    <div class="edit-tag-list-container tag-container" id="tag-list-container">
                                        <logic:notEmpty name="hashtagList" scope="request">
                                            <logic:iterate id="hashtag"   name="hashtagList" scope="request">
                                                <div id="tag-${hashtag.uuid}">
                                                    <a href="#" onclick="removeFromTag('#tag-${hashtag.uuid}');" class="alt">${hashtag.name}<i class="icon solid fa-times blue right"></i></a>
                                                   <input type="hidden" value="${hashtag.uuid}" name="tagValue" />
                                               </div>
                                            </logic:iterate>
                                        </logic:notEmpty>
                                    </div>
                                     <ul class="actions">
                                            <li><input type="submit" class="button primary" value="<bean:message key="btn.save" bundle="ae21studio"/>"/></li>
                                            <li><a href="${pageContext.request.contextPath}/panel/photo/${langCode}/${photo.uuid}/view.html" class="button   "><bean:message key="btn.back" bundle="ae21studio"/></a></li>
                                    </ul>
                                </form>
                            </div>
                        </div>
                        <div class="col-5 col-12-medium">
                            <div class="edit-tag-container">
                                <table id="tag-list" class="common-table"  data-page-length="25">
                                    <thead>
                                    <th><bean:message key="label.photo.hashtag"/></th>
                                        <th></th>
                                    </thead>
                                    <tbody>
                                        <logic:notEmpty name="tagList" scope="request">
                                            <logic:iterate id="tag"  name="tagList" scope="request">
                                            <tr>
                                                <td>
                                                    ${tag.name}
                                                </td>
                                                <td>
                                                    <a href="#" onclick="addTag2List('${tag.uuid}','${tag.name}','#tag-template','#tag-list-container');return false;" ><i class="icon solid fa-plus-circle blue"></i></a>
                                                </td>
                                            </tr>
                                            </logic:iterate>
                                        </logic:notEmpty>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                       
                    
        </div>
    </section>
                    
</div>
                                        
                                        <div class="hidden" id="tag-template">
                                            <div id="tag-@@ID@@@">
                                                 <a href="#" onclick="removeFromTag('#tag-@@ID2@@@');" class="alt"><i class="icon solid fa-times blue"></i>@@TAG@@@ </a>
                                                <input type="hidden" value="@@VALUE@@@" name="tagValue" />
                                            </div>
                                        </div>
                                        
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/plugin/datatables/datatables.min.js"></script>
<script type='text/javascript'>
    $(document).ready(function () {
        $('#tag-list').DataTable({
            responsive: true,
            searching: true,
            "bLengthChange": false,
            "order": [],
            "columns": [{"width": "80%"},null],
            "columnDefs": [
                {"className": "dt-right", "targets": [1]}
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