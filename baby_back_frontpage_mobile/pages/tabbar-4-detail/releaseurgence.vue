<template>
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
			<textarea maxlength="-1" v-model="findChildForm.missDescription" placeholder="请输入具体的失踪经过"></textarea>
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
					{{releaseUrgenceForm.imgList.length}}/1
				</view>
			</view>
			<view class="cu-form-group">
				<view class="grid col-4 grid-square flex-sub">
					<view class="bg-img" v-for="(item,index) in releaseUrgenceForm.imgList" :key="index" @tap="ViewImage" :data-url="releaseUrgenceForm.imgList[index]">
						<image :src="releaseUrgenceForm.imgList[index]" mode="aspectFill"></image>
						<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
							<text class='cuIcon-close'></text>
						</view>
					</view>
					<view class="solids" @tap="ChooseImage" v-if="releaseUrgenceForm.imgList.length<1">
						<text class='cuIcon-cameraadd'></text>
					</view>
				</view>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<button class="cu-btn bg-blue margin-tb-sm lg" form-type="submit" style="width:300upx;margin: 0 auto;">提交</button>
				<button class="cu-btn bg-blue margin-tb-sm lg" form-type="reset" style="width:300upx;margin: 0 auto;">清空数据</button>
			</view>
		</form>
</template>

<script>
	var graceChecker = require("../../common/graceChecker.js");
	export default {
		data() {
			return {
				releaseUrgenceForm: {
					id: '',
					name: '',
					sex: '',
					birthday: '2018-12-25',
					date: '2019-6-25', //失踪时间
					place: '',
					babyDescription: '', //特征描述
					missDescription: '', //失踪经过
					otherExplain: '', //其他说明
					contactRel: '', //联系人与失踪人关系
					imgList: []
				},
				items: [{
					name: 'man',
					value: '男'
				}, {
					name: 'woman',
					value: '女'
				}]
			}
		},
		onLoad() {
	
		},
		methods: {
			radioChange: function(e) {
				this.findChildForm.sex = e.detail.value;
			},
			BirthDateChange(e) {
				this.findChildForm.birthday = e.detail.value
			},
			LostDateChange(e) {
				this.findChildForm.date = e.detail.value
			},
			ChooseImage() {
				uni.chooseImage({
					count: 4, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						if (this.releaseUrgenceForm.imgList.length != 0) {
							this.releaseUrgenceForm.imgList = this.releaseUrgenceForm.imgList.concat(res.tempFilePaths)
						} else {
							this.releaseUrgenceForm.imgList = res.tempFilePaths
						}
					}
				});
			},
			ViewImage(e) {
				uni.previewImage({
					urls: this.releaseUrgenceForm.imgList,
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
							this.releaseUrgenceForm.imgList.splice(e.currentTarget.dataset.index, 1)
						}
					}
				})
			},
			formSubmit: function(e) {
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
	
				if (this.findChildForm.imgList != null && this.findChildForm.imgList.length > 0) {
					uni.uploadFile({
						url: this.URLS.uploadPictureUrl + "?action=AS_LOST_PICS", //仅为示例，非真实的接口地址
						filePath: this.findChildForm.imgList[0],
						name: 'file',
						formData: {
							'id': '6'
						},
						success: (uploadFileRes) => {
							console.log(uploadFileRes);
						}
					});
				}
			},
			formReset: function(e) {
				console.log("清空数据")
				this.chosen = ''
			}
		}
	}
</script>

<style>
</style>
