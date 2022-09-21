
//For Mark
// $(document).on("click", ".btn.btn-mark-edit", function (e) {
//   e.preventDefault();
//   let _input = $(this).closest("tr").find('input[type="number"]');
//   let edit_btn = $(this).find(".fa-pen-to-square");
//   if (edit_btn.length > 0) {
//     edit_btn
//       .removeClass("fa-pen-to-square")
//       .addClass("fa-solid fa-check");
//     _input.removeAttr("readonly");
//     _input.css("border", "1px solid red");
//   } else {
//     $(this)
//       .find(".fa-solid.fa-check")
//       .removeClass("fa-solid fa-check")
//       .addClass("fa-pen-to-square");

//     _input.attr("readonly", true);
//     _input.css("border", "none");
//     var marks = [];
//     var _tr = $(".fa-pen-to-square").closest("tr");
//     var exam_id = _tr.find("#exam-id").val();
    
//     var _td = _tr.find("td");
//     console.log(_td.length);
//     _td.each(function () {
//       var student_mark = {};
//       if ($(this).find("#student-id").length > 0) {
//         student_mark["student_id"] = $(this).find("#student-id").val();
//       }
//       if ($(this).find("#mark").length > 0) {
//         student_mark["mark"] = $(this).find("#mark").val();
//       }
//       student_mark["exam_id"] = exam_id;
//       if (student_mark["student_id"] && student_mark["mark"])
//         marks.push(student_mark);
//     });
//     console.log(marks);
//   }
// });

// For exam-schedule edit
// $(document).on("click", ".btn.btn-exam-schedule-edit", function(e){
//   e.preventDefault();    
//   let _start_date = $(this).closest("tr").find('input[name="start-date"]');
//   let _end_date = $(this).closest("tr").find('input[name="end-date"]');
//   let edit_btn = $(this).find(".fa-pen-to-square");
//   if (edit_btn.length > 0) {
//       edit_btn
//         .removeClass("fa-pen-to-square")
//         .addClass("fa-solid fa-check");
//       _start_date.removeAttr("disabled");
//       _end_date.removeAttr("disabled");
//       _start_date.css("border", "1px solid red");
//       _end_date.css("border", "1px solid red");
//     }
//   else{
//       $(this)
//     .find(".fa-solid.fa-check")
//     .removeClass("fa-solid fa-check")
//     .addClass("fa-pen-to-square");
//   _start_date.attr("disabled", true);
//   _end_date.attr("disabled", true);
//   _start_date.css("border", "none");
//   _end_date.css("border", "none");
  

//     }
//     })

//For Attendance grid
// $(document).on("click", ".btn.btn-attendance-edit", function(e){
//     e.preventDefault();    
//     let _input = $(this).closest("tr").find('select[name="attend-type"]');
//     let _date = $(this).closest("tr").find('#attend-date');
//     let edit_btn = $(this).find(".fa-pen-to-square");
//     if (edit_btn.length > 0) {
//         edit_btn
//           .removeClass("fa-pen-to-square")
//           .addClass("fa-solid fa-check");
//         _input.removeAttr("disabled");
//         _date.removeAttr("disabled");
//         _input.css("border", "1px solid red");
//         _date.css("border", "1px solid red");
//       }
//     else{
//         $(this)
//       .find(".fa-solid.fa-check")
//       .removeClass("fa-solid fa-check")
//       .addClass("fa-pen-to-square");
//     _input.attr("disabled", true);
//     _date.attr("disabled", true);
//     _input.css("border", "none");
//     _date.css("border", "none");
    
//     //Attendancce array
//     let attendance = [];
//     let _tr = $(".fa-pen-to-square").closest("tr");
//     let attend_date = _tr.find("#attend-date").val();
    
//     let _td = _tr.find(".stu-attendance");
//     //console.log(_td.length);
//         _td.each(function(){
//             let student = {}
//             if ($(this).find("#student-id").length > 0 ) {
//                 student["attendance_date"] = _date.val();
//                 student["student_id"] = $(this).find("#student-id").val();
//             }
//             if($(this).find("#attend-type").length > 0){
//                 student["attend_type"] = $(this).find("#attend-type").val();
//             }
//             attendance.push(student);
//         })
//         console.log(attendance);

//     }
// })


//For Chapter
$(document).on("click", ".btn.btn-chapter-edit", function(e){
  e.preventDefault();    
  let _start_date = $(this).closest("tr").find('input[name="start-date"]');
  let _end_date = $(this).closest("tr").find('input[name="end-date"]');
  let edit_btn = $(this).find(".fa-pen-to-square");
  if (edit_btn.length > 0) {
      edit_btn
        .removeClass("fa-pen-to-square")
        .addClass("fa-solid fa-check");

      _start_date.removeAttr("disabled");
      _end_date.removeAttr("disabled");
      _start_date.css("border", "1px solid red");
      _end_date.css("border", "1px solid red");
    }
  else{
      $(this)
    .find(".fa-solid.fa-check")
    .removeClass("fa-solid fa-check")
    .addClass("fa-pen-to-square");

  _start_date.attr("disabled", true);
  _end_date.attr("disabled", true);
 console.log(_start_date);
 _start_date =document.getElementById("start-date").value;
 _end_date=document.getElementById("end-date").value;
      if(_start_date.getTime() < _end_date.getTime())
      {
          console.log('start_date date is lower than end_date')
          _start_date.css("border", "none");
          _end_date.css("border", "none");
          $(this).id='submitButton';
      }
      else
      {
          console.log('end_date date is greater than end_date')
      }

  
  //Attendancce array
  // let attendance = [];
  // let _tr = $(".fa-pen-to-square").closest("tr");
  // let attend_date = _tr.find("#attend-date").val();
  
  // let _td = _tr.find(".stu-attendance");
  // //console.log(_td.length);
  //     _td.each(function(){
  //         let student = {}
  //         if ($(this).find("#student-id").length > 0 ) {
  //             student["attendance_date"] = _date.val();
  //             student["student_id"] = $(this).find("#student-id").val();
  //         }
  //         if($(this).find("#attend-type").length > 0){
  //             student["attend_type"] = $(this).find("#attend-type").val();
  //         }
  //         attendance.push(student);
  //     })
  //     console.log(attendance);
    }
    })