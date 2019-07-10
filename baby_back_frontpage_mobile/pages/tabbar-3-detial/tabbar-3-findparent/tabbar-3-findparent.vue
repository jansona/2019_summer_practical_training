<template>
	<view>
		<form @submit="formSubmit" @reset="formReset">
			<view class="cu-form-group">
				<view class="title">姓名</view>
				<input placeholder="请输入寻亲者姓名" name="name" v-model="findParentForm.name"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width:100upx;">性别</view>
				<radio-group class="radio-group" name="sex" bindchange="radioChange">
					<label class="radio" v-for="(item,index) in items" :key=index>
						<radio :value="item.value" :checked="item.checked" color="#FFCC33" style="transform:scale(0.7)" />{{item.value}}
					</label>
				</radio-group>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">出生日期</view>
				<picker mode="date" name="birthday" :value="findParentForm.birthday" start="1910-09-01" end="2020-09-01" @change="BirthDateChange">
					<view class="picker">
						{{findParentForm.birthday}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">籍贯</view>
				<input placeholder="请输入寻亲者籍贯" v-model="findParentForm.nativePlace"></input>
			</view>
			<view class="cu-form-group">
				<view class="uni-title">身高</view>
				<view>
					<slider name="height" value="100" style="width: 500upx;" @change="sliderChange" min="50" max="200" show-value />
				</view>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">失踪日期</view>
				<picker name="date" mode="date" :value="findParentForm.date" start="1910-09-01" end="2020-09-01" @change="LostDateChange">
					<view class="picker">
						{{findParentForm.date}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">失踪时地址</view>
				<input name="place" placeholder="请输入寻亲者失踪时的地址" v-model="findParentForm.place"></input>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">特征描述</view>
				<textarea maxlength="-1" v-model="findParentForm.babyDescription" placeholder="请输入寻亲者特征描述"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">失踪经过</view>
				<textarea maxlength="-1" v-model="findParentForm.missDescription" placeholder="请输入具体的失踪经过"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">其他信息</view>
				<textarea maxlength="-1" v-model="findParentForm.otherDescription" placeholder="请输入其他的信息"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">其他说明</view>
				<textarea maxlength="-1" v-model="findParentForm.otherExplain" placeholder="请输入其他的说明"></textarea>
			</view>
			<view class="uni-title" style="padding-left: 35upx;color: #888888;">联系人信息</view>
			<view class="cu-form-group">
				<view class="title">姓名</view>
				<input placeholder="请输入联系人姓名" v-model="findParentForm.contactName"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">与寻亲者关系</view>
				<input placeholder="请输入联系人与寻亲者的关系" v-model="findParentForm.contactRel"></input>
			</view>
			<!-- <view class="cu-form-group">
				<view class="title" style="width:450upx;">是否为寻亲者本人</view>
				<radio-group class="radio-group" @change="radio1Change">
					<label class="radio" v-for="(item1,index1) in items1" :key=index1>
						<radio :value="item1.value" :checked="item1.checked" color="#FFCC33" style="transform:scale(0.7)"/>{{item1.value}}
					</label>
				</radio-group>
			</view>
			<view class="cu-form-group" v-if="isOrNo==='否'">
				<view class="title">填表人与寻亲者关系</view>
					<input placeholder="请输入填表人与寻亲者关系" name="writerRelation"></input>
			</view> -->
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">地址</view>
				<input placeholder="请输入详细地址" v-model="findParentForm.contactAddress"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">邮箱</view>
				<input placeholder="请输入邮箱" v-model="findParentForm.contactEmail"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">手机号码</view>
				<input placeholder="请输入手机号码" type="number" v-model="findParentForm.contactPhone"></input>
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
				<input placeholder="请输入其他联系方式" v-model="findParentForm.otherContactMethod"></input>
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
	var  graceChecker = require("../../../common/graceChecker.js");
	export default {
		onReady: function() {
			let userId=this.$store.state.userId
			this.getUser(userId)
		},
		data() {
			return {
				matchbabyid:-1,
				imgList:[],
				isSend:false,
				findParentForm: {
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
				items: [{
					name: 'man',
					value: '男'
				}, {
					name: 'woman',
					value: '女'
				}],
				modalName: null,
				modalContent:null,
				user:{
					id:this.$store.state.userId,
				}
			}
		},
		onLoad() {

		},
		methods: {
			hideModal(e) {
				this.modalName = null
			},
			radioChange(e) {
				this.findParentForm.sex = e.detail.value
			},
			sliderChange(e) {
				console.log('value 发生变化：' + e.detail.value);
				this.findParentForm.height=e.detail.value
			},
			// radio1Change(e){
			// 	this.isOrNo=e.detail.value
			// },
			ChooseImage() {
				uni.chooseImage({
					count: 4, //默认9
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
					title: '寻亲者',
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
			BirthDateChange(e) {
				this.findParentForm.birthday = e.detail.value
			},
			LostDateChange(e) {
				this.findParentForm.date = e.detail.value
			},
			formSubmit: function (e) {
				if(this.isSend==true){
					this.modalName='Modal';
					this.modalContent='已经发布过了~';
					return;
				}
				//将下列代码加入到对应的检查位置
				//定义表单规则
				let _this=this;
				var rule = [
					{name:"name", checkType : "string", checkRule:"2,15",  errorMsg:"姓名应为2-15个字符"},
					{name:"sex", checkType : "in", checkRule:"男,女",  errorMsg:"请选择性别"},
					{name:"birthday", checkType : "notnull", checkRule:"",  errorMsg:"请选择出生日期"},
					{name:"height", checkType : "notnull", checkRule:"",  errorMsg:"请选择身高"},
					{name:"date", checkType : "notnull", checkRule:"",  errorMsg:"请选择失踪日期"},
					{name:"place", checkType : "notnull", checkRule:"",  errorMsg:"请填写失踪地点"}
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
				if(checkRes){
					//uni.showToast({title:"验证通过!", icon:"none"});
				}else{
					//uni.showToast({ title: graceChecker.error, icon: "none" });
					_this.modalName='Modal';
					_this.modalContent=graceChecker.error;
					return;
				}
				//上传表单信息
				this.$api.post(this.URLS.matchBabyInsertUrl,this.findParentForm).then(data => {
					console.log(data)
					_this.matchbabyid=data.data.data.id;
					console.log(_this.matchbabyid);
					//上传图片
					if (_this.imgList != null && _this.imgList.length > 0) {
						console.log('开始上传图片...');
						uni.uploadFile({
							url: this.URLS.uploadPictureUrl + "?action=AS_MATCH_PICS", 
							filePath: _this.imgList[0],
							name: 'file',
							formData: {
								'id': _this.matchbabyid
							},
							header: {
								'Authorization': 'Bearer '+ this.$store.state.token
							},
							success: (uploadFileRes) => {
								console.log(uploadFileRes);
								_this.isSend=true;
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
			formReset: function (e) {
				console.log("清空数据")
				this.chosen = ''
			},
			getUser(userId) {
				let url = this.URLS.userFindByIdUrl + '?id=' + userId;
				let _this = this
				this.$api.post(url).then(data => {
					_this.user = data.data.data
					_this.findParentForm.user = _this.user
				}).catch(error => {
					console.log(error)
				})
			}
		}
	}
</script>

<style>

</style>
