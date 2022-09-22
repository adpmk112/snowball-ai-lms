$(document).ready(function () {
  //For attendance color
  let selects = $('table.attendanceTable').find('select');
  $(selects).each(function(){
    if($(this).val() == 'Present'){
      $(this).removeClass('text-danger text-warning')
          .addClass('text-success')
    }
    else if($(this).val() == 'Absent'){
      $(this).removeClass('text-success text-warning')
          .addClass('text-danger')
    }
    else{
      $(this).removeClass('text-success text-danger')
              .addClass('text-warning')
    }
  })

  //For Attendance Edit
  $(document).on("click", ".btn.btn-attendance-edit", function (e) {
    e.preventDefault();
    let _input = $(this).closest("tr").find("select");
    let _date = $(this).closest("tr").find("#attend-date");
    let edit_btn = $(this).find(".fa-pen-to-square");
    if (edit_btn.length > 0) {
      edit_btn.removeClass("fa-pen-to-square").addClass("fa-solid fa-check");
      _input.removeAttr("disabled");
      _input.css("border", "1px solid red");
      _date.css("border", "1px solid red");
    } else {
      $(this)
        .find(".fa-solid.fa-check")
        .removeClass("fa-solid fa-check")
        .addClass("fa-pen-to-square")
        .removeAttr("type");
      _input.attr("disabled", true);
      _date.attr("disabled", true);
      _input.css("border", "none");
      _date.css("border", "none");

      //Attendancce object
      let attendance = {};
      let _tr = $(this).closest("tr");
      let batchId = _tr.find("input[name='batchId']").val();
      let classId = _tr.find("input[name='classId']").val();
      let studentLoop = $(this).closest("tr").find(".studentList");
      let studentAttendList = [];
      $(studentLoop).each(function () {
        let studentAndAttend = {};
        let studentId = $(this).find("input.studentId").val();
        let attend = $(this).find("select").val();
        studentAndAttend["studentId"] = studentId;
        studentAndAttend["attend"] = attend;
        studentAttendList.push(studentAndAttend);
      });
      attendance["batchId"] = batchId;
      attendance["classId"] = classId;
      attendance["studentAndAttendList"] = studentAttendList;
      //Send with ajax
      $.ajax({
        type: "POST",
        url: "/teacher/batch/setAttendance",
        headers: {
          "Content-Type": "application/json",
        },
        data: JSON.stringify(attendance),
        success: function () {
          window.location.replace("/teacher/batch/batchSeeMore?radio=attendance&batchId="+batchId);
        },
      });
    }
  });

  //For Exam mark
  $(document).on("click", ".btn.btn-mark-edit", function (e) {
    e.preventDefault();
    let _input = $(this).closest("tr").find('input[type="number"]');
    let edit_btn = $(this).find(".fa-pen-to-square");
    if (edit_btn.length > 0) {
      edit_btn
        .removeClass("fa-pen-to-square")
        .addClass("fa-solid fa-check");
      _input.removeAttr("readonly");
      _input.css("border", "1px solid red");
    } else {
      
  
      _input.attr("readonly", true);
      _input.css("border", "none");
      let examMark = {};
      let tr = $(this).closest("tr");

      let examId = tr.find('input[name="examId"]').val();
      let batchId = tr.find('input[name="batchId"]').val();
      let studentDataList = [];
      
      let error = "";
      let studentDataTd = tr.find('td.studentData')
      $(studentDataTd).each(function () {
          let studentId = $(this).find('input[name="studentId"]').val();
          let mark = $(this).find('input[name="mark"]').val();
          let maxMark = $(this).find('input[name="mark"]').attr('max');// Not use
          if(mark > maxMark || mark < 0){
            error = "error";
            return ;
          }
          let studentData = {}
          studentData['studentId'] = studentId;
          studentData['mark'] = mark;
          studentDataList.push(studentData);
      });

      examMark['examId'] = examId;
      examMark['studentData'] = studentDataList;
      examMark['batchId'] = batchId;
      console.log(examMark);
      if(error != "error"){
        $(this)
        .find(".fa-solid.fa-check")
        .removeClass("fa-solid fa-check")
        .addClass("fa-pen-to-square");
        $.ajax({
          type: "POST",
          url: "/teacher/batch/setExamMark",
          headers: {
            "Content-Type": "application/json",
          },
          data: JSON.stringify(examMark),
          success: function () {
            window.location=("/teacher/batch/batchSeeMore?radio=mark&batchId="+batchId);
          },
          error: function(){
            $.alert("Error!");
          }
        });
      }else{
        $.alert({
          title : "Error!",
          content : "Student's mark should be between 0 and maximum mark.",
        })
        edit_btn
        .removeClass("fa-pen-to-square")
        .addClass("fa-solid fa-check");
      _input.removeAttr("readonly");
      _input.css("border", "1px solid red");
      }

    }
  });

  //For Assignment Mark
  $(document).on("click", ".btn.btn-assignment-edit", function (e) {
    e.preventDefault();
    let _input = $(this).closest("tr").find('input[type="number"]');
    let edit_btn = $(this).find(".fa-pen-to-square");
    if (edit_btn.length > 0) {
      edit_btn
        .removeClass("fa-pen-to-square")
        .addClass("fa-solid fa-check");
      _input.removeAttr("readonly");
      _input.css("border", "1px solid red");
    } else {      
  
      _input.attr("readonly", true);
      _input.css("border", "none");
      let assignmentMark = {};
      let tr = $(this).closest("tr");

      let assignmentId = tr.find('input[name="assignmentId"]').val();
      let batchId = tr.find('input[name="batchId"]').val();
      let studentDataList = [];
      
      let error = "";
      let studentDataTd = tr.find('td.studentData')
      $(studentDataTd).each(function () {
          let studentId = $(this).find('input[name="studentId"]').val();
          let mark = $(this).find('input[name="mark"]').val();
          // let maxMark = $(this).find('input[name="mark"]').attr('max');// Not use
          // if(mark > maxMark || mark < 0){
          //   error = "error";
          //   return ;
          // }
          let studentData = {}
          studentData['studentId'] = studentId;
          studentData['mark'] = mark;
          studentDataList.push(studentData);
      });

      assignmentMark['assignmentId'] = assignmentId;
      assignmentMark['studentData'] = studentDataList;
      assignmentMark['batchId'] = batchId;
      console.log(assignmentMark);
      if(error != "error"){
        $(this)
        .find(".fa-solid.fa-check")
        .removeClass("fa-solid fa-check")
        .addClass("fa-pen-to-square");
        $.ajax({
          type: "POST",
          url: "/teacher/batch/setAssignmentMark",
          headers: {
            "Content-Type": "application/json",
          },
          data: JSON.stringify(assignmentMark),
          success: function () {
            window.location=("/teacher/batch/batchSeeMore?radio=mark&batchId="+batchId);
          },
          error: function(){
            $.alert("Error")
          }
        });
      }else{
        $.alert({
          title : "Error!",
          content : "Student's mark should be between 0 and maximum mark.",
        })
        edit_btn
        .removeClass("fa-pen-to-square")
        .addClass("fa-solid fa-check");
      _input.removeAttr("readonly");
      _input.css("border", "1px solid red");
      }

    }
  });

  //For Chapter date schedule
  $(document).on("click", ".btn.btn-chapter-edit", function (e) {
    e.preventDefault();
    let _start_date = $(this).closest("tr").find('input[name="start-date"]');
    let _end_date = $(this).closest("tr").find('input[name="end-date"]');
    let edit_btn = $(this).find(".fa-pen-to-square");
    if (edit_btn.length > 0) {
      edit_btn.removeClass("fa-pen-to-square").addClass("fa-solid fa-check");
      _start_date.removeAttr("disabled");
      _end_date.removeAttr("disabled");
      _start_date.css("border", "1px solid red");
      _end_date.css("border", "1px solid red");
    }
    else {
      let id = $(this).attr("id").split("_")[1];
      let chpId=$("#chapId_"+id)[0].value;
      let chpName = $("#chpName_" + id)[0].innerHTML;
      let startDate = $("#startDate_" + id)[0].value;
      let endDate = $("#endDate_" + id)[0].value;
      let batchId = $("#batchId_" + id)[0].value;
      window.state = $("#chpStatus_" + id)[0].innerHTML;
      window.currentDate = new Date();
      if(startDate!= ""&& endDate!=""){
        window.lessThan = startDate< endDate;
        window.equal = startDate==endDate;
      if (equal || lessThan) {
        $(this)
          .find(".fa-solid.fa-check")
          .removeClass("fa-solid fa-check")
          .addClass("fa-pen-to-square");
        _start_date.attr("disabled", true);
        _end_date.attr("disabled", true);
        _start_date.css("border", "none");
        _end_date.css("border", "none");
        $.ajax({
          type: "GET",
          url: "../batch/SendData",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          data: {
            chpId:chpId,
            chpName: chpName,
            startDate: startDate,
            endDate: endDate,
            batchId: batchId,
          },
          success: function (responce) {
            window.location = "/teacher/batch/batchSeeMore?radio=&batchId="+batchId;
          },
          error: function () {
            $.alert("Error!");
          },
        });
      }
      }else{
        $(this)
            .find(".fa-solid.fa-check")
            .removeClass("fa-solid fa-check")
            .addClass("fa-pen-to-square");
        _start_date.attr("disabled", true);
        _end_date.attr("disabled", true);
        _start_date.css("border", "none");
        _end_date.css("border", "none");
      }

      if (startDate > endDate) {
        $.alert("Start date can't be bigger!");
        edit_btn.removeClass("fa-pen-to-square").addClass("fa-solid fa-check");
        _start_date.removeAttr("disabled");
        _end_date.removeAttr("disabled");
        _start_date.css("border", "1px solid red");
        _end_date.css("border", "1px solid red");
      }
    }
  });

  //For activity schedule edit
  $(document).on("click",".btn.btn-custom-chapter-edit", function(e){
    e.preventDefault();
    let _start_date = $(this).closest("tr").find('input[name="start-date"]');
    let _end_date = $(this).closest("tr").find('input[name="end-date"]');
    let edit_btn = $(this).find(".fa-pen-to-square");
    if (edit_btn.length > 0) {
      edit_btn.removeClass("fa-pen-to-square").addClass("fa-solid fa-check");
      _start_date.removeAttr("disabled");
      _end_date.removeAttr("disabled");
      _start_date.css("border", "1px solid red");
      _end_date.css("border", "1px solid red");
    }
    else {      
        let batchId = $(this).closest('tr').find('input[name="batchId"]').val()
        let customChapterId = $(this).closest('tr').find('input[name="customChapterId"]').val()
        let startDate = _start_date.val();
        let endDate = _end_date.val();

        if(startDate == "" && endDate == ""){
          $(this)
            .find(".fa-solid.fa-check")
            .removeClass("fa-solid fa-check")
            .addClass("fa-pen-to-square");
            _start_date.attr("disabled", true);
            _end_date.attr("disabled", true);
            _start_date.css("border", "none");
            _end_date.css("border", "none"); 
        }
        else if((startDate == "" && endDate != "") || (startDate != "" && endDate == "")){
          $.alert("Start date or end date can't be blank!");
        }
        else if(startDate > endDate){
          $.alert("Start date should not be greater than end date!");
        }
        else{  
          
        $.ajax({
          type: "GET",
          url: "/teacher/batch/scheduleCustomChapter",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          data: {
            customChapterId:customChapterId,     
            startDate: startDate,
            endDate: endDate,
            batchId: batchId,
          },
          success: function () {
            window.location = "/teacher/batch/batchSeeMore?radio=&batchId="+batchId;
          },
          error: function () {
            $.alert("Error!");
          },
        });
           

          $(this)
            .find(".fa-solid.fa-check")
            .removeClass("fa-solid fa-check")
            .addClass("fa-pen-to-square");
            _start_date.attr("disabled", true);
            _end_date.attr("disabled", true);
            _start_date.css("border", "none");
            _end_date.css("border", "none");   
        }
        
      }      
     
  })
  // For exam-schedule edit
  $(document).on("click", ".btn.btn-exam-schedule-edit", function (e) {
    e.preventDefault();
    let _start_date = $(this).closest("tr").find('input[name="start-date"]');
    let _end_date = $(this).closest("tr").find('input[name="end-date"]');
    $(_start_date).attr("min", today);
    $(_end_date).attr("min", today);
    let edit_btn = $(this).find(".fa-pen-to-square");
    let batchId = $("input#batchIdForExamSchedule").val();
    //console.log(batchId)
    if (edit_btn.length > 0) {
      edit_btn.removeClass("fa-pen-to-square").addClass("fa-solid fa-check");
      _start_date.removeAttr("disabled");
      _end_date.removeAttr("disabled");
      _start_date.css("border", "1px solid red");
      _end_date.css("border", "1px solid red");
    } else {
      var id = $(this).attr("id");
      //console.log(id)
      var startDate = $(this)
        .closest("tr")
        .find('input[name="start-date"]')
        .val();
      var endDate = $(this).closest("tr").find('input[name="end-date"]').val();
      console.log(startDate)
      var d1 = new Date(startDate);
      var d2 = new Date(endDate);

      var lessThan = d1.getTime() < d2.getTime();
      var equal = d1.getTime() === d2.getTime();

      if (startDate == "" && endDate == "") {
        $(this)
          .find(".fa-solid.fa-check")
          .removeClass("fa-solid fa-check")
          .addClass("fa-pen-to-square");
        _start_date.attr("disabled", true);
        _end_date.attr("disabled", true);
        _start_date.css("border", "none");
        _end_date.css("border", "none");
      }else if(startDate == "" && endDate != ""){
        $.alert("Start date must be filled!")
      }else if(startDate != "" && endDate == ""){
        $.alert("End date must be filled!")
      }
      else if(startDate == endDate){
        $.alert("Start date and end date should not equal!")
      }else if (startDate < endDate) {
        $(this)
          .find(".fa-solid.fa-check")
          .removeClass("fa-solid fa-check")
          .addClass("fa-pen-to-square");
        _start_date.attr("disabled", true);
        _end_date.attr("disabled", true);
        _start_date.css("border", "none");
        _end_date.css("border", "none");

        $.ajax({
          type: "GET",
          url: "/teacher/batch/addExamSchedule",
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          data: { id: id, startDate: startDate, endDate: endDate },
          success: function () {
            window.location = "/teacher/batch/batchSeeMore?radio=examSchedule&batchId="+batchId;
          },
          error: function () {
            $.alert("Error occurs!");
          },
        });
      } else {        
        $.alert("Start Time can't be bigger!");
      }
    }
  });

  //Today Date for input
  function today(){
      let today = new Date();
      let dd = today.getDate();
      let mm = today.getMonth() + 1;
      let yyyy = today.getFullYear();
      let hh = today.getHours();
      let m = today.getMinutes();
      if (dd < 10) {
        dd = '0' + dd;
      }
      if (mm < 10) {
        mm = '0' + mm;
      } 
      if (hh < 10) {
        hh = '0' + hh;
      }  
      if (m < 10) {
        m = '0' + m;
      }               
      today = yyyy + "-" + mm + "-" + dd + "T" + hh + ":" + m;
      return today;
  }

});
