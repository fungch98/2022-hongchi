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
            <logic:equal name="item" property="itemType" value="photo">
                <c:set var="itemDetail" value="${item}" scope="request"/>
               <jsp:include page="item/photo-view.jsp"/>
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
                   });
               </script>
            </logic:equal>
        </logic:iterate>
    </logic:notEmpty>
</div>
