<template>
	<!--选择的城市jason数据存放在pickertext中-->
    <view class="mpvue-picker">
        <view class="uni-padding-wrap uni-common-mt">
            <view class="cu-btn round arrow" @click="showMulLinkageThreePicker">设置活动范围</view>
        </view>
        <mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValueDefault" @onConfirm="onConfirm"></mpvue-city-picker>
    </view>
</template>

<script>
	import mpvuePicker from '@/components/mpvue-picker/mpvuePicker.vue';
	import mpvueCityPicker from '@/components/mpvue-citypicker/mpvueCityPicker.vue'
    import cityData from '@/common/city.data.js';
	export default {
        components: {
            mpvuePicker,
            mpvueCityPicker
        },
        data() {
            return {
                cityPickerValueDefault: [0, 0, 1],
                themeColor: '#007AFF',
                pickerText: '',
				detail:''
            };
        },
        methods: {
            // 三级联动选择
            showMulLinkageThreePicker() {
                this.$refs.mpvueCityPicker.show()
            },
            onConfirm(e) {
                this.pickerText = JSON.stringify(e);
				this.detail=e.label;
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
        },
        // () {
        //     if (this.$refs.mpvuePicker.showPicker) {
        //         this.$refs.mpvuePicker.pickerCancel()
        //     }
        //     if (this.$refs.mpvueCityPicker.showPicker) {
        //         this.$refs.mpvueCityPicker.pickerCancel()
        //     }
        // }
    };

</script>

<style>
</style>
