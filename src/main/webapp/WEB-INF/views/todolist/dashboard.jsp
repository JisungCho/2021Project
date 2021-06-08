<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>CodePen - Bootstrap - Todo Design</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css'>
<link rel='stylesheet' href='https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,600;0,700;0,800;1,300;1,400;1,600;1,700;1,800&amp;display=swap'>
<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.standalone.min.css'>
<link rel="stylesheet" href="../css/style.css">

</head>
<body>
	<!-- partial:index.partial.html -->
	<div class="container m-5 p-2 rounded mx-auto bg-light shadow">
		<!-- App title section -->
		<div class="row m-1 p-4">
			<div class="col">
				<div class="p-1 h1 text-primary text-center mx-auto display-inline-block">
					<i class="fa fa-check bg-primary text-white rounded p-2"></i> <u>My Todo</u>
				</div>
				<c:if test="${not empty pageContext.request.userPrincipal }">
					<a href="/logout" class="btn btn-sm fa fa-sign-out bg-info text-white float-right">로그아웃</a>
				</c:if>
			</div>
		</div>
		<!-- Create todo section -->
		<div class="row m-1 p-3">
			<div class="col col-11 mx-auto">
				<div class="row bg-white rounded shadow-sm p-2 add-todo-wrapper align-items-center justify-content-center">
					<div class="col">
						<input class="todo_content form-control form-control-lg border-0 add-todo-input bg-transparent rounded" type="text" placeholder="Add new ..">
					</div>
					<div class="col-auto m-0 px-2 d-flex align-items-center">
							<label class="text-secondary my-2 p-0 px-1 view-opt-label due-date-label d-none"></label> 
							<i class="fa fa-calendar my-2 px-1 text-primary btn due-date-button" data-toggle="tooltip"data-placement="bottom" title="Set a Due date"></i>
					</div>
					<div class="col-auto px-0 mx-0 mr-2">
						<button type="button" class="btn btn-primary add">Add</button>
					</div>
				</div>
			</div>
		</div>
		<div class="p-2 mx-4 border-black-25 border-bottom"></div>
		<!-- View options section -->
		<div class="row m-1 p-3 px-5 justify-content-end">
			<div class="col-auto d-flex align-items-center">
				<label class="text-secondary my-2 pr-2 view-opt-label">Filter</label> 
				<select id="select" class="custom-select custom-select-sm btn my-2">
					<option value="ALL" selected>All</option>
					<option value="COMPLETED">Completed</option>
					<option value="ACTIVE">Active</option>
					<option value="HAS_DUE_DATE">Has due date</option>
				</select>
			</div>
		</div>
		<!-- Todo list section -->
		<div class="row mx-1 px-5 pb-3 w-80">
			<div class="col mx-auto to_do_list">
				<c:forEach items="${todolist}" var="todo">
		            <div class="row px-3 align-items-center todo-item rounded">
		                <div class="col-auto m-1 p-0 d-flex align-items-center">
		                    <h2 class="m-0 p-0">
		                    <!-- todo_state의 상태에 따라서 마커 설정 -->
		                    	<c:if test="${todo.todo_state == 'ACTIVE' || todo.todo_state == 'HAS_DUE_DATE' }">
		                    		<i class="fa fa-square-o text-primary btn m-0 p-0 todo_mark" data-number="${todo.seq }" data-toggle="tooltip" data-placement="bottom" title="Mark as complete"></i>
		                    	</c:if>
		                        <c:if test="${todo.todo_state == 'COMPLETED'}">
		                    		<i class="fa fa-check-square-o text-primary btn m-0 p-0 todo_mark" data-number="${todo.seq }" data-toggle="tooltip" data-placement="bottom" title="Mark as do"></i>
		                    	</c:if>
		                    </h2>
		                </div>
		                <div class="col px-1 m-1 d-flex align-items-center">
		                	<!-- todo_state의 상태에 따라서 todo내용 설정 -->
		                	<c:if test="${todo.todo_state == 'ACTIVE' || todo.todo_state == 'HAS_DUE_DATE' }">
		                		<input type="text" id="${todo.seq }" class="form-control form-control-lg border-0 edit-todo-input bg-transparent rounded px-3" readonly value="${todo.todo_content }" title="${todo.todo_content }" />
		                	</c:if>		                	
		                   	<c:if test="${todo.todo_state == 'COMPLETED' }">
		                		<input type="text" id="${todo.seq }" class="form-control form-control-lg border-0 edit-todo-input bg-transparent rounded px-3" style="text-decoration: line-through;" readonly value="${todo.todo_content }" title="${todo.todo_content }" />
		                	</c:if>
		                </div>
				        <div id="todo_date_row${todo.seq}" class="col-auto m-1 p-0 px-3">
				       		<c:if test="${todo.todo_date != null }"> <!--알림 날짜가 있는 경우 -->	            
								<div class="row">
				                 	<div class="col-auto d-flex align-items-center rounded bg-white border border-warning">
				                 		<c:set var="today" value="<%=new java.util.Date()%>" />
				                 		<c:set var="now"><fmt:formatDate value="${today}" pattern="yyyy/MM/dd" /></c:set> 
				                 		<c:set var="date"><fmt:formatDate value="${todo.todo_date}" pattern="yyyy/MM/dd" /></c:set>
										<c:choose>
					                        <c:when test="${todo.todo_state == 'HAS_DUE_DATE'  || date < now}">
					                        	<h6 id="todo_label${todo.seq }" class="text my-2 pr-2 text-danger font-italic font-weight-bold"><fmt:formatDate value="${todo.todo_date }" pattern="yyyy/MM/dd"/></h6>
											</c:when>
					                        <c:when test="${todo.todo_state != 'HAS_DUE_DATE'}">
					                        	<h6 id="todo_label${todo.seq }" class="text my-2 pr-2 font-italic"><fmt:formatDate value="${todo.todo_date }" pattern="yyyy/MM/dd"/></h6>
											</c:when>	
										</c:choose>									
									</div>
								</div>
							</c:if>
				     	</div>	
		                <div class="col-auto m-1 p-0 todo-actions">
		                	<!--  todo_state 가 active인 경우에만 수정 할 수 있게끔 -->
			                    <div class="row d-flex align-items-center justify-content-end">
			                    	<c:if test="${todo.todo_state == 'ACTIVE' || todo.todo_state == 'HAS_DUE_DATE' }">
			                        <h5  id="todo_edit${todo.seq }" class="m-0 p-0 px-2">
			                            <i class="fa fa-pencil text-info btn m-0 p-0 edit" data-number="${todo.seq }" data-toggle="tooltip" data-placement="bottom" title="Edit todo"></i>
			                        </h5>
			                        </c:if>
			                        <h5  id="todo_delete${todo.seq }" class="m-0 p-0 px-2">
			                            <i  class="fa fa-trash-o text-danger btn m-0 p-0 delete" data-number="${todo.seq }" data-toggle="tooltip" data-placement="bottom" title="Delete todo"></i>
			                        </h5>
			                        <h5  id="todo_date${todo.seq }" class="m-0 p-0 px-2 d-none">
			                            <i  class="fa fa-hourglass-2 text-warning btn m-0 p-0 due" data-number="${todo.seq }" data-toggle="tooltip" data-placement="bottom" title="Due on date"></i>
			                        </h5>			                    
			                   		<h5  id="todo_check${todo.seq}" class="m-0 p-0 px-2 d-none">
			                            <i class="fa fa-check text-info btn m-0 p-0 text-primary check" data-number="${todo.seq }" data-toggle="tooltip" data-placement="bottom" title="수정 완료"></i>
			                        </h5>
			                        <h5 id="todo_cancle${todo.seq}" class="m-0 p-0 px-2 d-none">
			                            <i class="fa fa-times text-info btn m-0 p-0 text-danger cancle" data-number="${todo.seq }" data-toggle="tooltip" data-placement="bottom" title="수정 취소"></i>
			                        </h5>			                        
			                    </div>
		                    
		                    <div class="row todo-created-info">
		                        <div class="col-auto d-flex align-items-center pr-2">
		                            <i class="fa fa-info-circle my-2 px-2 text-black-50 btn" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Created date"></i>
		                            <label class="date-label my-2 text-black-50">
		                            	<fmt:formatDate value="${todo.reg_date }" pattern="yyyy/MM/dd"/>
		                            </label>
		                        </div>
		                    </div>
		                </div>
		            </div>					
				</c:forEach>
			</div>
		</div>
	</div>
	<!-- partial -->
	<script src='https://code.jquery.com/jquery-3.3.1.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>
	<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js'></script>
	<script src='https://stackpath.bootstrapcdn.com/bootlint/1.1.0/bootlint.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js'></script>
	<script src="../js/script.js"></script>
	<script>history.scrollRestoration = "auto"</script><!-- 스크롤 위치 기억 -->
</body>
</html>