
try {
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("modal-close")[0];

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
       closeModal();
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        var modal = document.getElementById("popup-modal");
        if (event.target == modal) {
            //modal.style.display = "none";
            closeModal();
        }
    }
} catch (e) {
    console.log(e);
}

function openModal(html){
    try{
        $("#popup-modal-div").html(html);
        // Get the modal
        $("#popup-modal").css("display","block");
    }catch(e){
        console.log(e);
    }
    
}

function closeModal(){
    try{
        // Get the modal
        $("#popup-modal-div").html("");
        var modal = document.getElementById("popup-modal");
        modal.style.display = "none";
    }catch(e){
        console.log(e);
    }
    
}


function copyLink(target){
    var el = document.getElementById(target);
    var range = document.createRange();
    range.selectNodeContents(el);
    var sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(range);
    document.execCommand('copy');
    
    var msg=document.getElementById("copyMsg").value;
    showSnack("Copied");
    return false;
}

function showSnack(value){
    // Get the snackbar DIV
  var x = document.getElementById("snackbar");
  x.innerHTML=value;
  // Add the "show" class to DIV
  x.className = "show";

  // After 3 seconds, remove the show class from DIV
  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

function showErrorSnack(value){
    // Get the snackbar DIV
  var x = document.getElementById("snackbar");
  x.innerHTML=value;
  // Add the "show" class to DIV
  x.className = "show snackError";
  

  // After 3 seconds, remove the show class from DIV
  setTimeout(function(){ x.className = x.className.replace("show snackError", ""); }, 3000);
}

function refreshImage(obj){
    try{
        
        $("#"+obj.id+"-preview").attr( "src", obj.value );
    }catch(e){}
}

function onChangeEmpty(value, target){
    try{
        var targetList=target.split(";");
        for (i = 0; i < targetList.length; i++) {
            var targetValue=document.getElementById(""+targetList[i]).value;
            if(targetValue==null || targetValue==""){
                document.getElementById(""+targetList[i]).value=value;
            }
            
          }
    }catch(e){
        console.log("onChangeEmpty Porblem: "+e);
    }
}


