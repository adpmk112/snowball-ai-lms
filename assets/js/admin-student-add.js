
//Scroll
$(window).scroll(function(e){ 
    var $el = $('#student-form-table'); 
    var parentwidth = $(".table-responsive").width();
    var isPositionFixed = ($el.css('position') == 'fixed');
    if ($(this).scrollTop() > 100 && !isPositionFixed){ 
        $el.css({'position': 'fixed', 'top': '70px','width':parentwidth}); 
    }
    if ($(this).scrollTop() < 100 && isPositionFixed){
        $el.css({'position': 'static', 'top': '0px'}); 
    } 
});


$('form').submit(function(e){
    e.preventDefault();
    addStudent();
});



//Delete
$(document).on("click","#student-table .btn.btn-delete",function(){
    var _tr = $(this).closest('tr');
    $.confirm({
        title: "Delete Row!",
        content: 'Are you sure to delete this?',
        buttons: {
            No: function(){

            },
            Yes : function(){
                _tr.remove();                        
            }
        }

    }) 
});

//Add function
function addStudent(){
    var _number = $('#student-table tbody tr:last td:first').text()
    _number = parseInt(_number,10) + 1;

    console.log(_number);
    var _id = $('input[name="id"]').val();
    var _password = $('input[name="password"]').val();
    var _name = $('input[name="name"]').val();
    var _photo = $('input[name="photo"]').val();        
    //console.log(_gender)
    var _tr = '<tr>'+
                '<td>'+_number+'</td>'+
                '<td>'+ _id + '</td>'+
                '<td>' + _password + '</td>'+
                '<td>'+_name + '</td>'+
                // '<td>'+_photo+'</td>'+
                '<td class="d-flex">'+
                    '<button class="btn btn-outline-secondary btn-edit "><i class="fa fa-pencil"></i></button>'+
                    '<button class="btn btn-outline-danger btn-delete"><i class="fa fa-trash-can"></i></button>'
                '</td>'+
                '<tr>';
    $('#student-table').append(_tr);
    $("#student-form").trigger("reset");
    $('input[name="id"]').focus();
}

//Edit
var _trEdit = null;

//Edit button
$(document).on('click','#student-table .btn.btn-edit', function(){
    
    _trEdit = $(this).closest('tr');
    //var _name = $(_trEdit).find('td:eq(0)').text();
    var _id = $(_trEdit).find('td:eq(1)').text();
    var _password = $(_trEdit).find('td:eq(2)').text();
    var _name = $(_trEdit).find('td:eq(3)').text();

    $('input[name="id"]').val(_id);
    $('input[name="password"]').val(_password);
    //$('input[name="gender"]').removeAttr('checked');
    //$('input[name="gender"][value="'+_gender+'"]').prop('checked',true);
    $('input[name="name"]').val(_name);

    //Enable Update
    $('#student-form-table #btn-update').removeAttr("disabled");
    $('#student-form-table #btn-update').css("opacity","1");
    $('#student-form-table #btn-add').attr("disabled",true);
    $('#student-form-table #btn-add').css("opacity","0");
    $('input[name="id"]').focus();
    $('table tr').css("background","transparent");
    _trEdit.css("background","#d2e2e2");

});
//Update
$(document).on('click','#btn-update', function(){            
    if(_trEdit){                
        var _id = $('input[name="id"]').val();
        var _password = $('input[name="password"]').val();
        //var _gender = $('input[name="gender"]:checked').val();
        var _name = $('input[name="name"]').val();

        //$(_trEdit).find('td:eq(0)').text(_name);
        $(_trEdit).find('td:eq(1)').text(_id);
        $(_trEdit).find('td:eq(2)').text(_password);
        $(_trEdit).find('td:eq(3)').text(_name);
        _trEdit.css("background","transparent");
        _trEdit = null;
        //Enable add
        $('#student-form-table #btn-add').attr("disabled",false);
        $('#student-form-table #btn-update').attr("disabled",true);
        $('#student-form-table #btn-add').css("opacity","1");
        $('#student-form-table #btn-update').css("opacity","0");
        $("#student-form").trigger("reset");
        $('input[name="id"]').focus();
    }

});

//Btn add All
$('#student-add-card #btn-add-all').click(function(){
    $.confirm({
        title: "Upload all students",
        content: 'All student may be saved.',
        buttons: {
            Cancel: function(){},
            Confirm: function(){
                alert("Saved to database")
            }
        },
    })
})

