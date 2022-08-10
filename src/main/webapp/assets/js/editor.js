

function editorInit(){
    try{
        $(".editor-content-container").first().addClass("active");
    }catch(e){
        console.log(e);
    }
}

function addItem(type){
    
}

function editItemContent(uuid){
    $(".editor-content-container.active").removeClass("active");
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
                                containment: target
                              });
                              $( "#item-"+targetUUID+"-obj" ).draggable({ containment: target, scroll: false });
                              changeSize("#item-"+targetUUID+"-obj");
                               changePos("#item-"+targetUUID+"-obj");
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
                    $(target).append(result);
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
        $("#item-"+uuid+"-obj").css("left",""+height+"px");
    }catch(e){
        console.log(e);
    }
}


