<template>
	<view>
		<form @submit="formSubmit" @reset="formReset">
			<view class="cu-form-group">
				<view class="title">姓名</view>
				<input placeholder="请输入失踪人姓名" name="name" v-model="findChildForm.name"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width:100upx;">性别</view>
				<radio-group class="radio-group" name="sex" @change="radioChange">
					<label class="radio" v-for="(item,index) in items" :key=index>
						<radio :value="item.value" :checked="item.checked" color="#FFCC33" style="transform:scale(0.7)" />{{item.value}}
					</label>
				</radio-group>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">出生日期</view>
				<picker mode="date" :value="findChildForm.birthday" start="1910-09-01" end="2020-09-01" name="birthday" @change="BirthDateChange">
					<view class="picker">
						{{findChildForm.birthday}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">籍贯</view>
				<input placeholder="请输入失踪人籍贯" v-model="findChildForm.nativePlace"></input>
			</view>
			<view class="cu-form-group">
				<view class="uni-title">身高</view>
				<view>
					<slider value="100" style="width: 500upx;" @change="sliderChange" min="50" max="200" show-value />
				</view>
			</view>
			
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">失踪日期</view>
				<picker name="date" mode="date" :value="findChildForm.date" start="1910-09-01" end="2020-09-01" @change="LostDateChange">
					<view class="picker">
						{{findChildForm.date}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">失踪时地址</view>
				<input name="place" placeholder="请输入失踪人失踪时的位置" v-model="findChildForm.place"></input>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">特征描述</view>
				<textarea maxlength="-1" :disabled="modalName!=null" v-model="findChildForm.babyDescription" placeholder="请输入失踪人特征描述"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">失踪经过</view>
				<textarea maxlength="-1" :disabled="modalName!=null" v-model="findChildForm.missDescription" placeholder="请输入具体的失踪经过"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">其他信息</view>
				<textarea maxlength="-1" :disabled="modalName!=null" v-model="findChildForm.otherDescription" placeholder="请输入其他的信息"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">其他说明</view>
				<textarea maxlength="-1" :disabled="modalName!=null" v-model="findChildForm.otherExplain" placeholder="请输入其他的说明"></textarea>
			</view>
			<view class="uni-title" style="padding-left: 35upx;color: #888888;">联系人信息</view>
			<view class="cu-form-group">
				<view class="title">姓名</view>
				<input placeholder="请输入联系人姓名" v-model="findChildForm.contactName"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">与失踪人关系</view>
				<input placeholder="请输入联系人与失踪人的关系" v-model="findChildForm.contactRel"></input>
			</view>
			<!-- <view class="cu-form-group">
				<view class="title">填表人与失踪人关系</view>
				<input placeholder="请输入填表人与失踪人的关系" v-model="findChildForm.writerRelation"></input>
			</view> -->
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">地址</view>
				<input placeholder="请输入详细地址" v-model="findChildForm.contactAddress"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">邮箱</view>
				<input placeholder="请输入邮箱" v-model="findChildForm.contactEmail"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">手机号码</view>
				<input placeholder="请输入手机号码" type="number" v-model="findChildForm.contactPhone"></input>
				<view class="cu-capsule radius">
					<view class='cu-tag bg-blue '>
						+86
					</view>
					<view class="cu-tag line-blue">
						中国大陆
					</view>
				</view>
			</view>
			<view class="cu-form-group">
				<view class="title">其他联系方式</view>
				<input placeholder="请输入其他联系方式" v-model="findChildForm.otherContactMethod"></input>
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
			<!--模态框提示-->
			<!-- <view class="cu-modal" :class="modalName=='Modal'?'show':''">
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
			</view> -->
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
					<view class="cu-bar bg-white justify-end">
						<view class="action">
							<button class="cu-btn line-blue text-green" @tap="hideModal">取消</button>
							<button class="cu-btn bg-blue margin-left" @tap="sure">确定</button>
			
						</view>
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
			let userId=this.$store.state.userId
			this.getUser(userId)
		},
		data() {
			return {
				lostbabyid:-1,
				imgList:[],
				isSend:false,
				findChildForm: {
					id: '',
					name: '',
					sex: '',
					birthday: '2018-12-25',
					nativePlace: '', //籍贯
					height: 50,
					date: '2019-06-25', //失踪时间
					place: '',
					babyDescription: '', //特征描述
					missDescription: '', //失踪经过
					otherExplain: '', //其他说明
					otherDescription: '', //其他信息描述
					//联系人信息
					contactName: '', //联系人姓名
					contactRel: '', //联系人与失踪人关系
					//writerRelation:'',
					contactAddress: '',
					contactEmail: '',
					contactPhone: '',
					otherContactMethod: '',
					
					user: {
						id: this.$store.state.userId,
					}
				},
				modalName: null,
				modalContent:null,
				items: [{
					name: 'man',
					value: '男'
				}, {
					name: 'woman',
					value: '女'
				}],
				user:{}
			}
		},
		methods: {
			hideModal(e) {
				this.modalName = null
			},
			sure(){
				if(this.modalContent=='请上传照片'||this.modalContent=='发布失败'||this.modalContent=='图片上传失败'||this.modalContent=='已经发布过了'){
					this.modalName=null;
				}else if(this.modalContent=='发布成功'){
					uni.redirectTo({
						url:'../../index/index'
					})
				}
			},
			radioChange: function(e) {
				this.findChildForm.sex = e.detail.value;
			},
			sliderChange(e) {
				console.log('value 发生变化：' + e.detail.value);
				this.findChildForm.height=e.detail.value
			},
			BirthDateChange(e) {
				this.findChildForm.birthday = e.detail.value
			},
			LostDateChange(e) {
				this.findChildForm.date = e.detail.value
			},
			ChooseImage() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						if (this.imgList.length != 0) {
							this.imgList = this.imgList.concat(res.tempFilePaths);
						} else {
							this.imgList = res.tempFilePaths
						}
						console.log(this.imgList)
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
				if(this.isSend==true){
					this.modalName='Modal';
					this.modalContent='已经发布过了';
					return;
				}
				//将下列代码加入到对应的检查位置
				//定义表单规则
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
					// {
					// 	name: "height",
					// 	checkType: "notnull",
					// 	checkRule: "",
					// 	errorMsg: "请选择身高"
					// },
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
				let _this=this;
				if (checkRes) {
					// uni.showToast({
					// 	title: "验证通过!",
					// 	icon: "none"
					// });
				} else {
					// uni.showToast({
					// 	title: graceChecker.error,
					// 	icon: "none"
					// });
					_this.modalName='Modal';
					_this.modalContent=graceChecker.error;
					return;
				}
				
				//上传表单信息
				//console.log(this.findChildForm)
				this.$api.post(this.URLS.lostBabyInsertUrl,this.findChildForm).then(data => {
					//console.log(data)
					_this.lostbabyid=data.data.data.id;
					console.log(_this.lostbabyid);
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
								_this.modalContent='发布成功';
								_this.isSend=true;
							},
							fail: (uploadFileRes) => {
								console.log(uploadFileRes);
								_this.modalName='Modal';
								_this.modalContent='图片上传失败';
								return;
							}
						});
					}
				}).catch(error => {
					console.log(error)
					_this.modalName='Modal';
					_this.modalContent='发布失败';
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
					_this.user = data.data.data
					_this.findChildForm.user = _this.user
					console.log(_this.user)
				}).catch(error => {
					console.log(error)
				})
			}
		}

	}
</script>

<style>

</style>
