<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">Trang
							chủ</a></li>
					<li>Thêm sản phẩm</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<form id="formEdit">
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Tên
							sản phẩm</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="name" value="${model.name}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Diện
							tích sàn</label>
						<div class="col-sm-9">
							<input type="number" class="form-control" name="buildingArea" value="${model.buildingArea}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Quận</label>
						<div class="col-sm-9">
							<select class="custom-select" id="quan" name="district">
								<option value="">--Chọn quận--</option>
								<c:forEach var="item" items="${districts}">
									<option value="${item.key}"
										${item.key == model.district ? 'selected' : ''}>${item.value}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Phường</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="ward" value="${model.ward }"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Đường</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="street" value="${model.street }"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Hướng</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="direction" value="${model.direction}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Hạng</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="level" value="${model.level}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Số
							tầng hầm</label>
						<div class="col-sm-9">
							<input type="number" class="form-control" name="numberOfBasement" value="${model.numberOfBasement}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Diện
							tích thuê</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="rentArea" value="${model.rentArea}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Giá
							thuê</label>
						<div class="col-sm-9">
							<input type="number" class="form-control" name="costRent" value="${model.costRent}"/>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Tên
							quản lý</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="managerName" value="${model.managerName}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Số
							điện thoại quản lý</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="managerPhone" value="${model.managerPhone}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Loại
							tòa nhà</label>
						<div class="col-sm-9">
							<div class="fg-line">
								<c:forEach var="item" items="${buildingTypes}">
									<label class="checkbox-inline"> <input type="checkbox"
										value="${item.key}" name="buildingTypes"
										${fn:contains(fn:join(model.buildingTypes, ','), item.key) ? 'checked' : ''}><b>${item.value}</b></label>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				</form>
				<!-- <div class="row">
					<div class="form-group abc">
						<label class="col-sm-3 control-label no-padding-right">Upload
							sản phẩm</label>
						<div class="col-sm-9">
							<input type="file" class="form-control" />
						</div>
					</div>
				</div> -->
				<div class="row text-center btn-addsp">
					<c:if test="${empty model.id}">
						<button class="btn btn-success" id="btnAddorUpdateBuilding">Thêm tòa nhà</button>						
					</c:if>
					<c:if test="${not empty model.id}">
						<button class="btn btn-success" id="btnAddorUpdateBuilding">Cập nhật tòa nhà</button>						
					</c:if>
					
				</div>
			</div>
		</div>
	</div>
	<!-- /.main-content -->

</body>
</html>