<template>
	<view>
		<form @submit="formSubmit" @reset="formReset">
			<view class="cu-form-group">
				<view class="title">姓名</view>
				<input placeholder="请输入失踪人姓名" name="name" v-model="releaseUrgenceForm.name"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width:100upx;">性别</view>
				<radio-group class="radio-group" name="sex" @change="radioChange">
					<label class="radio" v-for="(item,index) in items" :key=index>
						<radio :value="item.value" :checked="item.checked" color="#FFCC33" style="transform:scale(0.7)" />{{item.value}}
					</label>
				</radio-group>
			</view>
			<view class="cu-form-group">
				<view class="title">出生日期</view>
				<picker mode="date" :value="releaseUrgenceForm.birthday" start="1910-09-01" end="2020-09-01" name="birthday" @change="BirthDateChange">
					<view class="picker">
						{{releaseUrgenceForm.birthday}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="uni-title">身高</view>
				<view>
					<slider name="height" value="100" style="width: 500upx;" @change="sliderChange" min="50" max="200" show-value />
				</view>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">失踪日期</view>
				<picker name="date" mode="date" :value="releaseUrgenceForm.date" start="1910-09-01" end="2020-09-01" @change="LostDateChange">
					<view class="picker">
						{{releaseUrgenceForm.date}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">失踪时地址</view>
				<input name="place" placeholder="请输入失踪人失踪时的位置" v-model="releaseUrgenceForm.place"></input>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">特征描述</view>
				<textarea maxlength="-1" :disabled="modalName!=null" v-model="releaseUrgenceForm.babyDescription" placeholder="请输入失踪人特征描述"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">失踪经过</view>
				<textarea maxlength="-1" v-model="releaseUrgenceForm.missDescription" placeholder="请输入具体的失踪经过"></textarea>
			</view>
			<view class="cu-form-group">
				<view class="title">与失踪人关系</view>
				<input placeholder="请输入联系人与失踪人的关系" v-model="releaseUrgenceForm.contactRel"></input>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">其他说明</view>
				<textarea maxlength="-1" v-model="releaseUrgenceForm.otherExplain" placeholder="请输入其他的说明"></textarea>
			</view>
			<view class="cu-bar bg-white margin-top">
					<view class="action">
						图片上传
					</view>
					<view class="action">
						{{imgList.length}}/1
					</view>
			</view>
			<view class="cu-form-group">
				<view class="grid col-4 grid-square flex-sub">
					<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
						<image :src="imgList[index]" mode="aspectFill"></image>
						<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
							<text class='cuIcon-close'></text>
						</view>
					</view>
					<view class="solids" @tap="ChooseImage" v-if="imgList.length<1">
						<text class='cuIcon-cameraadd'></text>
					</view>
				</view>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<button class="cu-btn bg-blue margin-tb-sm lg" form-type="submit" style="width:300upx;margin: 0 auto;">提交</button>
				<button class="cu-btn bg-blue margin-tb-sm lg" form-type="reset" style="width:300upx;margin: 0 auto;">清空数据</button>
			</view>
		</form>
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
	</view>
</template>

<script>
	var graceChecker = require("../../common/graceChecker.js");
	export default {
		onReady: function() {
			let userId=this.$store.state.userId
			this.getUser(userId)
		},
		data() {
			return {
				imgList:[],
				releaseUrgenceForm: {
					id: '',
					name: '',
					sex: '',
					height:50,
					birthday: '2018-12-25',
					date: '2019-06-25', //失踪时间
					place: '',
					babyDescription: '', //特征描述
					missDescription: '', //失踪经过
					otherExplain: '', //其他说明
					contactRel: '', //联系人与失踪人关系
					user: {}
				},
				items: [
					{
						name: 'man',
						value: '男'
					}, 
					{
						name: 'woman',
						value: '女'
					}
				],
				modalName: null,
				modalContent:null,
				user:{},
				lostbabyid:-1
			}
		},
		methods: {
			hideModal(e) {
				this.modalName = null
			},
			radioChange: function(e) {
				this.releaseUrgenceForm.sex = e.detail.value;
			},
			BirthDateChange(e) {
				this.releaseUrgenceForm.birthday = e.detail.value
			},
			sliderChange(e) {
				console.log('value 发生变化：' + e.detail.value);
				this.releaseUrgenceForm.height=e.detail.value
			},
			LostDateChange(e) {
				this.releaseUrgenceForm.date = e.detail.value
			},
			ChooseImage() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						if (this.imgList.length != 0) {
							this.imgList = this.imgList.concat(res.tempFilePaths)
						} else {
							this.imgList = res.tempFilePaths
						}
					}
				});
			},
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			DelImg(e) {
				uni.showModal({
					title: '寻子者',
					content: '确定要删除这张照片吗？',
					cancelText: '再看看',
					confirmText: '再见',
					success: res => {
						if (res.confirm) {
							this.imgList.splice(e.currentTarget.dataset.index, 1)
						}
					}
				})
			},
			formSubmit: function(e) {
				//将下列代码加入到对应的检查位置
				//定义表单规则
				let _this=this;
				var rule = [{
						name: "name",
						checkType: "string",
						checkRule: "2,15",
						errorMsg: "姓名应为2-15个字符"
					},
					{
						name: "sex",
						checkType: "in",
						checkRule: "男,女",
						errorMsg: "请选择性别"
					},
					{
						name: "birthday",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "请选择出生日期"
					},
					{
						name: "date",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "请选择失踪日期"
					},
					{
						name: "place",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "请填写失踪地点"
					}
					//{name:"img", checkType : "notnull", checkRule:"",  errorMsg:"请上传照片"}
				];
				//进行表单检查
				if(this.imgList.length<=0){
					this.modalName='Modal';
					this.modalContent='请上传照片';
					return;
				}
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
				}
				console.log(this.releaseUrgenceForm)
				this.$api.post(this.URLS.lostBabyInsertUrl+"?urgent=true",this.releaseUrgenceForm).then(data => {
					console.log(data)
					_this.lostbabyid = data.data.data.id
					//上传图片
					if (_this.imgList != null && _this.imgList.length > 0) {
						console.log('开始上传图片...');
						uni.uploadFile({
							url: this.URLS.uploadPictureUrl + "?action=AS_LOST_PICS", 
							filePath: _this.imgList[0],
							name: 'file',
							formData: {
								'id': _this.lostbabyid
							},
							header: {
								'Authorization': 'Bearer '+ this.$store.state.token
							},
							success: (uploadFileRes) => {
								console.log(uploadFileRes);
								_this.modalName='Modal';
								_this.modalContent='发布成功！';
							},
							fail: (uploadFileRes) => {
								console.log(uploadFileRes);
								_this.modalName='Modal';
								_this.modalContent='图片上传失败！';
								return;
							}
						});
					}
				}).catch(error => {
					console.log(error)
					_this.modalName='Modal';
					_this.modalContent='发布失败！';
				})
			},
			formReset: function(e) {
				console.log("清空数据")
				this.chosen = ''
			},
			getUser(userId) {
				let url = this.URLS.userFindByIdUrl + '?id=' + userId;
				let _this = this
				this.$api.post(url).then(data => {
					_this.releaseUrgenceForm.user = data.data.data
					console.log(_this.releaseUrgenceForm.user)
				}).catch(error => {
					console.log(error)
				})
			}
		}
	}
	
</script>

<style>
</style>
