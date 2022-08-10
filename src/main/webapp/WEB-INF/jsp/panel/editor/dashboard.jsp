<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div id="main">
    <section id="editor_dashboard" class="main">
        <div class="row gtr-0">
            <div class="col-1 col-12-medium split left trim">
                <div id="editor_nav">
                    <nav id="editor_action_container">
                        <jsp:include page="editor_action_container.jsp" />
                    </nav>
                </div>
            </div>
            <div class="col-3 col-12-medium">
                <div id="editor_item_container">
                    <jsp:include page="editor_layer_item.jsp"/>
                </div>
            </div>
               
            <div class="col-6 col-12-medium trim">
                <div id="editor_view_container">
                    <jsp:include page="editor_view_container.jsp" />
                </div>
                 <form id="editor_content_form" action="#" method="post">
                <div id="editor_content_detail">
                    <jsp:include page="editor_content_detail.jsp"/>
                </div>
                </form>
            </div>
            <div class="col-2 col-12-medium ">
                <div id="editor_layer_container">
                    <jsp:include page="editor_layer_container.jsp"/>
                </div>
            </div>        
        
        
        </div>
    </section>
</div>
                
<script src="${pageContext.request.contextPath}/assets/plugin/jscolor/jscolor.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/editor.js"></script>

<script>
    $(document).ready(function () {
        editorInit();
    });
    
    
</script>
