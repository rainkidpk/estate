<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách tòa nhà</title>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">Trang
							chủ</a></li>
					<li>Danh sách sản phẩm</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">

						<div class="widget-box table-filter">
							<div class="widget-header">
								<h4 class="widget-title">Tìm kiếm</h4>
								<div class="widget-toolbar">
									<a href="#" data-action="collapse"> <i
										class="ace-icon fa fa-chevron-up"></i>
									</a>
								</div>
							</div>
							<div class="widget-body">
								<div class="widget-main">
									<div class="form-horizontal">
										<div class="form-group">
											<div class="col-sm-6">
												<label><b>Tên Sản phẩm</b></label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-6">
												<label><b>Diện tích sàn</b></label>
												<div class="fg-line">
													<input type="number" class="form-control input-sm" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-4">
												<label><b>Quận hiện có</b></label>
												<div class="fg-line">
													<select class="custom-select" id="sel1">
														<option selected>--Chọn quận--</option>
														<option>1</option>
														<option>2</option>
														<option>3</option>
														<option>4</option>
													</select>
												</div>
											</div>
											<div class="col-sm-4">
												<label><b>Phường</b></label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-4">
												<label><b>Đường</b></label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-4">
												<label><b>Số tầng hầm</b></label>
												<div class="fg-line">
													<input type="number" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-4">
												<label><b>Hướng</b></label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-4">
												<label><b>Hạng</b></label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-3">
												<label><b>Diện tích từ</b></label>
												<div class="fg-line">
													<input type="number" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-3">
												<label><b>Diện tích đến</b></label>
												<div class="fg-line">
													<input type="number" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-3">
												<label><b>Giá thuê từ</b></label>
												<div class="fg-line">
													<input type="number" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-3">
												<label><b>Giá thuê đến</b></label>
												<div class="fg-line">
													<input type="number" class="form-control input-sm" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-4">
												<label><b>Tên Quản lý</b></label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-4">
												<label><b>Điện thoại quản lý</b></label>
												<div class="fg-line">
													<input type="text" class="form-control input-sm" />
												</div>
											</div>
											<div class="col-sm-4">
												<label><b>Nhân viên phụ trách</b></label>
												<div class="fg-line">
													<select class="custom-select" id="sel1">
														<option selected>--Chọn nhân viên phụ trách--</option>
														<option>1</option>
														<option>2</option>
														<option>3</option>
														<option>4</option>
													</select>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-6">
												<label>Loại tòa nhà</label>
												<div class="fg-line">
													<label class="checkbox-inline"><input
														type="checkbox" value=""><b>Tầng trệt</b></label> <label
														class="checkbox-inline"><input type="checkbox"
														value=""><b>Nguyên căn</b></label> <label
														class="checkbox-inline"><input type="checkbox"
														value=""><b>Nội thất</b></label>
												</div>
											</div>

										</div>
									</div>
									<div class="btn btn-success">
										Tìm kiếm <i class="fa fa-arrow-right"></i>
									</div>
								</div>



							</div>
						</div>


						<!-- button add, delete -->
						<div class="table-btn-controls">
							<div class="pull-right tableTools-container">
								<div class="dt-buttons btn-overlap btn-group">
									<a flag="info"
										class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
										data-toggle="tooltip" title='Thêm tòa nhà'
										href='<c:url value="/admin-building?action=EDIT"/>'> <span><i
											class="fa fa-plus-circle bigger-110 purple"></i></span>
									</a>
									<button type="button"
										class="dt-button buttons-html5 btn btn-white btn-primary btn-bold"
										data-toggle="tooltip" title='Xóa tòa nhà'>
										<span><i class="fa fa-trash-o bigger-110 pink"></i></span>
									</button>
								</div>
							</div>
						</div>

					</div>

				</div>
			</div>
		</div>
	</div>
	</div>
	<!-- /.main-content -->
</body>
</html>