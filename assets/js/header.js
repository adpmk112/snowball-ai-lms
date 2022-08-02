

$('#navbar-toggle').on('click', function(){
    $('#navbar-toggle').toggleClass('show');
    if($('#navbar-toggle').hasClass('show')){
        $('.menu-icon-close').css('display', 'block');
        $('#menu-icon').css('display','none');
    }
    else{
        $('.menu-icon-close').css('display', 'none');
        $('#menu-icon').css('display','block');
    }
    //console.log("reclick");
    
})

