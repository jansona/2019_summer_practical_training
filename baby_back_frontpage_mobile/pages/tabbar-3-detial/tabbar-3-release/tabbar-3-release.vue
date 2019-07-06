<template>
	<view>
		<form @submit="formSubmit">
			<view class="cu-form-group">
				<view class="title">标题</view>
				<input placeholder="请输入标题" name="title" v-model="title"></input>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">内容</view>
				<textarea style="height: 930upx;" name="content" maxlength="-1" v-model="content" placeholder="请输入具体的内容"></textarea>
			</view>
			<view class="cu-form-group">
				<view class="action">
					图片上传
				</view>
				<view class="action">
					{{imgList.length}}/4
				</view>
			</view>
			<view class="cu-form-group">
				<button class="cu-btn round" style="width:250upx;margin: 0 auto;background-image: 'url('../../../static/img/pkq.png')';"
				 form-type="submit">发布</button>
			</view>
			
			<!--模态框提示-->
			<view class="cu-modal" :class="modalName=='Modal'?'show':''">
				<view class="cu-dialog">
					<view class="cu-bar bg-white justify-end">
						<view class="content">发布提示</view>
						<view class="action" @tap="hideModal">
							<text class="cuIcon-close text-red"></text>
						</view>
					</view>
					<view class="padding-xl">
						{{modalContent}}
					</view>
				</view>
			</view>
		</form>

	</view>
</template>

<script>
	var graceChecker = require("../../../common/graceChecker.js");
	export default {
		onReady: function() {
			//let userId=this.$store.state.userId
			console.log('ON READY...');
			let userId = 2;
			this.getUser(userId)
		},
		data() {
			return {
				modalName:null,
				modalContent:null,
				imgList: [],
				content: '',
				title: '',
				user: {},
				article: {
					"content": "",
					"date": "",
					"likeNum": 0,
					"replyNum": 0,
					"title": "",
					"user": {
					},
					"viewNum": 0
				}
			}
		},
		methods: {
			hideModal(e) {
				this.modalName = null
			},
			contentInput(e) {
				this.content = e.detail.value
			},
			formSubmit: function(e) {
				//将下列代码加入到对应的检查位置
				//定义表单规则
				var rule = [{
						name: "title",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "标题不可为空"
					},
					{
						name: "content",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "内容不可为空"
					}
				];
				//进行表单检查
				var formData = e.detail.value;
				var checkRes = graceChecker.check(formData, rule);
				if (checkRes) {
					uni.showToast({
						title: "验证通过!",
						icon: "none"
					});
				} else {
					uni.showToast({
						title: graceChecker.error,
						icon: "none"
					});
					return
				}
				this.article.content=this.content;
				this.article.title=this.title;
				this.article.date=new Date();
				this.article.user=this.user;
				let _this=this;
				let url=this.URLS.articleInsertUrl;
				this.$api.put(url,this.article).then(data => {
					console.log(data);
					_this.modalName='Modal'
					_this.modalContent='发布成功！'
				}).catch(error => {
					console.log(error);
					_this.modalName='Modal';
					_this.modalContent='发布失败！'
				})
			},
			getUser(userId) {
				let url = this.URLS.userFindByIdUrl + '?id=' + userId;
				let _this = this
				this.$api.post(url).then(data => {
					_this.user = data.data.data
				}).catch(error => {
					console.log(error)
				})
			}
		}
	}
</script>

<style>
	page {
		height: auto;
		min-height: 100%;
	}
</style>
