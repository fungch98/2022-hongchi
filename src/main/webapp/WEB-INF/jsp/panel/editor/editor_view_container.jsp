<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div id="editor_viewer" class="ui-widget-content">
    <logic:notEmpty name="itemList"  scope="request">
        <logic:iterate id="item" name="itemList"  scope="request">
            <logic:equal name="item" property="itemType" value="bg">
                <script>
                   $(document).ready(function () {
                       setLayerBackground("${item.bgColor}${item.opacityVal}");
                   });
                   </script>
            </logic:equal>
                   <logic:equal name="item" property="itemType" value="text">
                <c:set var="itemDetail" value="${item}" scope="request"/>
               <jsp:include page="item/text-view.jsp"/>
                   </logic:equal>
            <logic:equal name="item" property="itemType" value="photo">
                <c:set var="itemDetail" value="${item}" scope="request"/>
               <jsp:include page="item/photo-view.jsp"/>
               
            </logic:equal>
            <logic:equal name="item" property="itemType" value="material">
                <c:set var="itemDetail" value="${item}" scope="request"/>
               <jsp:include page="item/material-view.jsp"/>
            </logic:equal>
            <logic:equal name="item" property="itemType" value="role">
                <c:set var="itemDetail" value="${item}" scope="request"/>
               <jsp:include page="item/role-view.jsp"/>
            </logic:equal>
            <logic:notEqual name="item" property="itemType" value="bg">
                <script>
                   $(document).ready(function () {
                       
                       $( "#item-${item.uuid}-obj" ).resizable({
                                containment: "#editor_viewer", 
                                stop: function (evt, ui) {
                                    //console.log("#"+targetKey+"-width"+":"+ui.size.width+":"+ui.size.height);
                                    $("#${item.uuid}-width").val(ui.size.width);
                                    $("#${item.uuid}-height").val(ui.size.height);
                                }
                              });
                              $( "#item-${item.uuid}-obj" ).draggable({ 
                                  containment: "#editor_viewer", scroll: false , 
                                  stop: function (evt, ui) {
                                    //console.log("#"+targetKey+"-width"+":"+ui.position.top+":"+ui.position.left);
                                    $("#${item.uuid}-posx").val(ui.position.top);
                                    $("#${item.uuid}-posy").val(ui.position.left);
                                    }
                              });
                              
                               var isFilp=1;
                              if($("#${item.uuid}-isFilp").val()!==undefined && $("#${item.uuid}-isFilp").val().trim()==='1'){
                                    isFilp=-1;
                              }
                              $( "#item-${item.uuid}-obj" ).rotatable({
                                    snap: true,
                                    degrees: ${item.rotate},
                                    transforms: {scaleX:isFilp},
                                    rotate: function(e, ui){
                                        var degrees = ui.angle.current * 180/Math.PI
                                        if ( degrees < 0 ) {degrees += 360;}
                                        $("#${item.uuid}-rotate").val(degrees);
                                    }
                               });
                                        <logic:equal name="item" property="itemType" value="role">
                                         
                                                               
                               $( "#item-${item.uuid}-obj" ).contextMenu({
                                    selector: 'img', 
                                    callback: function(key, options) {
                                        chageRoleEmotion('${item.uuid}', key);
                                    },
                                    items: contextMemuItem
                                });
                                          </logic:equal>
                   });      
               </script>
            </logic:notEqual>
        </logic:iterate>
    </logic:notEmpty>
</div>
