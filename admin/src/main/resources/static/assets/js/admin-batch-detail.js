// $(document).on('click', '.btn.btn-attendance-edit', function (e) {
//   let _input = $(this).closest('tr').find('select')
//   let _date = $(this).closest('tr').find('#attend-date')
//   let edit_btn = $(this).find('.fa-pen-to-square')
//   if (edit_btn.length > 0) {
//     edit_btn.removeClass('fa-pen-to-square').addClass('fa-solid fa-check')
//     _input.removeAttr('disabled')
//     _input.css('border', '1px solid red')
//     _date.css('border', '1px solid red')
//     $(this).attr("type", "submit");
//   } else {
//     $(this)
//       .find('.fa-solid.fa-check')
//       .removeClass('fa-solid fa-check')
//       .addClass('fa-pen-to-square')
//       .removeAttr('type')
//     _input.attr('disabled', true)
//     _date.attr('disabled', true)
//     _input.css('border', 'none')
//     _date.css('border', 'none')
//     $(this).removeAttr("type")
  
   
//     //Attendancce array
//     // let attendance = [];
//     // let _tr = $(".fa-pen-to-square").closest("tr");
//     // let attend_date = _tr.find("#attend-date").val();

//     // let _td = _tr.find(".stu-attendance");
//     // //console.log(_td.length);
//     //     _td.each(function(){
//     //         let student = {}
//     //         if ($(this).find("#student-id").length > 0 ) {
//     //             student["attendance_date"] = _date.val();
//     //             student["student_id"] = $(this).find("#student-id").val();
//     //         }
//     //         if($(this).find("#attend-type").length > 0){
//     //             student["attend_type"] = $(this).find("#attend-type").val();
//     //         }
//     //         attendance.push(student);
//     //     })
//     //     console.log(attendance);
//   }
// })

//For Chapter date schedule
$(document).on('click', '.btn.btn-chapter-edit', function (e) {
  let _start_date = $(this).closest('tr').find('input[name="start-date"]')
  let _end_date = $(this).closest('tr').find('input[name="end-date"]')
  let edit_btn = $(this).find('.fa-pen-to-square')
  if (edit_btn.length > 0) {
    edit_btn.removeClass('fa-pen-to-square').addClass('fa-solid fa-check')
    _start_date.removeAttr('disabled')
    _end_date.removeAttr('disabled')
    _start_date.css('border', '1px solid red')
    _end_date.css('border', '1px solid red')
  } else {
    var id = $(this).attr('id').split('_')[1]

    var chpName = $('#chpName_' + id)[0].innerHTML
    var startDate = $('#startDate_' + id)[0].value
    var endDate = $('#endDate_' + id)[0].value
    var batchId = $('#batchId_' + id)[0].value
    window.state = $('#chpStatus_' + id)[0].innerHTML
    window.currentDate = new Date()
    window.today = currentDate.toISOString().slice(0, 10)
    let d1 = new Date(startDate)
    window.startDay = d1.toISOString().slice(0, 10)
    let d2 = new Date(endDate)
    window.endDay = d2.toISOString().slice(0, 10)
    window.lessThan = d1.getTime() < d2.getTime()
    window.equal = startDay == endDay
    if (equal || lessThan) {
      $(this)
        .find('.fa-solid.fa-check')
        .removeClass('fa-solid fa-check')
        .addClass('fa-pen-to-square')
      _start_date.attr('disabled', true)
      _end_date.attr('disabled', true)
      _start_date.css('border', 'none')
      _end_date.css('border', 'none')
      $.ajax({
        type: 'GET',
        url: '../SendData',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: {
          chpName: chpName,
          startDate: startDate,
          endDate: endDate,
          batchId: batchId,
        },
        success: function (responce) {
          if (d1 === null || d2 === null) {
            $('#chpStatus_' + id)[0].innerHTML = 'Not Added'
          } else {
            if (
              (lessThan &&
                d2.getTime() > currentDate.getTime() &&
                startDay == today) ||
              (equal && endDay == today) ||
              startDay == today ||
              (lessThan && endDay == today) ||
              startDay == today
            ) {
              $('#chpStatus_' + id)[0].innerHTML = 'In progress'
            } else if (
              (lessThan && d2.getTime() < currentDate.getTime()) ||
              (equal && d2.getTime() < currentDate.getTime())
            ) {
              $('#chpStatus_' + id)[0].innerHTML = 'Done'
            } else {
              $('#chpStatus_' + id)[0].innerHTML = 'Not Started'
            }
          }
        },
        error: function () {
          alert('Error!')
        },
      })
    }
    if (d1.getTime() > d2.getTime()) {
      alert("Start date can't be bigger!")
      edit_btn.removeClass('fa-pen-to-square').addClass('fa-solid fa-check')
      _start_date.removeAttr('disabled')
      _end_date.removeAttr('disabled')
      _start_date.css('border', '1px solid red')
      _end_date.css('border', '1px solid red')
    }
  }
})
// For exam-schedule edit
$(document).on('click', '.btn.btn-exam-schedule-edit', function (e) {
  let _start_date = $(this).closest('tr').find('input[name="start-date"]')
  let _end_date = $(this).closest('tr').find('input[name="end-date"]')
  let edit_btn = $(this).find('.fa-pen-to-square')
  if (edit_btn.length > 0) {
    edit_btn.removeClass('fa-pen-to-square').addClass('fa-solid fa-check')
    _start_date.removeAttr('disabled')
    _end_date.removeAttr('disabled')
    _start_date.css('border', '1px solid red')
    _end_date.css('border', '1px solid red')
  } else {
    var id = $(this).attr('id')
    //console.log(id)
    var startDate = $(this).closest('tr').find('input[name="start-date"').val()
    var endDate = $(this).closest('tr').find('input[name="end-date"').val()
    console.log(startDate + endDate)

    var d1 = new Date(startDate)
    var d2 = new Date(endDate)

    var lessThan = d1.getTime() < d2.getTime()
    var equal = d1.getTime() === d2.getTime()

    if (startDate == '' && endDate == '') {
      $(this)
        .find('.fa-solid.fa-check')
        .removeClass('fa-solid fa-check')
        .addClass('fa-pen-to-square')
      _start_date.attr('disabled', true)
      _end_date.attr('disabled', true)
      _start_date.css('border', 'none')
      _end_date.css('border', 'none')
    } else if (equal || lessThan) {
      $(this)
        .find('.fa-solid.fa-check')
        .removeClass('fa-solid fa-check')
        .addClass('fa-pen-to-square')
      _start_date.attr('disabled', true)
      _end_date.attr('disabled', true)
      _start_date.css('border', 'none')
      _end_date.css('border', 'none')

      $.ajax({
        type: 'GET',
        url: '../addExamSchedule',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: { id: id, startDate: startDate, endDate: endDate },
        success: function (responce) { },
        error: function () {
          alert('Error!')
        },
      })
    } else {
      alert("Start date can't be bigger!")
    }
  }
})

//For student edit

//For exam schedule
$(document).on('click', '.btn.btn-exam-schedule', function (e) {
  let _start_date = $(this).closest('tr').find('input[name="start-date"]')
  let _end_date = $(this).closest('tr').find('input[name="end-date"]')
  let edit_btn = $(this).find('.fa-pen-to-square')
  if (edit_btn.length > 0) {
    edit_btn.removeClass('fa-pen-to-square').addClass('fa-solid fa-check')
    _start_date.removeAttr('disabled')
    _end_date.removeAttr('disabled')
    _start_date.css('border', '1px solid red')
    _end_date.css('border', '1px solid red')
  } else {
    $(this)
      .find('.fa-solid.fa-check')
      .removeClass('fa-solid fa-check')
      .addClass('fa-pen-to-square')
    _start_date.attr('disabled', true)
    _end_date.attr('disabled', true)
    _start_date.css('border', 'none')
    _end_date.css('border', 'none')
  }
})

//Check name for batch
