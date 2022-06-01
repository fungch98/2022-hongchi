/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function openLink(target, current){
    // Hide all elements with class="tabcontent" by default */
    $( ".tab-content" ).css( "display", "none" );

    // Remove the background color of all tablinks/buttons
    $( ".tab-link" ).removeClass("active");

    // Show the specific tab content
     $( ""+target ).css("display", "block");
     $( ".tab-link" ).eq(current).addClass("active");
    //document.getElementById(target).style.display = "block";

    
}



