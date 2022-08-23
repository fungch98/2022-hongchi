

function editorInit(){
    try{
        $(".editor-content-container").first().addClass("active");
    }catch(e){
        console.log(e);
    }
}


function editItemContent(uuid){
    $(".editor-content-container.active").removeClass("active");
    console.log("#item-"+uuid);
    $("#item-"+uuid).addClass("active");
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
            //console.log(seqList[i].value);
            itemSeqUUID=seqList[i].value;
            $("#"+itemSeqUUID+"-seq").val(i);
            $("#"+itemSeqUUID+"-zIndex").val(i);
            $("#item-"+itemSeqUUID+"-obj").css("z-index",""+i);
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
                            $(listTarget).append(html[2]);
                        }
                        if(html.length>=4){
                            targetUUID=html[3].trim();
                        }
                        
                        $(".editor-content-container.active").removeClass("active");
                        $(".editor-content-container").last().addClass("active");
                        
                        if(type==="photo"){
                            console.log("#item-"+targetUUID+"-obj");
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
                                    console.log("#"+targetKey+"-width"+":"+ui.position.top+":"+ui.position.left);
                                    $("#"+targetUUID+"-posx").val(ui.position.top);
                                    $("#"+targetUUID+"-posy").val(ui.position.left);
                                    }
                              });
                              changePos(targetUUID);
                              changeSize(targetUUID);
                              updateItemSeq();
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
    console.log("chage");
    photoSearch($("#search-ajax-key").val());
}

function photoSearch(key){
    var url=""+$("#rootpath").val()+"panel/"+$("#langCode").val()+"/editor/search.html";
    var target="#search-photo-result-container";
    
    try{
        console.log(url);
        console.log(key);
        $("#search-ajax-key").val(key);
        
        $.ajax({
                url:url,
                type:"POST",
                //contentType: "application/json; charset=utf-8",
                //dataType: "json",
                data: {"searchKey": ''+key},
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
                    console.log(result);
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
    try{
        posX=$("#"+uuid+"-posx").val();
        posY=$("#"+uuid+"-posy").val();
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
        $.ajax({
                url:url,
                type:"POST",
                //contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: form.serialize(),
                //contentType: 'text/html; charset=UTF-8',
                success:function(result){
                    try{
                        console.log(result.code);
                        if(result!==undefined && result.code==1){
                            if(result.inputKey==="new"){
                                  $("li.editor.nav").addClass("valid");
                            }
                            console.log(""+$("#rootpath").val()+"panel/photo/"+$("#langCode").val()+"/"+result.uuid+"/view.html");
                            $("#preview-editor-btn").attr("href",""+$("#rootpath").val()+"panel/photo/"+$("#langCode").val()+"/"+result.uuid+"/view.html");
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
        $.ajax({
                url:url,
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
    try{
        url=
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
    } catch (e) {
        console.log(e);
    }
    return false;
}