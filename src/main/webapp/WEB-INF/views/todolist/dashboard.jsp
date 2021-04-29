<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
						<label class="text-secondary my-2 p-0 px-1 view-opt-label due-date-label d-none"></label> <i class="fa fa-calendar my-2 px-1 text-primary btn due-date-button" data-toggle="tooltip"
							data-placement="bottom" title="Set a Due date"></i>
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
				<label class="text-secondary my-2 pr-2 view-opt-label">Filter</label> <select class="custom-select custom-select-sm btn my-2">
					<option value="all" selected>All</option>
					<option value="completed">Completed</option>
					<option value="active">Active</option>
					<option value="has-due-date">Has due date</option>
				</select>
			</div>
			<div class="col-auto d-flex align-items-center px-1 pr-3">
				<label class="text-secondary my-2 pr-2 view-opt-label">Sort</label> <select class="custom-select custom-select-sm btn my-2">
					<option value="added-date-asc" selected>Added date</option>
					<option value="due-date-desc">Due date</option>
				</select> <i class="fa fa fa-sort-amount-asc text-info btn mx-0 px-0 pl-1" data-toggle="tooltip" data-placement="bottom" title="Ascending"></i> <i
					class="fa fa fa-sort-amount-desc text-info btn mx-0 px-0 pl-1 d-none" data-toggle="tooltip" data-placement="bottom" title="Descending"></i>
			</div>
		</div>
		<!-- Todo list section -->
		<div class="row mx-1 px-5 pb-3 w-80">
			<div class="col mx-auto to_do_list">
			
			</div>
		</div>
	</div>
	<!-- partial -->
	<script src='https://code.jquery.com/jquery-3.3.1.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>
	<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js'></script>
	<script src='https://stackpath.bootstrapcdn.com/bootlint/1.1.0/bootlint.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js'></script>
	<script src="../js//script.js"></script>
</body>
</html>