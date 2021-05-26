window.onload = function () {
	var prevday;
	var prevcontent;
	
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
        clearBtn : true,
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
    
    
    
    //edit 버튼 클릭
    $(".to_do_list").on("click",".edit",function(){
    	var seq = $(this).data("number");
    	$('#'+seq).removeClass("bg-transparent");
    	$('#'+seq).attr("readonly",false);
    	prevcontent = $('#'+seq).val();
    	//v , x 버튼 추가 edit , delete버튼 삭제
    	$('#todo_check'+seq).removeClass('d-none');
    	$('#todo_cancle'+seq).removeClass('d-none');
		
    	$("#todo_date"+seq).removeClass('d-none');
    	$("#todo_edit"+seq).addClass("d-none");
    	$("#todo_delete"+seq).addClass("d-none");
    });
    
    //날짜수정 버튼 클릭
    $(".due").on("click",function(){
    	prevday = $(this).next().text();
    	
	    $(this).datepicker({
	        format: "yyyy/MM/dd",
	        autoclose: true,
	        todayHighlight: true,
	        clearBtn : true,
	        startDate: currentDate,
	        orientation: "bottom"
	    });	
	    
	    $(this)
	    .datepicker("show")
	    .on("changeDate", function (dateChangeEvent) {
                $(".due-date-button").datepicker("hide");
                $(this).next().text(formatDate(dateChangeEvent.date));
       	});
       	
    })
    
    //수정 취소
    $(".cancle").on("click",function(){
    	var seq = $(this).data("number");
    	$('#'+seq).addClass("bg-transparent");
    	$('#'+seq).attr("readonly",true);
    	//v , x 버튼 삭제 edit , delete버튼 추가
    	$('#todo_check'+seq).addClass('d-none');
    	$('#todo_cancle'+seq).addClass('d-none');
    	$("#todo_date"+seq).addClass('d-none');
    	$('#todo_label'+seq).text(prevday);
    	$("#"+seq).val(prevcontent);
    	$("#todo_edit"+seq).removeClass("d-none");
    	$("#todo_delete"+seq).removeClass("d-none");
    });
    
    //수정 완료
    $(".to_do_list").on("click",".check",function(){
    	var seq = $(this).data("number");
    	$('#'+seq).addClass("bg-transparent");
    	$('#'+seq).attr("readonly",true);
    	//v , x 버튼 삭제 edit , delete버튼 추가
    	$('#todo_check'+seq).addClass('d-none');
    	$('#todo_cancle'+seq).addClass('d-none');
    	$("#todo_date"+seq).addClass('d-none');
    	$("#todo_edit"+seq).removeClass("d-none");
    	$("#todo_delete"+seq).removeClass("d-none");
    	
    	var todo_data = {
    		seq : seq,
			todo_content : $("#"+seq).val(),
			todo_date : $("#todo_label"+seq).text(),
			member_seq : 1
		};
    	
    	$.ajax({
    		url : "/todolist/update",
    		type : "put",
    		contentType: 'application/json',
    		data : JSON.stringify(todo_data),
    		dataType: 'json',
    		success : function(e){
    				alert('수정되었습니다.');
    				location.reload();
    		}
    	});
    });
    
    //삭제버튼
    $(".to_do_list").on("click",".delete",function(){
    	var number = $(this).data("number");
    	console.log(number);
    	$.ajax({
    		url: "/todolist/delete/"+number,
    		type:'delete',
    		dataType:'json',
    		success : function(e){
    			if(e.data == true){
    				alert('삭제되었습니다.');
    				location.reload();
    			}
    		}
    	});
    })
    
	//할 일 추가 버튼 클릭
    $(".add").on("click",function(){
		var todo_data = {
			todo_content : $(".todo_content").val(),
			todo_state : "ACTIVE",
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
					if(e.code == 'SUCCESS' && e.data.todo_date == null){
						var item = '<div class="row px-3 align-items-center todo-item rounded">'+
						        '<div class="col-auto m-1 p-0 d-flex align-items-center">'+
						            '<h2 class="m-0 p-0">'+
						                '<i class="fa fa-square-o text-primary btn m-0 p-0 todo_mark" data-number="'+e.data.seq+ '" data-toggle="tooltip" data-placement="bottom" title="Mark as complete"></i>'+
						            '</h2>'+
						        '</div>'+
						        '<div class="col px-1 m-1 d-flex align-items-center">'+
						           '<input type="text" id="'+e.data.seq+'"class="form-control form-control-lg border-0 edit-todo-input bg-transparent rounded px-3" readonly value="'+e.data.todo_content +' " title="Todo Content" />'+
						        '</div>'+
					         	 '<div class="col-auto m-1 p-0 px-3">'+
                   					'<div class="row d-none">'+
                       					'<div class="col-auto d-flex align-items-center rounded bg-white border border-warning">'+
                           					'<i id="todo_date'+e.data.seq+'" class="fa fa-hourglass-2 my-2 px-2 text-warning btn d-none due" data-toggle="tooltip" data-placement="bottom" title="Due" data-original-title="Due on date"></i>'+
                      						'<h6 id="todo_label'+e.data.seq+'" class="text my-2 pr-2">'+e.data.todo_date+'</h6>'+
                        				'</div>'+
                    				'</div>'+
                				'</div>'+						        
						        '<div class="col-auto m-1 p-0 todo-actions">'+
						            '<div class="row d-flex align-items-center justify-content-end">'+
						                '<h5 class="m-0 p-0 px-2">'+
						                    '<i id="todo_check'+e.data.seq+'"class="fa fa-check text-info btn m-0 p-0 d-none" data-number="'+e.data.seq+ '"data-toggle="tooltip" data-placement="bottom" title="Edit todo"></i>'+
						                '</h5>'+						            
						                '<h5 class="m-0 p-0 px-2">'+
						                    '<i class="fa fa-pencil text-info btn m-0 p-0 edit" data-number="'+e.data.seq+ '"data-toggle="tooltip" data-placement="bottom" title="Edit todo"></i>'+
						                '</h5>'+
						                '<h5 class="m-0 p-0 px-2">'+
						                    '<i class="fa fa-trash-o text-danger btn m-0 p-0 delete" data-number="'+e.data.seq+ '" data-toggle="tooltip" data-placement="bottom" title="Delete todo"></i>'+
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
						$(".todo_content").val('');
						$('[data-toggle="tooltip"]').tooltip();	
					}else if(e.code == 'SUCCESS' && e.data.todo_date != null){
						var item = '<div class="row px-3 align-items-center todo-item rounded">'+
						        '<div class="col-auto m-1 p-0 d-flex align-items-center">'+
						            '<h2 class="m-0 p-0">'+
						                '<i class="fa fa-square-o text-primary btn m-0 p-0 todo_mark" data-toggle="tooltip" data-placement="bottom" title="Mark as complete"></i>'+
						            '</h2>'+
						        '</div>'+
						        '<div class="col px-1 m-1 d-flex align-items-center">'+
						           '<input type="text"  id="'+e.data.seq+'" class="form-control form-control-lg border-0 edit-todo-input bg-transparent rounded px-3" readonly value="'+e.data.todo_content +' " title="Todo Content" />'+
						        '</div>'+
					         	 '<div class="col-auto m-1 p-0 px-3">'+
                   					'<div class="row">'+
                       					'<div class="col-auto d-flex align-items-center rounded bg-white border border-warning">'+
                           					'<i id="todo_date'+e.data.seq+'" class="fa fa-hourglass-2 my-2 px-2 text-warning btn d-none due" data-toggle="tooltip" data-placement="bottom" title="Due" data-original-title="Due on date"></i>'+
                      						'<h6 id="todo_label'+e.data.seq+'" class="text my-2 pr-2">'+e.data.todo_date+'</h6>'+
                        				'</div>'+
                    				'</div>'+
                				'</div>'+
						        '<div class="col-auto m-1 p-0 todo-actions">'+
						            '<div class="row d-flex align-items-center justify-content-end">'+
						                '<h5 class="m-0 p-0 px-2">'+
						                    '<i id="todo_check'+e.data.seq+'"class="fa fa-check text-info btn m-0 p-0 d-none" data-number="'+e.data.seq+ '"data-toggle="tooltip" data-placement="bottom" title="Edit todo"></i>'+
						                '</h5>'+								            
						                '<h5 class="m-0 p-0 px-2">'+
						                    '<i class="fa fa-pencil text-info btn m-0 p-0 edit" data-number="'+e.data.seq+ '"data-toggle="tooltip" data-placement="bottom" title="Edit todo"></i>'+
						                '</h5>'+
						                '<h5 class="m-0 p-0 px-2">'+
						                    '<i class="fa fa-trash-o text-danger btn m-0 p-0 delete" data-number="'+e.data.seq+ '" data-toggle="tooltip" data-placement="bottom" title="Delete todo"></i>'+
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
						    $(".todo_content").val('');	
						     $('[data-toggle="tooltip"]').tooltip();						
					}else{
						alert("내용을 입력해 주세요");
					}
			}
		});	
    });
  	//마커 클릭시
		$(document).on("click",".todo_mark",function(){
			
			var seq = $(this).data("number"); //클릭한 할 일의 번호
			var mark = $(this); // 클릭한 마커
			
			if($(this).hasClass("fa-square-o") === true){  // 아직 할 일 상태이면
					//db에 업데이트
					//해당 seq를 가진 todo에 접근해서 todo_state를 completed로 바꿔줌
					var data = {
						seq : seq,
						todo_state : "COMPLETED",
					};					
					
					$.ajax({
						url : "/todolist/update",
						type : "put",
						data : JSON.stringify(data),
						contentType : "application/json",
						dataType : 'json',
						success : function(){
							location.reload(); //페이지 새로고침
						}
					});
			}else{ 
					//완료 상태이면
					//db에 업데이트
					//해당 seq를 가진 todo에 접근해서 todo_state를 ACTIVE로 바꿔줌
					var data = {
						seq : seq,
						todo_state : "ACTIVE",
					};					
					
					$.ajax({
						url : "/todolist/update",
						type : "put",
						data : JSON.stringify(data),
						contentType : "application/json",
						dataType : 'json',
						success : function(){
							location.reload();
						}
					});
			}
		});   
		
		
		//Filter 적용시 
		$('#select').change(function() {
		    var data = $(this).val();
			
			$.ajax({
				url : "/todolist/select",
				type : 'get',
				contentType : 'text',
				data : {
					select : data,
				},
				success: function(e){
					//목록 창 클리어
					$(".to_do_list").empty();
					
					for(var i=0;i<e.data.length;i++){
							var item = '<div class="row px-3 align-items-center todo-item rounded">'+
							        '<div class="col-auto m-1 p-0 d-flex align-items-center">'+
							            '<h2 class="m-0 p-0">';
					         if(e.data[i].todo_state == "ACTIVE"){
					         	item += '<i class="fa fa-square-o text-primary btn m-0 p-0 todo_mark" data-number="'+e.data[i].seq +'" data-toggle="tooltip" data-placement="bottom" title="Mark as complete"></i></h2></div><div class="col px-1 m-1 d-flex align-items-center">'
					         }else if(e.data[i].todo_state == "COMPLETED"){
					         	item += '<i class="fa fa-check-square-o text-primary btn m-0 p-0 todo_mark" data-number="'+e.data[i].seq +'" data-toggle="tooltip" data-placement="bottom" title="Mark as do"></i></h2></div><div class="col px-1 m-1 d-flex align-items-center">'		
					         }           	
					         if(e.data[i].todo_state == "ACTIVE"){
					         	item  += '<input type="text" id="'+e.data[i].seq +'" class="form-control form-control-lg border-0 edit-todo-input bg-transparent rounded px-3" readonly value="'+e.data[i].todo_content +'" title="'+e.data[i].todo_content +'" /></div>'
					         }else if(e.data[i].todo_state == "COMPLETED"){
					         	item += '<input type="text" id="'+e.data[i].seq +'" class="form-control form-control-lg border-0 edit-todo-input bg-transparent rounded px-3" style="text-decoration: line-through;" readonly value="'+e.data[i].todo_content +'" title="'+e.data[i].todo_content +'" /></div>'
					         } 
							if(e.data[i].todo_date != null){
								item += 
						          '<div class="col-auto m-1 p-0 px-3">'+
	                   					'<div class="row">'+
	                       					'<div class="col-auto d-flex align-items-center rounded bg-white border border-warning">'+
	                           					'<i id="todo_date'+e.data[i].seq+'" class="fa fa-hourglass-2 my-2 px-2 text-warning btn d-none due" data-toggle="tooltip" data-placement="bottom" title="Due" data-original-title="Due on date"></i>'+
	                      						'<h6 id="todo_label'+e.data[i].seq+'" class="text my-2 pr-2">'+e.data[i].todo_date+'</h6>'+
	                        				'</div>'+
	                    				'</div>'+
	                				'</div>';									
							}
							item += '<div class="col-auto m-1 p-0 todo-actions">';
							if(e.data[i].todo_state == "ACTIVE"){
								item += 
					                    '<div class="row d-flex align-items-center justify-content-end">'+
					                   		'<h5  id="todo_check'+e.data[i].seq+'" class="m-0 p-0 px-2 d-none">'+
					                            '<i class="fa fa-check text-info btn m-0 p-0 text-primary check" data-number="'+e.data[i].seq+'" data-toggle="tooltip" data-placement="bottom" title="수정 완료"></i>'+
					                        '</h5>'+
					                        '<h5 id="todo_cancle'+e.data[i].seq+'" class="m-0 p-0 px-2 d-none">'+
					                            '<i class="fa fa-times text-info btn m-0 p-0 text-danger cancle" data-number="'+e.data[i].seq+'" data-toggle="tooltip" data-placement="bottom" title="수정 취소"></i>'+
					                        '</h5>'+
					                        '<h5  id="todo_edit'+e.data[i].seq+'" class="m-0 p-0 px-2">'+
					                            '<i class="fa fa-pencil text-info btn m-0 p-0 edit" data-number="'+e.data[i].seq+'" data-toggle="tooltip" data-placement="bottom" title="Edit todo"></i>'+
					                        '</h5>'+
					                        '<h5  id="todo_delete'+e.data[i].seq+'" class="m-0 p-0 px-2">'+
					                            '<i  class="fa fa-trash-o text-danger btn m-0 p-0 delete" data-number="'+e.data[i].seq+'" data-toggle="tooltip" data-placement="bottom" title="Delete todo"></i>'+
					                        '</h5>'+
					                    '</div>';
							}
							item += 
								        '<div class="row todo-created-info">'+
								        	'<div class="col-auto d-flex align-items-center pr-2">'+
								            	'<i class="fa fa-info-circle my-2 px-2 text-black-50 btn" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Created date"></i>'+
								               	'<label class="date-label my-2 text-black-50">'+e.data[i].reg_date+'</label>'+
								          	'</div>'+
								          '</div>'+
								       '</div>'+
								    '</div>';					
							$(".to_do_list").append(item);
							$('[data-toggle="tooltip"]').tooltip();	
					}
				}
			});
			
			
		});
		 
};