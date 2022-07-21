<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<ul id="sortable" class="sortable">
    <li>
        <div class="editor-item-content-container">
            <header>
                <h4><i class="icon solid fa-arrows-alt-v"></i>背景</h4>
                <div class="config">
                    <a href="#"><i class="icon solid fa-angle-up"></i></a>
                </div>
            </header>
            
            <div class="content-container">
                <div class="row">
                    <div class="col-12">
                        <label for="content">背景</label>
                        <input type="text" id="content" name="content" value=""/>
                    </div>
                </div>
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
</ul>


<script>
  $( function() {
    $( "#sortable" ).sortable();
  } );
  </script>