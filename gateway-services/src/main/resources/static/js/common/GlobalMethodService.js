app.factory('GlobalMethodService', function($window, localStorageService  ,$timeout, $rootScope, $location) {
	return {
		globalValidateEmail : function(email) {
			var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
			return re.test(email);
		},
		globalFlashMsg : function(elementId) {
			if (elementId != null) {
				angular.element("#" + elementId)
						.trigger('show');
				// Close the info again
				$timeout(function() {
					angular.element("#" + elementId).trigger(
							'hide');
				}, 5000);
			}
		},
		globalShowSuccessMsg : function() {
			if (elementId != null) {
				angular.element("#" + elementId)
						.trigger('show');
				// Close the info again
				$timeout(function() {
					angular.element("#" + elementId).trigger(
							'hide');
				}, 5000);
			}
		},
		globalHideSuccessMsg : function() {
			if (elementId != null) {
				angular.element("#" + elementId)
						.trigger('show');
				// Close the info again
				$timeout(function() {
					angular.element("#" + elementId).trigger(
							'hide');
				}, 5000);
			}
		},
		sortByKey : function(array, orderBy) {
			key = orderBy.split(" ")[0];
			return array
					.sort(function(a, b) {
						var x = a[key];
						var y = b[key];
						return orderBy.split(" ")[1] == "ASC" ? ((x < y) ? -1
								: ((x > y) ? 1 : 0))
								: ((y < x) ? -1 : ((y > x) ? 1
										: 0));
					});
		},
		inputRequiredFail : function(elementId) {
			if (elementId != null) {
				angular.element("#" + elementId)
						.trigger('show');
				// Close the info again
				$timeout(function() {
					angular.element("#" + elementId).trigger(
							'hide');
				}, 5000);
			}
		},
		inputRequiredPass : function(elementId) {
			if (elementId != null) {
				angular.element("#" + elementId)
						.trigger('show');
				// Close the info again
				$timeout(function() {
					angular.element("#" + elementId).trigger(
							'hide');
				}, 5000);
			}
		},
		inputSuccess : function(elementId) {
			if (elementId != null) {
				angular.element("#" + elementId)
						.trigger('show');
				// Close the info again
				$timeout(function() {
					angular.element("#" + elementId).trigger(
							'hide');
				}, 5000);
			}
		},
		validateEmail : function(email) {
			var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
			return re.test(email);
		},
		validatePhoneNumber : function(phoneNumber) {
			if (phoneNumber.length < 10
					|| phoneNumber.length > 15
					|| isNaN(phoneNumber)) {
				return false;
			} else {
				return true;
			}
		},
		globalUploadFile : function(fileObj, id, url, header,
				async, callback) {
			var self = this;
			angular.element('#loadingdiv').show();
			var $this = jQuery(fileObj);
			var fileName = $this.val();
			var formData = new FormData();
			formData.append("file", fileObj.files[0]);
			formData.append("id", id);
			formData.append("returnRowCount", 10);
			if (async != null && async != 'undefined') {
				formData.append("async", async);
			}
	
			if (fileName.match(/fakepath/)) {
				fileName = fileName.replace(/C:\\fakepath\\/i,
						'');
			}
			if (header != null
					&& (typeof header != "undefined" || header != "")) {
				jQuery.ajax({
					type : "POST",
					url : url,
					cache : false,
					contentType : false,
					headers : header,
					processData : false,
					data : formData,
					success : function(json) {
						return self.globalUploadFileResp(json,
								callback);
					},
					error : function(err) {
						return self.globalUploadFileResp(err,
								callback);
					}
				});
			} else {
				jQuery.ajax({
					type : "POST",
					url : url,
					cache : false,
					contentType : false,
					processData : false,
					data : formData,
					success : function(json) {
						return self.globalUploadFileResp(json,
								callback);
					},
					error : function(err) {
						return self.globalUploadFileResp(err,
								callback);
					}
				});
			}
		},
		globalUploadFileResp : function(responseObj, callback) {
			angular.element('#loadingdiv').hide();
			if (responseObj.status == 200) {
				callback(responseObj.responseText);
			} else {
				callback(responseObj);
			}
		},
		globalCheckLogin : function() {
			if (localStorageService.get('user') == null) {
				$window.location.href = '#/login';
			}
		},
		isNotEmptyString : function(str) {
			return !this.isEmptyString(str);
		},
		isEmptyString : function(str) {
			if (str == null || str == 'undefined'
					|| String(str).trim().length <= 0) {
				return true;
			}
			return false;
		},
		triggerTooltip : function(elementId) {
			if (elementId != null) {
				angular.element("#" + elementId)
						.trigger('show');
				// Close the info again
				var timer = $timeout(function() {
					angular.element("#" + elementId).trigger(
							'hide');
					$timeout.cancel(timer);
				}, 5000);
			}
		},
		showMessage : function(template, messages) {
			$rootScope.message = {
				template : template,
				messages : messages,
				showMessage : true
			};
			$timeout(function() {
				$rootScope.closeMessage();
			}, 10000);
		},
		showMessageForCustomTime : function(template, messages,
				timeInMillis) {
			$rootScope.message = {
				template : template,
				messages : messages,
				showMessage : true
			};
			$timeout(function() {
				$rootScope.closeMessage();
			}, timeInMillis);
		},
		getEventEndDate : function(startDate) {
			var endDate = new Date(startDate);
			endDate.setHours(17);
			endDate.setMinutes(00);
			endDate.setSeconds(00);
			endDate.setMilliseconds(000);
	
			if (new Date(startDate).getTime() >= endDate
					.getTime()) {
				endDate.setHours(23);
				endDate.setMinutes(59);
				endDate.setSeconds(59);
				endDate.setMilliseconds(999);
			}
			return endDate;
		},
		randomString : function(length) {
			var chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
			var result = '';
			for (var i = length; i > 0; --i)
				result += chars[Math.round(Math.random()
						* (chars.length - 1))];
			return result.toUpperCase();
		},
		randomColor : function() {
			var hexCodeArray = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
					'a', 'b', 'c', 'd', 'e', 'f' ];
			var color = '';
			for (var i = 0; i < 6; i++) {
				color += hexCodeArray[Math
						.floor(Math.random() * 16)];
			}
			return '#' + color;
		},
		randomColorFromPallete : function(index) {
			var colorPallete = [ '#00a85d', '#f1ae23',
					'#9a57a5', '#3155a4', '#fa8a3b', '#008cc2',
					'#bc232f', '#af8746', '#6fb245', '#45bd9f',
					'#d44273', '#6b4099' ];
			return colorPallete[index % colorPallete.length];
		},
		validateImportFile : function(fileName) {
			var re = (/\.(csv|xlsx|xls)$/i);
			return re.test(fileName);
		},
		getCookie : function(cname) {
			var name = cname + "=";
			var ca = document.cookie.split(';');
			for (var i = 0; i < ca.length; i++) {
				var c = ca[i];
				while (c.charAt(0) == ' ')
					c = c.substring(1);
				if (c.indexOf(name) == 0)
					return c.substring(name.length, c.length);
			}
			return "";
		},
		
		base64ToArrayBuffer : function(base64) {
			var binaryString = window.atob(base64);
			var binaryLen = binaryString.length;
			var bytes = new Uint8Array(binaryLen);
			for (var i = 0; i < binaryLen; i++) {
				var ascii = binaryString.charCodeAt(i);
				bytes[i] = ascii;
			}
			return bytes;
		},
		isAlphaNumeric : function(str) {
			var re = /^[a-zA-Z0-9]*$/;
			return re.test(str);
		},
		showLoadingDiv : function(elementId) {
			if (elementId != null) {
				angular.element("#" + elementId).removeClass(
						"display_hide");
				angular.element("#" + elementId).addClass(
						"display_show");
			}
		},
		hideLoadingDiv : function(elementId) {
			if (elementId != null) {
				angular.element("#" + elementId).removeClass(
						"display_show");
				angular.element("#" + elementId).addClass(
						"display_hide");
			}
		},
		createCookie : function(name, value, days) {
			var expires = "";
			if (days) {
				var date = new Date();
				date.setTime(date.getTime()
						+ (days * 24 * 60 * 60 * 1000));
				var expires = "; expires=" + date.toGMTString();
			}
			document.cookie = name + "=" + value + expires
					+ "; path=/" + "; domain="
					+ $location.host();
		},
		isBlank: function(obj) {
			return obj == undefined || obj == null || obj == '';
		},
		showNotification : function(msg, typ) {
			$.notify({
				message: msg
			},{
				type: typ
			});
		}
  	};
})