
function initUploadImage(divId, responeDIV, URL){
     var defaultMsg=$("#upload_image_msg1").val();
    var invalidType=$("#upload_image_msg2").val();
    var overSize=$("#upload_image_msg3").val();
    var tooMany=$("#upload_image_msg4").val();
    //console.log( "ready!:"+divId+":"+responeDIV+":"+overSize );
    //var myDropzone = new Dropzone(""+divId);
    // Dropzone class:
    Dropzone.autoDiscover = false;
    var myDropzone = new Dropzone(""+divId, {
        url: ""+URL,
        paramName: "file", // The name that will be used to transfer the file
        method:"POST",
        maxFilesize: 5, // MB
        acceptedFiles: "image/*",
        maxFiles:1,
        dictDefaultMessage:defaultMsg,
        dictInvalidFileType:invalidType,
        dictMaxFilesExceeded:tooMany,
        dictFileTooBig:overSize,
       // previewsContainer: ""+responeDIV,
        accept: function (file, done) {
            done();
        },
        init: function () {
            this.on("complete", function(file) { 
                //this.removeAllFiles(true); 
             }),
            this.on("success", function(data) {
                this.removeAllFiles(true); 
                var response = data.xhr.response;
                //console.log( "Aready!:"+response );
                //console.log( "ready!:"+responeDIV );
                if(response !== undefined || response !== null){
                    var jsonObj=JSON.parse(""+response);
                    if(jsonObj.code==="1"){
                        //$(""+responeDIV).attr( "src", jsonObj.src );
                        $(""+responeDIV).val( jsonObj.src );
                        try{
                            $(""+responeDIV+"-preview").attr( "src", jsonObj.src );
                            //alert(""+responeDIV+"-preview");
                        }catch(e){}
                        //$(""+responeDIV+"-url").val(jsonObj.src);
                        //$("#UPLOAD_SUCCESS_DIV").css("display","block");
                        //$("#dropzone").css("display","none");
                    }else{
                        //console.log( "Update Image ERR:"+"#"+jsonObj.msg+":"+$("#"+jsonObj.msg).val());
                        //$("#UPLAOD_INVALID").css("display","block");
                        //$("#UPLAOD_INVALID_MSG").html(""+$("#"+jsonObj.msg).val());
                    }
                }
                

            });
        }
    });
}

function initUploadImageURL(divId, responeDIV, URL, updateField){
     var defaultMsg=$("#upload_image_msg1").val();
    var invalidType=$("#upload_image_msg2").val();
    var overSize=$("#upload_image_msg3").val();
    var tooMany=$("#upload_image_msg4").val();
    //console.log( "ready!:"+divId+":"+responeDIV );
    //var myDropzone = new Dropzone(""+divId);
    // Dropzone class:
    Dropzone.autoDiscover = false;
    var myDropzone = new Dropzone(""+divId, {
        url: ""+URL,
        paramName: "file", // The name that will be used to transfer the file
        method:"POST",
        maxFilesize: 10, // MB
        acceptedFiles: "image/*",
        maxFiles:10,
        dictDefaultMessage:defaultMsg,
        dictInvalidFileType:invalidType,
        dictMaxFilesExceeded:tooMany,
        dictFileTooBig:overSize,
        accept: function (file, done) {
            done();
        },
        init: function () {
            this.on("complete", function(file) { 
                
             }),
            this.on("success", function(data) {
                this.removeAllFiles(true); 
                var response = data.xhr.response;
                //console.log( "Aready!:"+response );
                
                if(response !== undefined || response !== null){
                    var jsonObj=JSON.parse(""+response);
                    if(jsonObj.code==="1"){
                        
                        $(""+responeDIV).val( jsonObj.src );
                        try{
                            $(""+responeDIV+"-preview").attr( "src", jsonObj.src );
                        
                        }catch(e){}
                        
                        try{
                            var res=updateField.split(";");
                            //console.log("Error("+updateField+"): "+res.length);
                            var i;
                            for (i = 0; i < res.length; i++) {
                                //console.log("Error: "+i+' val(): '+res[i]);
                                if( $(""+res[i]).val()===''){
                                    $(""+res[i]).val( jsonObj.src );
                                    $(""+res[i]+"-preview").attr( "src", jsonObj.src );
                                }
                              
                            }
                        }catch(e){
                            console.log("Error: "+e);
                            
                        }
                        
                    }else{
                        console.log( "Update Image ERR:"+"#"+jsonObj.msg+":"+$("#"+jsonObj.msg).val());
                    }
                    
                    
                }
                

            });
        }
    });
}

function initUploadFile(divId, responeDIV, URL, updateField){
     var defaultMsg=$("#upload_image_msg1").val();
    var invalidType=$("#upload_image_msg2").val();
    var overSize=$("#upload_image_msg3").val();
    var tooMany=$("#upload_image_msg4").val();
    //console.log( "ready!:"+divId+":"+responeDIV );
    //var myDropzone = new Dropzone(""+divId);
    // Dropzone class:
    Dropzone.autoDiscover = false;
    var myDropzone = new Dropzone(""+divId, {
        url: ""+URL,
        paramName: "file", // The name that will be used to transfer the file
        method:"POST",
        maxFilesize: 10, // MB
        //acceptedFiles: "application/pdf, .xlsx, .xls, .doc, .docx, .ppt, .pptx",
        maxFiles:1,
        dictDefaultMessage:defaultMsg,
        dictInvalidFileType:invalidType,
        dictMaxFilesExceeded:tooMany,
        dictFileTooBig:overSize,
       // previewsContainer: ""+responeDIV,
        accept: function (file, done) {
            done();
            //alert("FileUploaded");
            
        },
        init: function () {
            this.on("complete", function(file) { 
                //this.removeAllFiles(true); 
             }),
            this.on("success", function(data) {
                //this.removeAllFiles(true); 
                var response = data.xhr.response;
                //console.log( "Aready!:"+response );
               
                if(response !== undefined || response !== null){
                    var jsonObj=JSON.parse(""+response);
                    if(jsonObj.code==="1"){//$(""+responeDIV).attr( "src", jsonObj.src );
                        $(""+responeDIV).val( jsonObj.src );
                       try{
                            var res=updateField.split(";");
                            //console.log("Error("+updateField+"): "+res.length);
                            var i;
                            for (i = 0; i < res.length; i++) {
                                //console.log("Error: "+i+' val(): '+res[i]);
                                if( $(""+res[i]).val()===''){
                                    $(""+res[i]).val( jsonObj.src );
                                }
                              
                            }
                        }catch(e){
                            console.log("Error: "+e);
                            
                        }
                    }else{
                        console.log( "Update Image ERR:"+"#"+jsonObj.msg+":"+$("#"+jsonObj.msg).val());
                    }
                    
                    
                }
                

            });
        }
    });
}

function initUploadFileList(divId, responeDIV, URL, path, btnDisplay){
     var defaultMsg=$("#upload_image_msg1").val();
    var invalidType=$("#upload_image_msg2").val();
    var overSize=$("#upload_image_msg3").val();
    var tooMany=$("#upload_image_msg4").val();
    //console.log( "ready!:"+divId+":"+responeDIV );
    //var myDropzone = new Dropzone(""+divId);
    // Dropzone class:
    Dropzone.autoDiscover = false;
    var uploadDropzone = new Dropzone(""+divId, {
        url: ""+URL,
        paramName: "file", // The name that will be used to transfer the file
        method:"POST",
        acceptedFiles: "image/jpg,image/jpeg,image/png,image/gif, application/pdf, .xlsx, .xls, .doc, .docx, .ppt, .pptx",
        maxFilesize: 10, // MB
        maxFiles:10,
        dictDefaultMessage:defaultMsg,
        dictInvalidFileType:invalidType,
        dictMaxFilesExceeded:tooMany,
        dictFileTooBig:overSize,
       // previewsContainer: ""+responeDIV,
        accept: function (file, done) {
            done();
        },
        init: function () {
            this.on("success", function(data) {
                var response = data.xhr.response;
                //console.log( "Aready!:"+response );
                //alert("OK2");
                if(response !== undefined || response !== null){
                    var jsonObj=JSON.parse(""+response);
                    if(jsonObj.code==="1"){
                        var btnEdit="<a href='"+path+"/panel/upload/"+jsonObj.uuid+"/edit.html' class='button'>"+btnDisplay+"</a> ";
                        //alert(""+btnEdit);
                        $('#uploadList').DataTable().row.add({
                            0:jsonObj.user,1:jsonObj.name,2:jsonObj.modify,3:btnEdit
                        }).draw();
                        
                    }else{
                        console.log( "Update File ERR:"+"#"+jsonObj.msg+":"+$("#"+jsonObj.msg).val());
                    }
                    
                    
                }
                

            });
        }
    });
}



function onDeleteFile(href){
    var msg=$("#confirmMsg");
    //alert(msg);
    if(confirm(msg.val())){
        window.location.href = href; 
        return true;
    }else{
        return false;
    }
}

function uploadDocAttach(divId,  URL, updateField){
     var defaultMsg=$("#upload_image_msg1").val();
    var invalidType=$("#upload_image_msg2").val();
    var overSize=$("#upload_image_msg3").val();
    var tooMany=$("#upload_image_msg4").val();
    //console.log( "ready!:"+divId+":"+responeDIV );
    //var myDropzone = new Dropzone(""+divId);
    // Dropzone class:
    Dropzone.autoDiscover = false;
    var myDropzone = new Dropzone(""+divId, {
        url: ""+URL,
        paramName: "file", // The name that will be used to transfer the file
        method:"POST",
        maxFilesize: 10, // MB
        acceptedFiles: "image/jpg,image/jpeg,image/png,image/gif, application/pdf, .xlsx, .xls, .doc, .docx, .ppt, .pptx",
        maxFiles:10,
        dictDefaultMessage:defaultMsg,
        dictInvalidFileType:invalidType,
        dictMaxFilesExceeded:tooMany,
        dictFileTooBig:overSize,
        accept: function (file, done) {
            done();
        },
        init: function () {
            this.on("complete", function(file) { 
                
             }),
            this.on("success", function(data) {
                this.removeAllFiles(true); 
                var response = data.xhr.response;
                //console.log( "Aready!:"+response );
                
                if(response !== undefined || response !== null){
                    //console.log( "Aready!:"+response );
                     $( "#attach-list-container" ).append(response);
                     showSnack("Upload Done");
                }

            }),
            this.on("error", function(xhr, status, error) {
                this.removeAllFiles(true); 
                //console.log("Error Found"+status);
            });
        }
    });
}




