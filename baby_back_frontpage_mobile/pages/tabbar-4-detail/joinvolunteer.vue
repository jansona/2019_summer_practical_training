<template>
	<view style="margin-top: 70upx;">
		<uni-card title="设置活动范围" thumbnail="../../static/img/tabbar/add.png" note="您只会收到活动范围内的紧急寻人信息" @click="showMulLinkageThreePicker">
			<text style="font-weight: 800;font-size: 35upx;">{{detail}}</text>
		</uni-card>
		<mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValueDefault" @onConfirm="onConfirm"></mpvue-city-picker>
		<view class="padding text-center" style="margin-top: 80upx;">
			<button class="cu-btn bg-blue margin-tb-sm lg" style="width:300upx;margin: 0 auto;" @click="Join">完成</button>
		</view>
	</view>
</template>
<script>
	import mpvuePicker from '@/components/mpvue-picker/mpvuePicker.vue';
	import mpvueCityPicker from '@/components/mpvue-citypicker/mpvueCityPicker.vue'
	import cityData from '@/common/city.data.js';
	import uniCard from '@/components/uni-card/uni-card.vue'
	export default {
		components: {
		    mpvuePicker,
		    mpvueCityPicker,
			uniCard
		},
		data() {
			return {
				cityPickerValueDefault: [0, 0, 1],
				themeColor: '#007AFF',
				pickerText: '',
				detail:''
			};
		},
		onLoad() {
		},
		methods: {
			changeSkin(){
				uni.navigateTo({
						url:"../tabbar/tabbar-5/skin-change/skin-change"
				});
			},
			showMulLinkageThreePicker() {
			    this.$refs.mpvueCityPicker.show()
			},
			onConfirm(e) {
			    this.pickerText = JSON.stringify(e);
				this.detail=e.label;
			},
			Join(e){
				
			}
		},
		onBackPress() {
		  if (this.$refs.mpvuePicker.showPicker) {
		    this.$refs.mpvuePicker.pickerCancel();
		    return true;
		  }
		  if (this.$refs.mpvueCityPicker.showPicker) {
		    this.$refs.mpvueCityPicker.pickerCancel();
		    return true;
		  }
		}
	}
</script>

<style>

</style>
