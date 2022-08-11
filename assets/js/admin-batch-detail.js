$(document).on("click", ".btn.btn-attendance-edit", function(e){
    e.preventDefault();    
    let _input = $(this).closest("tr").find('select[name="attend-type"]');
    let _date = $(this).closest("tr").find('#attend-date');
    let edit_btn = $(this).find(".fa-pen-to-square");
    if (edit_btn.length > 0) {
        edit_btn
          .removeClass("fa-pen-to-square")
          .addClass("fa-solid fa-check");
        _input.removeAttr("disabled");
        _date.removeAttr("disabled");
        _input.css("border", "1px solid red");
        _date.css("border", "1px solid red");
      }
    else{
        $(this)
      .find(".fa-solid.fa-check")
      .removeClass("fa-solid fa-check")
      .addClass("fa-pen-to-square");
    _input.attr("disabled", true);
    _date.attr("disabled", true);
    _input.css("border", "none");
    _date.css("border", "none");
    
    //Attendancce array
    let attendance = [];
    let _tr = $(".fa-pen-to-square").closest("tr");
    let attend_date = _tr.find("#attend-date").val();
    
    let _td = _tr.find(".stu-attendance");
    //console.log(_td.length);
        _td.each(function(){
            let student = {}
            if ($(this).find("#student-id").length > 0 ) {
                student["attendance_date"] = _date.val();
                student["student_id"] = $(this).find("#student-id").val();
            }
            if($(this).find("#attend-type").length > 0){
                student["attend_type"] = $(this).find("#attend-type").val();
            }
            attendance.push(student);
        })
        console.log(attendance);

    }
})
