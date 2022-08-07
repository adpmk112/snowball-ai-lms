

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

// $('#header .navbar-nav .nav-item .nav-link').click(function(){
//     $('.navbar-nav .nav-item .nav-link').removeClass('active');
//     $(this).addClass('active')
// })

// $(function(){
//     var current = location.pathname;
//     $('#navcol-1 li a').each(function(){
//         var $this = $(this);
//         // if the current path is like this link, make it active
//         if($this.attr('href').indexOf(current) !== -1){
//             $this.addClass('active');
//         }
//     })
// })
