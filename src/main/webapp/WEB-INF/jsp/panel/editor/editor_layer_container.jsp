<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<header class="title">
            <h4><bean:message key="label.editor.layer"/></h4>
        </header>
<ul id="sortable" class="sortable">
    
            
    <logic:notEmpty name="itemList"  scope="request">
        <logic:iterate id="item" name="itemList"  scope="request">
            <logic:notEqual name="item" property="itemType" value="bg">
                 <c:set var="itemDetail" value="${item}" scope="request"/>
                   <jsp:include page="item/item-list.jsp"/>
            </logic:notEqual>
           
        </logic:iterate>
    </logic:notEmpty>
    
</ul>
<ul class="sortable">
    <logic:notEmpty name="itemList"  scope="request">
        <logic:iterate id="item" name="itemList"  scope="request">
            <logic:equal name="item" property="itemType" value="bg">
                 <c:set var="itemDetail" value="${item}" scope="request"/>
                   <jsp:include page="item/item-list.jsp"/>
            </logic:equal>
        </logic:iterate>
    </logic:notEmpty>
    <li>
                <div class="editor-item-content-container">
                    <header>
                        <h4><bean:message key="label.editor.info"/></h4>
                        
                        <div class="config">
                            <a href="#" onclick="editItemContent('editor-info');return false;"><i class="icon solid fa-edit"></i></a>
                            
                        </div>
                    </header>
                </div>
                
            </li>
</ul>


<script>
  $( function() {
    $( "#sortable" ).sortable({
        update:function(event, ui){updateItemSeq();}
    });
  } );
  </script>