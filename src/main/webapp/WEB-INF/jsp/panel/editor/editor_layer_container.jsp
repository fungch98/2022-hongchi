<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<ul id="sortable" class="sortable">
    <logic:notEmpty name="editor" property="editorItemList" scope="request">
        <logic:iterate id="item" name="editor" property="editorItemList" scope="request">
            <li>
               
                <div class="editor-item-content-container">
                   
                    <header>
                         
                        <h4><i class="icon solid fa-arrows-alt-v"></i><bean:message key="label.editor.item.type.${item.itemType}"/></h4>
                        
                        <div class="config">
                            <a href="#" onclick="editItemContent('${item.uuid}');return false;"><i class="icon solid fa-edit delete"></i></a>
                            <a href="#"><i class="icon solid fa-trash delete"></i></a>
                        </div>
                    </header>

                    <div class="content-container">
                        <input type="hidden" name="item-uuid" value="${item.uuid}"/>
                    </div>
                    
                </div>
                
            </li>
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