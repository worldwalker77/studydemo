/**
 * 网络连接层的回调函数,方法由具体实例去实现
 */
function ConnectLayCallBack() {
}
ConnectLayCallBack.prototype.success = function(data) {
};
ConnectLayCallBack.prototype.error = function(data) {
};
ConnectLayCallBack.prototype.complete = function(data) {
};
var urlCollection = {
		frontUserRegisterUrl :'/frontUser/doRegister'
};
var commonUtil = {
		
		ajax : function(url, params, success, error, timeout, async){
			try {
				timeout = timeout ? timeout : 10000;
				var async = typeof (async) == 'undefined' ? true : async;
				return $.ajax({
					url : url,
					type : 'POST',
					dataType : "json",
					async : async,
					cache : false,
					timeout : timeout,
					data : params,
					success : success ? success : function(data, textStatus) {
					},
					error : error ? error : function(XMLHttpRequest,
							textStatus, errorThrown) {
						if (textStatus == "timeout") {
						}
					}
				});
			} catch (e) {
				commonUtil.Log.error(e);
			}
		},

		Log : {
			info : function(e){
				try {
					if(console && console.log){
						/*console.log(e);*/
					}
				} catch (e) {}
			},
			error : function(e){
				try {
					if(console && console.error){
						/*console.error(e);*/
					}
				} catch (e) {}
			}
		},
		
};