

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<li id="item-${itemDetail.uuid}-list" class="editor-item-content-container-list">
               
                <div class="editor-item-content-container">
                   
                    <header>
                         
                        <h4><i class="icon solid fa-arrows-alt-v"></i><bean:message key="label.editor.item.type.${itemDetail.itemType}"/><span id="item-list-${itemDetail.uuid}-name"><logic:notEmpty name="itemDetail" property="name"> - ${itemDetail.name}</logic:notEmpty></span></h4>
                        
                        <div class="config">
                            
                            <a href="#" onclick="editItemContent('${itemDetail.uuid}');return false;"><i class="icon solid fa-edit"></i></a>
                        <logic:notEqual name="itemDetail" property="itemType" value="bg">
                                <a href="#" onclick="showItem('${itemDetail.uuid}');return false;"><i id="item-${itemDetail.uuid}-list-eye" class="icon solid fa-eye"></i></a>
                                <a href="#" onclick="deleteItem('${itemDetail.uuid}');return false;"><i class="icon solid fa-trash delete"></i></a>
                        </logic:notEqual>
                        
                        </div>
                    </header>

                    <div class="content-container">
                        <input type="hidden" name="item-uuid" value="${itemDetail.uuid}"/>
                    </div>
                    
                </div>
                
            </li>