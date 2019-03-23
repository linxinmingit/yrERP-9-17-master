/*
* @Author: 94468
* @Date:   2018-03-16 18:24:47
* @Last Modified by:   Jeffrey Wang
* @Last Modified time: 2018-09-18 20:44:36
*/
// 节点树
layui.define(['jquery', 'form'], function(exports){
	$ = layui.jquery;
	form = layui.form;

	obj = {
		// 渲染 + 绑定事件
		openIconContent: '&#xe625;',
		closeIconContent: '&#xe623;',
		checkedNode : [],
		notCheckedNode : [],
		/**
		 * 渲染DOM并绑定事件
		 * @param  {[type]} dst       [目标ID，如：#test1]
		 * @param  {[type]} trees     [数据，格式：{}]
		 * @param  {[type]} inputname [上传表单名]
		 * @param  {[type]} layfilter [lay-filter的值]
		 * @param  {[type]} openall [默认展开全部]
		 * @return {[type]}           [description]
		 */
		render: function(dst, trees, opt){
			var inputname = opt.inputname ? opt.inputname : 'menuids[]';
			var layfilter = opt.layfilter ? opt.layfilter : 'checkauth';
			var openall = opt.openall ? opt.openall : false;
			var autowidth = opt.autowidth !== false ? true : false;

			$(dst).html(obj.renderAuth(trees, 0, {inputname: inputname, layfilter: layfilter, openall: openall}));
			// 变动则存一下临时状态
			obj._saveNodeStatus(dst);
			form.render();

			// 如果开启自动宽度优化
			if (autowidth) {
				$(dst).css({
					'whiteSpace': 'nowrap',
					'maxWidth' : '100%',
				});
				$(dst).find('.layui-form-checkbox').each(function(index, item){
					if ($(this).is(':hidden')) {
						// 比较奇葩的获取隐藏元素宽度的手法，请见谅
						$('body').append('<div id="layui-authtree-get-width">'+$(this).html()+'</div>');
						$width = $('#layui-authtree-get-width').find('span').width() + $('#layui-authtree-get-width').find('i').width() + 25;
						$('#layui-authtree-get-width').remove();
					} else {
						$width = $(this).find('span').width() + $(this).find('i').width() + 25;
					}
					$(this).width($width);
				});
			}
			// 备注：如果使用form.on('checkbox()')，外部就无法使用form.on()监听同样的元素了（LAYUI不支持重复监听了）。
			// form.on('checkbox('+layfilter+')', function(data){
			// 	/*属下所有权限状态跟随，如果选中，往上走全部选中*/
			// 	var childs = $(data.elem).parent().next().find('input[type="checkbox"]').prop('checked', data.elem.checked);
			// 	if(data.elem.checked){
			// 		/*查找child的前边一个元素，并将里边的checkbox选中状态改为true。*/
			// 		$(data.elem).parents('.auth-child').prev().find('input[type="checkbox"]').prop('checked', true);
			// 	}
			// 	/*console.log(childs);*/
			// 	form.render('checkbox');
			// });
			$(dst).find('.auth-single:first').unbind('click').on('click', '.layui-form-checkbox', function(){
				var elem = $(this).prev();
				var checked = elem.is(':checked');

				// 变动则存一下临时状态
				obj._saveNodeStatus(dst);

				var childs = elem.parent().next().find('input[type="checkbox"]').prop('checked', checked);
				if(checked){
					/*查找child的前边一个元素，并将里边的checkbox选中状态改为true。*/
					elem.parents('.auth-child').prev().find('input[type="checkbox"]').prop('checked', true);
				}
				/*console.log(childs);*/
				form.render('checkbox');
			});

			/*动态绑定展开事件*/
			$(dst).unbind('click').on('click', '.auth-icon', function(){
				var origin = $(this);
				var child = origin.parent().parent().find('.auth-child:first');
				if(origin.is('.active')){
					/*收起*/
					origin.removeClass('active').html(obj.closeIconContent);
					child.slideUp('fast');
				} else {
					/*展开*/
					origin.addClass('active').html(obj.openIconContent);
					child.slideDown('fast');
				}
				return false;
			})
		},
		// 递归创建格式
		renderAuth: function(tree, dept, opt){
			var inputname = opt.inputname;
			var layfilter = opt.layfilter;
			var openall = opt.openall;
			var str = '<div class="auth-single">';

			layui.each(tree, function(index, item){
				var hasChild = item.list ? 1 : 0;
				// 注意：递归调用时，this的环境会改变！
				var append = hasChild ? obj.renderAuth(item.list, dept+1, opt) : '';

				// '+new Array(dept * 4).join('&nbsp;')+'
				str += '<div><div class="auth-status"> '+
					(hasChild?'<i class="layui-icon auth-icon '+(openall?'active':'')+'" style="cursor:pointer;">'+(openall?obj.openIconContent:obj.closeIconContent)+'</i>':'<i class="layui-icon auth-leaf" style="opacity:0;">&#xe626;</i>')+
					(dept > 0 ? '<span>├─ </span>':'')+//value="'+item.id是复选框的值
					'<input type="checkbox" name="'+inputname+'" title="'+item.name+'" value="'+item.id+'" lay-skin="primary" lay-filter="'+layfilter+'" '+
					(item.checked?'checked="checked"':'')+'> </div>'+
					' <div class="auth-child" style="'+(openall?'':'display:none;')+'padding-left:40px;"> '+append+'</div></div>'
			});
			str += '</div>';
			return str;
		},
		// 动态获取最大深度
		getMaxDept: function(dst){
			var next = $(dst);
			var dept = 1;
			while(next.length && dept < 100000) {
				next = this._getNext(next);
				if (next.length) {
					dept++;
				} else {
					break;
				}
			}
			return dept;
		},
		// 全选
		checkAll: function(dst){
			var origin = $(dst);
			// 控制好第一层即可
			origin.find('.auth-single:first>div>.auth-status input').each(function(index, item) {
				if ($(this).is(':checked')) {
					// 如果不更新checked状态，，容易产生状态读取错误的卡顿BUG
					$(this).prop('checked', true).next().click(function(){$(this).click()});
				} else {
					$(this).next().click();
				}
			});
		},
		// 全不选
		uncheckAll: function(dst){
			var origin = $(dst);
			// 控制好第一层即可
			origin.find('.auth-single:first>div>.auth-status input').each(function(index, item) {
				if ($(this).is(':checked')) {
					$(this).prop('checked', true).next().click();
				} else {
					// $(this).parent().click().click();
				}
			});
		},
		// 显示整个树
		showAll: function(dst) {
			this.showDept(dst, this.getMaxDept(dst));
		},
		// 关闭整颗树
		closeAll: function(dst) {
			this.closeDept(dst, 1);
		},
		// 切换整颗树的显示/关闭
		toggleAll: function(dst) {
			if (this._shownDept(2)) {
				this.closeDept(dst);
			} else {
				this.showAll(dst);
			}
		},
		// 显示到第 dept 层
		showDept: function(dst, dept) {
			var next = $(dst);
			for(var i = 1; i < dept; i++) {
				next = this._getNext(next);
				if (next.length) {
					this._showSingle(next);
				} else {
					break;
				}
			}
		},
		// 第 dept 层之后全部关闭
		closeDept: function(dst, dept) {
			var next = $(dst);
			for(var i = 0; i < dept; i++){
				next = this._getNext(next);
			}
			while(next.length) {
				this._closeSingle(next);
				next = this._getNext(next);
			}
		},
		// 临时保存所有节点信息状态
		_saveNodeStatus: function(dst){
			this.checkedNode = this.getChecked(dst);
			this.notCheckedNode = this.getNotChecked(dst);
		},
		// 判断某一层是否显示
		_shownDept: function(dst, dept) {
			var next = $(dst);
			for(var i = 0; i < dept; i++){
				next = this._getNext(next);
			}
			return !next.is(':hidden');
		},
		// 获取
		_getNext: function(dst) {
			return $(dst).find('.auth-single:first>div>.auth-child');
		},
		// 显示某层 single
		_showSingle: function(dst) {
			var origin = $(dst).find('.auth-single:first');
			var parentChild = origin.parent();
			var parentStatus = parentChild.prev();
			if (!parentStatus.find('.auth-icon').hasClass('active')) {
				parentChild.show();
				// 显示上级的 .auth-child节点，并修改.auth-status的折叠状态
				parentStatus.find('.auth-icon').addClass('active').html(obj.openIconContent);
			}
		},
		// 关闭某层 single
		_closeSingle: function(dst) {
			var origin = $(dst).find('.auth-single:first');
			var parentChild = origin.parent();
			var parentStatus = parentChild.prev();
			if (parentStatus.find('.auth-icon').hasClass('active')) {
				parentChild.hide();
				// 显示上级的 .auth-child节点，并修改.auth-status的折叠状态
				parentStatus.find('.auth-icon').removeClass('active').html(obj.closeIconContent);
			}
		},
		// 获取选中叶子结点
		getLeaf: function(dst){
			var leafs = $(dst).find('.auth-leaf').parent().find('input[type="checkbox"]:checked');
			var data = [];
			leafs.each(function(index, item){
				// console.log(item);
				data.push(item.value);
			});
			// console.log(data);
			return data;
		},
		// 获取所有节点数据
		getAll: function(dst){
			var inputs = $(dst).find('input[type="checkbox"]');
			var data = [];
			inputs.each(function(index, item){
				data.push(item.value);
			});
			// console.log(data);
			return data;
		},
		// 最新选中（之前取消-现在选中）
		getLastChecked: function(dst) {
			var lastCheckedNode = this.getChecked();

			var data = [];
			for (i in lastCheckedNode) {
				if ($.inArray(lastCheckedNode[i], this.notCheckedNode)) {
					data.push(lastCheckedNode[i]);
				}
			}
			return data;
		},
		// 获取所有选中的数据
		getChecked: function(dst){
			var inputs = $(dst).find('input[type="checkbox"]:checked');
			var data = [];
			inputs.each(function(index, item){
				data.push(item.value);
			});
			// console.log(data);
			return data;
		},
		// 最新取消（之前选中-现在取消）
		getLastNotChecked: function(dst) {
			var lastNotCheckedNode = this.getNotChecked();

			var data = [];
			for (i in lastNotCheckedNode) {
				if ($.inArray(lastNotCheckedNode[i], this.checkedNode)) {
					data.push(lastNotCheckedNode[i]);
				}
			}
			return data;
		},
		// 获取未选中数据
		getNotChecked: function(dst){
			var inputs = $(dst).find('input[type="checkbox"]:not(:checked)');
			var data = [];
			inputs.each(function(index, item){
				data.push(item.value);
			});
			// console.log(data);
			return data;
		}
	}
	exports('authtree', obj);
});