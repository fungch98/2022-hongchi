<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:empty name="family">
    <bean:message key="ERROR.NOFOUND" bundle="error"/>
</logic:empty>
<logic:notEmpty name="family">
    <div class="footer path">
        <logic:notEmpty name="family" property="path">
            <span> <a href="#" onclick="selectFolder('root');return false;">主目錄</a></span>
            <logic:iterate id="path" name="family" property="path">
                <span>/</span><span><a href="#" onclick="selectFolder('${path.url}');return false;"> ${path.name}</a></span>
            </logic:iterate>
        </logic:notEmpty>
    </div>
</logic:notEmpty>
<div class="row gtr-25">


    <logic:notEmpty name="family">
        <logic:notEmpty name="family" property="subFolder">

            <logic:iterate id="folder"  name="family" property="subFolder">

                <div class="col-3 col-4-medium col-6-small">
                    <a href="#" onclick="selectFolder('${folder.url}');return false;" class="alt3 filter-item cover-img-container">
                        <div class="image fit">
                            <img src="${pageContext.request.contextPath}/images/icon/folder.png"/>
                        </div>
                        <span class="folder-title">${folder.name}</span>
                    </a>
                </div>

            </logic:iterate>


        </logic:notEmpty>
    </logic:notEmpty>

</div>

<div class="col-12 col-12-small">


    <div id="folder-image-container" class="row gtr-25">

        <jsp:include page="imageResult.jsp"/>

    </div>
</div>


