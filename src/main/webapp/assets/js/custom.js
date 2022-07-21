function savePhoto(){
    console.log("Save Photo");
}

function savePhoto(){
    console.log("Download Photo");
}

function showDeleteMessage(wording){
    //alert(confirm(""+wording));
    if(confirm(""+wording)){
        return true;
    }else {
        return false;
    }
}

function addTag2List(uuid, display, temp, resp ){
    try{
        
        var tempHTML=$(""+temp).html();
        var respDIV=$(""+resp);
        if(tempHTML!=='undefined'){
            tempHTML=$(""+temp).html();
            tempHTML=tempHTML.replace("@@ID@@@",uuid);
            tempHTML=tempHTML.replace("@@ID2@@@",uuid);
            tempHTML=tempHTML.replace( "@@TAG@@@",display);
            tempHTML=tempHTML.replace("@@VALUE@@@",uuid);
            respDIV.append(tempHTML);
        }
    }catch(e){
        console.log(e);
    }
    return false;
}


function addTagList(input, temp, resp){
    try{
        var tagInput=$(""+input).val();
        var tempHTML=$(""+temp).html();
        var respDIV=$(""+resp);
        var uuid="";
        if(tagInput!=='undefined'){
            tagInput=tagInput.replaceAll(",",";");
            var tagArray=tagInput.split(";");
            for(var i=0; tagArray!='undefined' && i<tagArray.length;i++){
                uuid=Math.random().toString(16).substr(2, 8);
                tempHTML=$(""+temp).html();
                tempHTML=tempHTML.replace("@@ID@@@",uuid);
                tempHTML=tempHTML.replace("@@ID2@@@",uuid);
                tempHTML=tempHTML.replace( "@@TAG@@@",tagArray[i]);
                tempHTML=tempHTML.replace("@@VALUE@@@",tagArray[i]);
                respDIV.append(tempHTML);
            }
        }
    }catch(e){
        console.log(e);
    }
}

function removeFromTag(target){
    try{
        $(""+target).remove();
    }catch(e){
        console.log(e);
    }
}


