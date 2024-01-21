<template>
  <div id="lottery">
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="lottery-unit lottery-unit-0"><img v-if="loadView" class="lottery-img" :src="imageList[0].image">
          <div class="mask"></div>
        </td>
        <td class="lottery-unit lottery-unit-1"><img v-if="loadView" class="lottery-img" :src="imageList[1].image">
          <div class="mask"></div>
        </td>
        <td class="lottery-unit lottery-unit-2"><img v-if="loadView" class="lottery-img" :src="imageList[2].image">
          <div class="mask"></div>
        </td>
      </tr>
      <tr>
        <td class="lottery-unit lottery-unit-7"><img v-if="loadView" class="lottery-img" :src="imageList[7].image">
          <div class="mask"></div>
        </td>
        <td><a @click="startLottery"></a></td>
        <td class="lottery-unit lottery-unit-3"><img v-if="loadView" class="lottery-img" :src="imageList[3].image">
          <div class="mask"></div>
        </td>
      </tr>
      <tr>
        <td class="lottery-unit lottery-unit-6"><img v-if="loadView" class="lottery-img" :src="imageList[6].image">
          <div class="mask"></div>
        </td>
        <td class="lottery-unit lottery-unit-5"><img v-if="loadView" class="lottery-img" :src="imageList[5].image">
          <div class="mask"></div>
        </td>
        <td class="lottery-unit lottery-unit-4"><img v-if="loadView" class="lottery-img" :src="imageList[4].image">
          <div class="mask"></div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import $ from 'jquery';
import {codeDetail, lotteryApi} from "@/api/lottery/play";

export default {
  name: 'LotteryGrid',
  props:{
    invalidCode: String,
  },
  mounted() {
    this.lottery.init('lottery');
    const that = this;
    console.log('子页面 mounted code":',this.invalidCode)
    codeDetail(this.newInvalidCode(this.invalidCode)).then(response => {
      console.log("response:", response)
      if (response.data) {
        that.imageList = response.data;
        that.loadView = true;
        that.$emit('lottery-count', response.lotteryCount);
      }
    })

  },
  data() {
    return {
      loadView: false,
      click: false,
      playIndex: -1,
      imageList: [],
      lottery: {
        index: -1,    //当前转动到哪个位置，起点位置
        count: 0,    //总共有多少个位置
        timer: 0,    //setTimeout的ID，用clearTimeout清除
        speed: 20,    //初始转动速度
        times: 0,    //转动次数
        cycle: 80,    //转动基本次数：即至少需要转动多少次再进入抽奖环节
        prize: -1,    //中奖位置
        init: function (id) {
          if ($("#" + id).find(".lottery-unit").length > 0) {
            const $lottery = $("#" + id);
            const $units = $lottery.find(".lottery-unit");
            this.obj = $lottery;
            this.count = $units.length;
            $lottery.find(".lottery-unit-" + this.index).addClass("active");
          }
          ;
        },
        roll: function () {
          var index = this.index;
          var count = this.count;
          var lottery = this.obj;
          $(lottery).find(".lottery-unit-" + index).removeClass("active");
          index += 1;
          if (index > count - 1) {
            index = 0;
          }
          ;
          $(lottery).find(".lottery-unit-" + index).addClass("active");
          this.index = index;
          return false;
        },
        stop: function (index) {
          this.prize = index;
          return false;
        }
      }
    }
  },
  methods: {
    newInvalidCode(code){
      let newInvalidCode = this.invalidCode;
      if(this.invalidCode.indexOf("?code=") !== -1){
        newInvalidCode = this.invalidCode.replace("?code=","");
      }
      return newInvalidCode;
    },
    updateCode(updateCode){
      console.log("updateCode",updateCode);
      if(updateCode != ''){
        this.invalidCode = updateCode;
      }
    },
    lotteryDone() {
      const that = this;
      console.log('抽奖完  中奖的是:', this.imageList[this.playIndex])

      console.log("lottery:", that.lottery)

      that.lottery.index = -1;    //当前转动到哪个位置，起点位置

      this.$emit('lottery-done', this.imageList[this.playIndex]);
    },
    async roll() {
      const lottery = this.lottery;
      const roll = this.roll;
      lottery.times += 1;
      lottery.roll();//转动过程调用的是lottery的roll方法，这里是第一次调用初始化
      if (lottery.times > lottery.cycle + 10 && lottery.prize == lottery.index) {
        clearTimeout(lottery.timer);
        lottery.prize = -1;
        lottery.times = 0;
        this.click = false;

        this.lotteryDone();
      } else {

        if (lottery.times < lottery.cycle) {
          lottery.speed -= 10;
        } else if (lottery.times == lottery.cycle) {
          lotteryApi(this.newInvalidCode(this.invalidCode)).then(response => {
            if (response.index !=null && response.index !=undefined) {
                this.playIndex = response.index;
                lottery.prize = response.index;
            }
          })
        } else {
          if (lottery.times > lottery.cycle + 10
            // &&
            // ((lottery.prize == 0 && lottery.index == 7) || lottery.prize == lottery.index + 1)
          )
          {
            lottery.speed += 110;
          } else {
            lottery.speed += 20;
          }
        }
        if (lottery.speed < 40) {
          lottery.speed = 40;
        }
        ;
        //console.log(lottery.times+'^^^^^^'+lottery.speed+'^^^^^^^'+lottery.prize);
        lottery.timer = setTimeout(roll, lottery.speed);//循环调用
      }
      return false;
    },
    startLottery() {
      if (this.click) {//click控制一次抽奖过程中不能重复点击抽奖按钮，后面的点击不响应
        return false;
      } else {
        const that = this;

        const $lottery = $("#lottery");
        $lottery.find(".lottery-unit-" + that.playIndex).removeClass("active");

        that.playIndex = -1;

        this.lottery.speed = 100;
        this.roll();    //转圈过程不响应click事件，会将click置为false
        this.click = true; //一次抽奖完成后，设置click为true，可继续抽奖
        return false;
      }
    },
  }
};
</script>

<style scoped>
#lottery {
  width: 570px;
  height: 510px;
  margin: 0px auto;
  border: 4px solid #ba1809;
}

#lottery table {
  background-color: yellow;
}

#lottery table td {
  position: relative;
  width: 190px;
  height: 170px;
  text-align: center;
  color: #333;
  font-index: -999;
}

#lottery table td img {
  display: block;
  width: 190px;
  height: 170px;
}

#lottery table td a {
  width: 190px;
  height: 170px;
  display: block;
  text-decoration: none;
  background: url(../../assets/lottery/lottery1.jpg) no-repeat top center;
}

#lottery table td a:hover {
  background-image: url(../../assets/lottery/lottery2.jpg);
}

#lottery table td.active .mask {
  display: block;
}

.mask {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: 0;
  background-color: rgba(252, 211, 4, 0.5);
  display: none;
}

.lottery-img {
  display: block;
  width: 190px;
  height: 170px;
}
</style>
