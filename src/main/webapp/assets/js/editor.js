var params = {
    snap: true,
            
            rotate: function(event, ui) {
			//console.log("Rotating " + ui.angle.current)
                if (Math.abs(ui.angle.current > 6)) {
                  console.log("Rotating " + ui.angle.current)
                }
            },
            start: function(event, ui) {
                console.log("Rotating started"+(ui.angle.current * 180/Math.PI))
            },
            stop: function(event, ui) {
                console.log("Rotating Stop"+(ui.angle.current * 180/Math.PI))
            },
        };

function editorInit(){
    try{
        $(".editor-content-container").first().addClass("active");
    }catch(e){
        console.log(e);
    }
}


function editItemContent(uuid){
    $(".editor-content-container.active").removeClass("active");
    $(".editor-item-content-container-list").removeClass("active");
    //console.log("#item-"+uuid);
    $("#item-"+uuid).addClass("active");
    $("#item-"+uuid+"-list").addClass("active");
    $("#item-"+uuid).focus();
    
    return false;
}

function setLayerBackground(value){
    var color="FFFFFF";
    var opactiy=1;
    var cR="FF";
    var cG="FF";
    var cB="FF";
    try{
        if(value.length>=7){
            color=value.substring(0,7);
            cR=parseInt(value.substring(1,3),16);
            cG=parseInt(value.substring(3,5),16);
            cB=parseInt(value.substring(5,7),16);
        }
        if(value.length>7){
            opactiy=parseInt(value.substring(7,value.length),16);
            opactiy=opactiy/255;
        }
        //console.log("Color: "+color+"/"+value.substring(1,3)+"/"+value.substring(3,5)+" : Op: "+opactiy);
        //console.log("rgba("+cR+","+cG+","+cB+","+opactiy+")");
        
        $("#editor_viewer").css("background-color","rgba("+cR+","+cG+","+cB+","+opactiy+")");
        
        
    }catch(e){
        console.log(e);
    }
}

function updateItemSeq(){
    var itemSeqUUID;
    try{
        var seqList=document.getElementsByName("item-uuid");
        for(var i=0; i<seqList.length; i++){
            console.log(seqList[i].value+":"+(seqList.length-i));
            itemSeqUUID=seqList[i].value;
            $("#"+itemSeqUUID+"-seq").val((seqList.length-i));
            $("#"+itemSeqUUID+"-zIndex").val((seqList.length-i));
            $("#item-"+itemSeqUUID+"-obj").css("z-index",""+(seqList.length-i));
        }
    }catch(e){
        console.log(e);
    }
}

function addItem(type, targetKey){
    var url=""+$("#rootpath").val()+"panel/editor/"+$("#langCode").val()+"/item/"+type+"/"+(targetKey!==undefined && targetKey!==''?targetKey:"new")+"/add.html";
    var target="#editor_viewer";
    var contentTarget="#editor-content-container";
    var listTarget="#sortable";
    var targetUUID="";
    try{
        $.ajax({
                url:url,
                type:"POST",
                //dataType: "json",
                contentType: 'text/html; charset=UTF-8',
                success:function(result){
                    //$(target).append(result);
                    //console.log(result);
                    var html=result.split("@@SPLIT@@@");
                    try{
                        if(html.length>=1){
                            $(contentTarget).append(html[0]);
                        }
                        if(html.length>=2){
                            $(target).append(html[1]);
                        }
                        if(html.length>=3){
                            $(listTarget).prepend(html[2]);
                        }
                        if(html.length>=4){
                            targetUUID=html[3].trim();
                        }
                        
                        $(".editor-content-container.active").removeClass("active");
                        $(".editor-content-container").last().addClass("active");
                        
                        if(type==="photo" || type==="text" || type==="material" || type==="role"){
                            //console.log("#item-"+targetUUID+"-obj");
                            $( "#item-"+targetUUID+"-obj" ).resizable({
                                containment: target, 
                                stop: function (evt, ui) {
                                    //console.log("#"+targetKey+"-width"+":"+ui.size.width+":"+ui.size.height);
                                    $("#"+targetUUID+"-width").val(ui.size.width);
                                    $("#"+targetUUID+"-height").val(ui.size.height);
                                }
                              });
                              $( "#item-"+targetUUID+"-obj" ).draggable({ 
                                  containment: target, scroll: false , 
                                  stop: function (evt, ui) {
                                    //console.log("#"+targetKey+"-width"+":"+ui.position.top+":"+ui.position.left);
                                    $("#"+targetUUID+"-posx").val(ui.position.top);
                                    $("#"+targetUUID+"-posy").val(ui.position.left);
                                    }
                              });
                               $( "#item-"+targetUUID+"-obj" ).rotatable({
                                    snap: true,
                                    rotate: function(e, ui){
                                        var degrees = ui.angle.current * 180/Math.PI
                                        if ( degrees < 0 ) {degrees += 360;}
                                        $("#"+targetUUID+"-rotate").val(degrees);
                                    }
                               });
                               
                               
                              changePos(targetUUID);
                              changeSize(targetUUID);
                              updateItemSeq();
                        }
                        
                        if(type==="text"){
                            jscolor.install();
                        }
                        
                        if(type==="role"){
                            updRoleOption(targetUUID, ''+$("#"+targetUUID+"-upUUID").val());
                            try{
                            $( "#item-"+targetUUID+"-obj" ).contextMenu({
                                    selector: 'img', 
                                    callback: function(key, options) {
                                        chageRoleEmotion(targetUUID, key);
                                    },
                                    items: contextMemuItem
                                });
                            }catch(e){
                                 console.log(e);
                            }
                        }
                        
                        
                        
                        
                        
                    }catch(e){
                        console.log(e);
                    }
                },
                error:function(xhr, error){
                    console.log("Load Next return false: ");
                    console.debug(xhr); console.debug(error);
                    showErrorSnack("Save Fail");
                }
            });
    }catch(e){
        console.log(e);
    }
    
}

function photoSearchTarget(target){
    //console.log("chage");
    photoSearch($("#search-ajax-key").val());
}

function photoSearch(key){
    photoSearch(key, null);
}

function photoSearch(key, type){
    var url=""+$("#rootpath").val()+"panel/"+$("#langCode").val()+"/editor/search.html";
    var target="#search-photo-result-container";
    
    try{
        //console.log(url);
        console.log(key);
        console.log(type);
        $("#search-ajax-key").val(key);
        
        $.ajax({
                url:url,
                type:"POST",
                //contentType: "application/json; charset=utf-8",
                //dataType: "json",
                data: {"searchKey": ''+key, "searchType":''+type},
                //contentType: 'text/html; charset=UTF-8',
                success:function(result){
                    
                    $(target).html(result);
                },
                error:function(xhr, error){
                    console.log("Load Next return false: ");
                    console.debug(xhr); console.debug(error);
                    showErrorSnack("Can not load image");
                }
            });
    }catch(e){
        console.log(e);
    }
    return false;
}

function photoSearchPage(page){
    var url=""+$("#rootpath").val()+"panel/"+$("#langCode").val()+"/editor/page/"+page+"/next.html";
    
    
    var target="#search-photo-result-container";
    try{
        $.ajax({
                url:url,
                type:"POST",
                //dataType: "json",
                //contentType: 'text/html; charset=UTF-8',
                success:function(result){
                    //$(target).append(result);
                    //console.log(result);
                    $(target).html(result);
                },
                error:function(xhr, error){
                    console.log("Load Next return false: ");
                    console.debug(xhr); console.debug(error);
                    showErrorSnack("Save Fail");
                }
            });
    }catch(e){
        console.log(e);
    }
    return false;
}

function changePos(uuid){
    var obj;
    var posX;
    var posY;
    var width=0;
    var height=0;
    try{
        posX=$("#"+uuid+"-posx").val();
        posY=$("#"+uuid+"-posy").val();
        width=$("#"+uuid+"-width").val();
        height=$("#"+uuid+"-height").val();
        //console.log("X"+(posX+height));
        if((posX+height)>450){
            posX=450-height;
            $("#"+uuid+"-posx").val(posX);
        }
        //console.log("Y"+(posY+width));
        if((posY+width)>600){
            posY=600-width;
            $("#"+uuid+"-posy").val(posY);
        }
        
        $("#item-"+uuid+"-obj").css("top",""+posX+"px");
        $("#item-"+uuid+"-obj").css("left",""+posY+"px");
    }catch(e){
        console.log(e);
    }
}

function changeSize(uuid){
    var width=0;
    var height=0;
    try{
        width=$("#"+uuid+"-width").val();
        height=$("#"+uuid+"-height").val();
        if(width>600){
            width=600;
            $("#"+uuid+"-width").val(width);
            $("#"+uuid+"-posy").val(0);
            $("#item-"+uuid+"-obj").css("left","0px");
        }
        if(height>450){
            height=450;
            $("#"+uuid+"-height").val(height);
            $("#"+uuid+"-posx").val(0);
            $("#item-"+uuid+"-obj").css("top","0px");
        }
        $("#item-"+uuid+"-obj").css("width",""+width+"px");
        $("#item-"+uuid+"-obj").css("height",""+height+"px");
    }catch(e){
        console.log(e);
    }
}

function changeName(uuid,value){
    try{
        $("#item-list-"+uuid+"-name").html(" - "+value);
    }catch(e){
        console.log(e);
    }
}

function editorSave(){
    var form = $("#editor_content_form");
    var url = form.attr('action');
    var rootPath="";
    try{
        //console.log(url);
        $.ajax({
                url:url,
                type:"POST",
                //contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: form.serialize(),
                //contentType: 'text/html; charset=UTF-8',
                success:function(result){
                    try{
                        //console.log(result.code);
                        if(result!==undefined && result.code==1){
                            if(result.inputKey==="new"){
                                  $("li.editor.nav").addClass("valid");
                            }
                            //console.log(""+$("#rootpath").val()+"panel/photo/"+$("#langCode").val()+"/"+result.uuid+"/view.html");
                            $("#preview-editor-btn").attr("href",""+$("#rootpath").val()+"panel/photo/"+$("#langCode").val()+"/"+result.photoUUID+"/view.html");
                            $("#download-editor-btn").attr("href",""+$("#rootpath").val()+"panel/upload/editor/"+result.uuid+"/download.html");
                            $("#editor_content_form").attr("action",""+$("#rootpath").val()+"panel/editor/"+$("#langCode").val()+"/"+result.uuid+"/save.html");
                            var successMsg=$("#editor-save-success-msg").val();
                            showSnack(successMsg);
                            
                        }else{
                            showErrorSnack(result.msg);
                        }
                    }catch(e){
                        console.log(e);
                    }
                    
                },
                error:function(xhr, error){
                    console.log("Load Next return false: ");
                    console.debug(xhr); console.debug(error);
                    showErrorSnack("Can not load image");
                }
            });
    }catch(e){
        console.log(e);
    }
}


function deleteItem(uuid){
    var msg="";
    var itemName="";
    try{
        msg=$("#editor-del-confirm-msg").val();
        itemName=$("#"+uuid+"-name").val();
    }catch(e){
        console.log(e);
    }
    try{
        if(confirm(""+msg+" ("+itemName+")")){
            $("#item-"+uuid).remove();
            $("#item-"+uuid+"-list").remove();
            $("#item-"+uuid+"-obj").remove();
        }
    }catch(e){
        console.log(e);
    }
    return false;
}

function accordionTag(){
    //var accPanel=$("#accordion-action-panel");
    var accPanel=document.getElementById("accordion-action-panel");
    try {
        if (accPanel.style.maxHeight) {
            accPanel.style.maxHeight = null;
            
             $(".accordion").removeClass("open");
        } else {
            accPanel.style.maxHeight = accPanel.scrollHeight + "px";
           $(".accordion").addClass("open");
        }
    } catch (e) {
        console.log(e);
    }
    return false;
}

function uploadPhoto(){
    var url=""+$("#rootpath").val()+"panel/editor/"+$("#langCode").val()+"/item/upload/add.html";
    try {
        //openModal("I am Model");
        var parentURL=$("#photo-info-folder").val();
        $.ajax({
                url:url+(parentURL!=''?"?folder="+parentURL:""),
                type:"POST",
                //contentType: "application/json; charset=utf-8",
                //dataType: "json",
                //data: form.serialize(),
                contentType: 'text/html; charset=UTF-8',
                success:function(result){
                    try{
                        openModal(result);
                    }catch(e){
                        console.log(e);
                    }
                    
                },
                error:function(xhr, error){
                    console.log("Load Next return false: ");
                    console.debug(xhr); console.debug(error);
                    showErrorSnack("Can not load image");
                }
            });
    } catch (e) {
        console.log(e);
    }
    return false;
}


function saveUploadPhoto(){
    var url="";
    var form = $("#editor-upload-form");
    var url = form.attr('action');
    var photoUUID=$("#photo-uuid").val();
    var photoName=$("#photo-name").val();
    try{
        if(photoUUID!==undefined && photoName!==undefined && photoUUID!=='' && photoName!==''){
            
        $.ajax({
                url:url,
                type:"POST",
                //contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: form.serialize(),
                //contentType: 'text/html; charset=UTF-8',
                success:function(result){
                    try{
                        if(result.code==1){
                            if(result.uuid!==undefined && result.uuid!=="" && result.uuid!=="new"){
                                addItem('photo',result.uuid);
                                closeModal();
                                showSnack(result.msg);
                            }
                        }else{
                            showErrorSnack(result.msg);
                        }
                        
                    }catch(ee){
                        console.log(ee);
                    }
                    
                },
                error:function(xhr, error){
                    console.log("Load Next return false: ");
                    console.debug(xhr); console.debug(error);
                    showErrorSnack("Can not load image");
                }
            });
        }else{
            if(photoUUID===undefined || photoUUID===''){
                alert(''+$("#editor-upload-emprty-photo").val());
            }else{
                alert(''+$("#editor-upload-emprty-photo-name").val());
            }
        }
    } catch (e) {
        console.log(e);
    }
    return false;
}

function updText(uuid, value){
    try{
        $("#item-"+uuid+"-obj").html(value);
    } catch (e) {
        console.log(e);
    }
}



function setTextStyle(uuid,type, value){
    var newName="";
    try{
        //console.log("Upd Text action:"+type);
        //console.log("Upd Text ID:"+"#item-"+uuid+"-obj");
        //console.log("Upd Text Value:"+value);
        if(type==='text'){
            $("#item-"+uuid+"-obj pre").html(value);
            
            try{
                
                newName=value;
                if(newName.length>10){
                    newName=newName.substr(0,10);
                }
                $("#"+uuid+"-name").val(newName);
                changeName(uuid, newName);
                
            }catch(e){}
            
            changeName(uuid, newName);
        }else if(type==='font'){
            $("#item-"+uuid+"-obj").removeClass("arial");
            $("#item-"+uuid+"-obj").removeClass("times");
            $("#item-"+uuid+"-obj").removeClass("noto");
            $("#item-"+uuid+"-obj").removeClass("hk");
            $("#item-"+uuid+"-obj").removeClass("SimSun");
            $("#item-"+uuid+"-obj").addClass(value);
        }else if(type==='size'){
            $("#item-"+uuid+"-obj").css("font-size",""+value+"pt");
            //console.log("Upd Text upd Value:"+$("#item-"+uuid+"-obj").css("font-size"));
        }else if(type==='color'){
            $("#item-"+uuid+"-obj").css("color",""+value);
        }else if(type==='bg'){
            setTextBackground(uuid, value);
        }else if(type==='bold'){
            $("#item-"+uuid+"-obj").removeClass("bold");
            if(value==='1'){
                $("#item-"+uuid+"-obj").addClass("bold");
            }
        }else if(type==='italic'){
            $("#item-"+uuid+"-obj").removeClass("italic");
            //console.log(""+(value==='1'));
            if(value==='1'){
                $("#item-"+uuid+"-obj").addClass("italic");
            }
        }else if(type==='align'){
            $("#item-"+uuid+"-obj").removeClass("left");
            $("#item-"+uuid+"-obj").removeClass("center");
            $("#item-"+uuid+"-obj").removeClass("right");
            
            $("#item-"+uuid+"-obj").addClass(value);
            
        }
        
    } catch (e) {
        console.log(e);
    }
}

function setTextBackground(uuid, value){
    var color="FFFFFF";
    var opactiy=1;
    var cR="FF";
    var cG="FF";
    var cB="FF";
    try{
        if(value.length>=7){
            color=value.substring(0,7);
            cR=parseInt(value.substring(1,3),16);
            cG=parseInt(value.substring(3,5),16);
            cB=parseInt(value.substring(5,7),16);
        }
        if(value.length>7){
            opactiy=parseInt(value.substring(7,value.length),16);
            opactiy=opactiy/255;
        }
        //console.log("Color: "+color+"/"+value.substring(1,3)+"/"+value.substring(3,5)+" : Op: "+opactiy);
        //console.log("rgba("+cR+","+cG+","+cB+","+opactiy+")");
        
        $("#item-"+uuid+"-obj").css("background-color","rgba("+cR+","+cG+","+cB+","+opactiy+") ");
        
        
    }catch(e){
        console.log(e);
    }
}

function showItemDetail(target){
    try{
        if(target==='image'){
            $("#item-character-view").removeClass("active");
            $("#item-image-view").addClass("active");
            $("#item-material-view").removeClass("active");
        }else if(target==='character'){
            $("#item-character-view").addClass("active");
            $("#item-image-view").removeClass("active");
            $("#item-material-view").removeClass("active");
        }else{
            $("#item-material-view").addClass("active");
            $("#item-image-view").removeClass("active");
            $("#item-character-view").removeClass("active");
        }
    }catch(e){
        console.log(e);
    }
}

function photoRoleDetail(key){
    var url=""+$("#rootpath").val()+"panel/editor/"+$("#langCode").val()+"/role/"+key+"/detail/search.html";
    var target="#character-emontion-container-result";
    
    try{
        console.log(url);
        
        $.ajax({
                url:url,
                type:"POST",
                //contentType: "application/json; charset=utf-8",
                //dataType: "json",
                //data: {"searchKey": ''+key},
                //contentType: 'text/html; charset=UTF-8',
                success:function(result){
                    
                    $(target).html(result);
                    $("#character-main-container").removeClass("active");
                    $("#character-emontion-container").addClass("active");
                },
                error:function(xhr, error){
                    console.log("Load Next return false: ");
                    console.debug(xhr); console.debug(error);
                    showErrorSnack("Can not load image");
                }
            });
    }catch(e){
        console.log(e);
    }
    return false;
}

function backToRole(){
    try {
        $("#character-main-container").addClass("active");
        $("#character-emontion-container").removeClass("active");
    } catch (e) {
        console.log(e);
    }
}

function chageRoleEmotion(uuid, key){
    try{
        var keyVal=key.split("_");
        if(keyVal!==undefined && keyVal.length>=0){
            console.log(keyVal[0]+":"+keyVal[1]);
            if(keyVal[0]==='ACTION'){
                $("#"+uuid+"-role-action").val(keyVal[1]);
            }else{
                $("#"+uuid+"-role-emotion").val(keyVal[1]);
            }
        }
        
    chageRole(uuid);
    }catch(e){
        console.log(e);
    }
    
}

function chageRole(uuid){
    var rootPath=$("#rootpath").val();
    var roleRoot=$("#"+uuid+"-upSrc").val();
    var roleEmotion=$("#"+uuid+"-role-emotion").val();
    var roleAction=$("#"+uuid+"-role-action").val();
    var roleKey=$("#"+uuid+"-upUUID").val();
    try {
        var keyList=roleKey.split('_');
        if(keyList!== undefined && keyList.length>=0){  
            var updRoleSrc=keyList[0]+"_"+roleAction+"_"+roleEmotion;
            var url=$("#"+uuid+"-upSrc").val()+"_"+roleAction+"_"+roleEmotion+".png";
            $("#"+uuid+"-imgSrc").val(url);
            $("#"+uuid+"-imgURL").val(url);
            $("#"+uuid+"-upUUID").val(updRoleSrc);
            console.log(""+rootPath+url);
            $("#item-"+uuid+"-obj-img-src").attr("src",rootPath+url);
        }
        
        
    } catch (e) {
        console.log(e);
    }
}

function updRoleOption(uuid, key){
    try{
        console.log(uuid+":"+key);
        if(key!=='' && key!== undefined){
            var keyList=key.split('_');  //[0] - Name [1] - action [2] - emotion
            if(keyList!== undefined && keyList.length>0){  
                $("#"+uuid+"-role-action").val(''+keyList[1]);
            }
            
            if(keyList!== undefined && keyList.length>1){
                $("#"+uuid+"-role-emotion").val(''+keyList[2]);
            }
        }
    } catch (e) {
        console.log(e);
    }
    
}

function showItem(uuid){
    try{
        var className=$("#item-"+uuid+"-list-eye").attr('class');
        if(className==='icon solid fa-eye'){
            $("#item-"+uuid+"-list-eye").removeClass("fa-eye");
            $("#item-"+uuid+"-list-eye").addClass("fa-eye-slash");
            $("#item-"+uuid+"-obj").css('display','none');
        }else{
            $("#item-"+uuid+"-list-eye").addClass("fa-eye");
            $("#item-"+uuid+"-list-eye").removeClass("fa-eye-slash");
            $("#item-"+uuid+"-obj").css('display','block');
        }
    }catch(e){
        
    }
}