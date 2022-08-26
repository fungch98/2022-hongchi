<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<ul id="sortable" class="sortable">
    <li>
                <div class="editor-item-content-container">
                    <header>
                        <h4><i class="icon solid fa-arrows-alt-v"></i><bean:message key="label.editor.info"/></h4>
                        
                        <div class="config">
                            <a href="#" onclick="editItemContent('editor-info');return false;"><i class="icon solid fa-edit"></i></a>
                            
                        </div>
                    </header>
                </div>
                
            </li>
            
    <logic:notEmpty name="itemList"  scope="request">
        <logic:iterate id="item" name="itemList"  scope="request">
            <c:set var="itemDetail" value="${item}" scope="request"/>
            <jsp:include page="item/item-list.jsp"/>
        </logic:iterate>
    </logic:notEmpty>
            <!--
    <li>
        <div class="editor-item-content-container">
            <header>
                <h4><i class="icon solid fa-arrows-alt-v"></i>背景</h4>
                <div class="config">
                    <a href="#"><i class="icon solid fa-angle-up"></i></a>
                </div>
            </header>
            
            <div class="content-container">
               
            </div>
        </div>
    </li>
    <li>
        <div class="editor-item-content-container">
            <header>
                <h4><i class="icon solid fa-arrows-alt-v"></i>文字</h4>
                <div class="config">
                    <a href="#"><i class="icon solid fa-angle-down"></i></a>
                </div>
            </header>
            
            <div class="content-container">
                
            </div>
        </div>
    </li>
     <li>
        <div class="editor-item-content-container">
            <header>
                <h4><i class="icon solid fa-arrows-alt-v"></i>圖片</h4>
                <div class="config">
                    <a href="#"><i class="icon solid fa-angle-down"></i></a>
                </div>
            </header>
            
            <div class="content-container">
                
            </div>
        </div>
    </li>
    <li>
        <div class="editor-item-content-container">
            <header>
                <h4><i class="icon solid fa-arrows-alt-v"></i>圖片</h4>
                <div class="config">
                    <a href="#"><i class="icon solid fa-angle-down"></i></a>
                </div>
            </header>
            
            <div class="content-container">
                
            </div>
        </div>
    </li>
            -->
</ul>


<script>
  $( function() {
    $( "#sortable" ).sortable({
        update:function(event, ui){updateItemSeq();}
    });
  } );
  </script>