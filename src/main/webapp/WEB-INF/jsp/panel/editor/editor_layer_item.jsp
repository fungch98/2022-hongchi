<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="item-list-container">
    <input type="Search" id="search" name="search" placeholder="Search" value=""/>
    
    <div class="tag-container">
        <a href="#">#陳南昌圖庫</a>
        <a href="#">#協康圖庫</a>
    </div>
</div>
<hr>

<div class="item-list-image-container">
            <div class="row alt">
                <c:forEach var="seq" begin="1" end="28"> 
                     <div class="col-3 col-6-medium col-12-small trim alt">
                        <img src="${pageContext.request.contextPath}/images/album/demo-0${(((seq+2)%3)+1)}.jpg" class="images fit"/>
                    </div>
                </c:forEach>
                
               
            </div>
        </div>