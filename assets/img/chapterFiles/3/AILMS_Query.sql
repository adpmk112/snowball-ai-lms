SELECT * FROM mydb.chapter_batch;

select chapter.name,chapter_batch.start_date,chapter_batch.end_date from chapter_batch
join chapter on chapter_batch.chapter_id = chapter.id where chapter_batch.batch_id = 1;

select student.photo,student.name from batch
join student on batch.id = student.batch_id where batch.id = 1;

select teacher.photo,teacher.name from batch
join teacher_batch on batch.id = teacher_batch.batch_id 
join teacher on teacher_batch.teacher_id = teacher.id
where batch.id = 1;

select exam_form.name,batch_exam_form.start_date,batch_exam_form.end_date from batch
join batch_exam_form on batch.id = batch_exam_form.batch_id 
join exam_form on batch_exam_form.exam_form_id = exam_form.id
where batch.id = 1;


select student.name,classroom.date,attendance.attend from batch
join classroom on batch.id = classroom.batch_id 
join attendance on classroom.id = attendance.classroom_id
join student on attendance.student_id = student.id
where batch.id = 1;

select classroom.date,classroom.time,classroom.link,classroom.record_video,teacher.name from
classroom join batch on classroom.batch_id = batch.id 
join teacher_batch on batch.id = teacher_batch.batch_id
join teacher on teacher.id = teacher_batch.teacher_id
where batch.id = 1

