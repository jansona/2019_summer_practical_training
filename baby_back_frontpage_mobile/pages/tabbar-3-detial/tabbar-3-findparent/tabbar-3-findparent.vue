<template>
	<view>
		<form method="post">
			<view class="cu-form-group">
				<view class="title">姓名</view>
				<input placeholder="请输入寻亲者姓名" v-model="findParentForm.name"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width:100upx;">性别</view>
				<radio-group class="radio-group" bindchange="radioChange">
					<label class="radio" v-for="(item,index) in items" :key=index>
						<radio :value="item.value" :checked="item.checked" color="#FFCC33" style="transform:scale(0.7)"/>{{item.value}}
					</label>
				</radio-group>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">出生日期</view>
				<picker mode="date" :value="birthDate" start="1910-09-01" end="2020-09-01" @change="BirthDateChange">
					<view class="picker">
						{{birthDate}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">籍贯</view>
				<input placeholder="请输入寻亲者籍贯" name="birthPlace"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">现居地</view>
				<input placeholder="请输入寻亲者现居地" name="livePlace"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">身高</view>
				<input placeholder="请输入寻亲者失踪时身高" name="height"></input>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">失踪日期</view>
				<picker mode="date" :value="lostDate" start="1910-09-01" end="2020-09-01" @change="LostDateChange">
					<view class="picker">
						{{lostDate}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">失踪时地址</view>
				<input placeholder="请输入寻亲者失踪时的地址" name="height"></input>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">特征描述</view>
				<textarea maxlength="-1" :disabled="modalName!=null" @input="featureInput" placeholder="请输入寻亲者特征描述"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">失踪经过</view>
				<textarea maxlength="-1" :disabled="modalName!=null" @input="lostInput" placeholder="请输入具体的失踪经过"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">其他信息</view>
				<textarea maxlength="-1" :disabled="modalName!=null" @input="otherInfoInput" placeholder="请输入其他的信息"></textarea>
			</view>
			<view class="cu-form-group align-start">
				<view class="title">其他说明</view>
				<textarea maxlength="-1" :disabled="modalName!=null" @input="otherDescriptionInput" placeholder="请输入其他的说明"></textarea>
			</view>
			<view class="uni-title" style="padding-left: 35upx;color: #888888;">联系人信息</view>
			<view class="cu-form-group">
				<view class="title">姓名</view>
				<input placeholder="请输入联系人姓名" name="contactName"></input>
			</view>
			<view class="cu-form-group">
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
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<view class="title">地址</view>
				<input placeholder="请输入详细地址" name="address"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">邮箱</view>
				<input placeholder="请输入邮箱" name="email"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">手机号码</view>
				<input placeholder="请输入手机号码" type="number" name="phoneNumber"></input>
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
				<input placeholder="请输入其他联系方式" name="otherWay"></input>
			</view>
			<view class="cu-bar bg-white margin-top">
				<view class="action">
					图片上传
				</view>
				<view class="action">
					{{imgList.length}}/4
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
					<view class="solids" @tap="ChooseImage" v-if="imgList.length<4">
						<text class='cuIcon-cameraadd'></text>
					</view>
				</view>
			</view>
			<view class="cu-form-group" style="margin-top: 30upx;">
				<button class="cu-btn bg-blue margin-tb-sm lg" style="width:300upx;margin: 0 auto;" @click="submit">提交</button>
			</view>
		</form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				findParentForm: {
					id:'',
					name: '',
					sex:'',
					birthday:'2018-12-25',
					nativePlace:'', //籍贯
					height:'',
					date:'2019-6-25', //失踪时间
					place:'',
					babyDescription:'', //特征描述
					missDescription:'', //失踪经过
					otherExplain:'', //其他说明
					otherDescription:'', //其他信息描述
					//联系人信息👇
					contactName:'', //联系人姓名
					contactRel:'', //联系人与失踪人关系
					//writerRelation:'',
					contactAddress:'',
					contactEmail:'',
					contactPhone:'',
					otherContactMethod:'', 
					imgList: []
				},
				items:[{name:'man',value:'男'},{name:'woman',value:'女'}],
			}
		},
		onLoad() {

		},
		methods: {
			radioChange(e){
				this.gender=e.detail.value
			},
			radio1Change(e){
				this.isOrNo=e.detail.value
			},
			BirthDateChange(e) {
				this.birthDate = e.detail.value
			},
			LostDateChange(e) {
				this.lostDate = e.detail.value
			},
			feaInput(e) {
				this.feature = e.detail.value
			},
			lostInput(e) {
				this.lostProcess = e.detail.value
			},
			otherInfoInput(e){
				this.otherInfo = e.detail.value
			},
			otherDescriptionInput(e){
				this.otherDescription = e.detail.value
			},
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
			submit(e){
				
			}
		}
	}
</script>

<style>
	.content {
		text-align: center;
		height: 400upx;
		margin-top: 200upx;
	}
</style>
