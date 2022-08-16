
         $('.owl-carousel').owlCarousel({
            
            items: 4,
            margin:10,
            nav: true,
            responsive:{
                0:{
                    items:1
                },
                600:{
                    items:4
                },
                1000:{
                    items:8
                }
            }
        })


        //For assignment arrow change
        $(".assignment-collapse ").click(function(){ 
            $(this).find("i").toggleClass("fa-angle-up") ;
           
        })