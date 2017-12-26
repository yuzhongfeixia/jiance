<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function confimChangePassword(){
		var valdatFlg = $(passWordForm).data("validator").checkAll(false, false);
		if(valdatFlg){
			modalConfirm('修改密码后会注销登陆,是否确认修改?',{
				callback: function () {
					// 保存
					$(saveChangePassword).click();
					// 注销登陆 延迟是为了显示操作成功的提示，否则会直接注销
					setTimeout(function(){window.location.href = 'loginController.do?logout'},500);
				}
			});
		}
	}
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box popupBox_usual">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-reorder"></i> <span class="hidden-480">修改密码</span>
				</div>
			</div>
			<div class="portlet-body">
				<form id="passWordForm" name="passWordForm" class="form-horizontal"  validate="true">
					<input id="id" type="hidden" value="${user.id}">
					<div class="control-group">
						<label class="control-label">原密码:</label>
						<div class="controls">
							<input type="password" id="password" name="password" class="inputxt" datatype="*" ajaxurl="userController.do?checkPassword" nullmsg="原密码不能为空！"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">新密码:</label>
						<div class="controls">
							<input type="password" value="" name="newpassword" class="inputxt" plugin="passwordStrength" datatype="*6-100"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">确认密码:</label>
						<div class="controls">
							<input id="newpassword" type="password" recheck="newpassword" datatype="*6-20" errormsg="两次输入的密码不一致！">
							<span class="Validform_checktip"></span>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn popclose">关闭</button>
						<button type="button" class="btn popenter" onClick="confimChangePassword()">保存</button>
						<button id="saveChangePassword" href="userController.do?savenewpwd" action-operation="popsave" action-pop="#password_modal"
								action-mode="ajax" action-form="#passWordForm" style="display:none;">保存</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>