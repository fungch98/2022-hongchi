

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<li>
               
                <div class="editor-item-content-container">
                   
                    <header>
                         
                        <h4><i class="icon solid fa-arrows-alt-v"></i><bean:message key="label.editor.item.type.${itemDetail.itemType}"/><span id="item-list-${itemDetail.uuid}-name"><logic:notEmpty name="itemDetail" property="name"> - ${itemDetail.name}</logic:notEmpty></span></h4>
                        
                        <div class="config">
                            <a href="#" onclick="editItemContent('${itemDetail.uuid}');return false;"><i class="icon solid fa-edit delete"></i></a>
                            <a href="#"><i class="icon solid fa-trash delete"></i></a>
                        </div>
                    </header>

                    <div class="content-container">
                        <input type="hidden" name="item-uuid" value="${itemDetail.uuid}"/>
                    </div>
                    
                </div>
                
            </li>