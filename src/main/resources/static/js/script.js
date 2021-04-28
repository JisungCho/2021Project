window.onload = function () {
    
    bootlint.showLintReportForCurrentDocument([], {
        hasProblems: false,
        problemFree: false
    });

    $('[data-toggle="tooltip"]').tooltip();

    function formatDate(date) {
        return (
            date.getFullYear() +
            "/" +
            (date.getMonth() + 1) +
            "/" +
            date.getDate()
        );
    }

    var currentDate = formatDate(new Date());

    $(".due-date-button").datepicker({
        format: "yyyy/MM/dd",
        autoclose: true,
        todayHighlight: true,
        startDate: currentDate,
        orientation: "bottom right"
    });

    $(".due-date-button").on("click", function (event) {
        $(".due-date-button")
            .datepicker("show")
            .on("changeDate", function (dateChangeEvent) {
                $(".due-date-button").datepicker("hide");
                $(".due-date-label").text(formatDate(dateChangeEvent.date));
            });
    });
    

    $(".add").on("click",function(){
		var todo_data = {
			todo_content : $(".todo_content").val(),
			todo_state : "new",
			todo_date : $(".due-date-label").text(),
			reg_date : currentDate,
			member_seq : 1
		};
				
   		$.ajax({
			url : '/todolist/save',
			type: 'post',
			contentType: 'application/json',
			data : JSON.stringify(todo_data), //자동으로key,value 값으로 변환
			dataType: 'json',
			success : function(e){
					if(e.code == 'SUCCESS'){
						var item = '<div class="row px-3 align-items-center todo-item rounded">'+
						        '<div class="col-auto m-1 p-0 d-flex align-items-center">'+
						            '<h2 class="m-0 p-0">'+
						                '<i class="mark fa fa-square-o text-primary btn m-0 p-0" data-toggle="tooltip" data-placement="bottom" title="Mark as complete"></i>'+
						            '</h2>'+
						        '</div>'+
						        '<div class="col px-1 m-1 d-flex align-items-center">'+
						           '<input type="text" class="form-control form-control-lg border-0 edit-todo-input bg-transparent rounded px-3" readonly value="'+e.data.todo_content +' " title="Buy groceries for next week" />'+
						        '</div>'+
						        '<div class="col-auto m-1 p-0 px-3 d-none">'+
						        '</div>'+
						        '<div class="col-auto m-1 p-0 todo-actions">'+
						            '<div class="row d-flex align-items-center justify-content-end">'+
						                '<h5 class="m-0 p-0 px-2">'+
						                    '<i class="fa fa-pencil text-info btn m-0 p-0" data-toggle="tooltip" data-placement="bottom" title="Edit todo"></i>'+
						                '</h5>'+
						                '<h5 class="m-0 p-0 px-2">'+
						                    '<i class="fa fa-trash-o text-danger btn m-0 p-0" data-toggle="tooltip" data-placement="bottom" title="Delete todo"></i>'+
						                '</h5>'+
						            '</div>'+
						            '<div class="row todo-created-info">'+
						                '<div class="col-auto d-flex align-items-center pr-2">'+
						                    '<i class="fa fa-info-circle my-2 px-2 text-black-50 btn" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Created date"></i>'+
						                    '<label class="date-label my-2 text-black-50">'+e.data.reg_date+'</label>'+
						                '</div>'+
						           '</div>'+
						        '</div>'+
						    '</div>';						
						$(".to_do_list").append(item);
					}else{
						alert("내용을 입력해 주세요");
					}
			}
		});		
    });
 
};