<template>
  <div>
    <div id="roll" style="display:block">
      <!-- <p class="time">3.10-3.25</p> -->
      <!--星星-->
      <div class="stars-box">
        <span class="stars"></span>
        <span class="stars"></span>
        <span class="stars"></span>
        <span class="stars"></span>
        <span class="stars"></span>
        <span class="stars"></span>
        <span class="stars"></span>
      </div>
      <!--主体-->
      <div class="main">
        <p class="rule" style="display:block" @click="showMaskRule"></p>
        <p class="my" style="display:block" @click="showMaskMy"></p>
        <!--游戏区域-->
        <div class="boxx">
          <span class="coin"></span>
          <!--          <h2>已有 <span>99</span> 人参与</h2>-->
          <ul class="light clearfix">
            <li class="fl">
              <p></p>
              <p class="blin"></p>
              <p></p>
              <p class="blin"></p>
            </li>
            <li class="fr">
              <p class="blin"></p>
              <p></p>
              <p class="blin"></p>
              <p></p>
            </li>
          </ul>

          <div class="play clearfix flex-fix-play">
            <LotteryGrid />
          </div>

        </div>
      </div>
    </div>

    <!--游戏规则弹窗-->
    <div id="mask-rule" ref="mask-rule" :class="activeClass? 'active':''  ">
      <div class="box-rule">
        <span class="star"></span>
        <h2>活动说明</h2>
        <span id="close-rule" @click="hiddenMaskRule"></span>
        <div class="con">
          <div class="text">
            活动规则： <br>
            1、凭手气赢奖品！百分百中奖！ <br>
            2、中奖后联系下方客服发送邀请码进行兑奖。 <br>
            3、填写的姓名、电话和联系地址必须真实，否则奖品没办法寄送。 <br>
            <p>
              <br>
            </p>                       <br>
            <div id="times">
              <div>
                剩余抽奖次数：<span id="curtimes5">999999</span><br>
              </div>
            </div>
            <hr class="hr0">
            活动时间：<br>2024-08-08 08:00至2088-08-08 08:00 <br><br>
          </div>
        </div>
      </div>
    </div>

    <!--中奖记录弹窗-->
    <div id="mask-my" ref="mask-my" :class="activeMyClass? 'active':''  ">
      <div class="box-my">
        <span class="star"></span>
        <h2>中奖记录</h2>
        <span id="close-my" @click="hiddenMaskMy"></span>
        <div class="con">
          <div class="text">
            <!--            <?php
                        $l = D('lottory',array('ip'=>sys_ip(),'order'=>'id desc','limit1'=>0,'limit2'=>20));
                        if(count($l)>0){?>
                        <div class="boxcontent boxwhite" style="margin:0">
                          <div class="box">
                            <?php

                          foreach($l as $k=>$v){?>
                            <div class="Detail">

                              <b style='color:#000'>奖品：<?php echo $v['award_name']?></b> <span style='color:gray; white-space:nowrap'>[<?php echo $v['adddate']?>]</span><br> <hr>
                            </div>
                            <?php }?>
                          </div>
                        </div>
                        <?php }?>-->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import {codeDetail} from "@/api/lottery/play";
import LotteryGrid from "@/components/LotteryGrid"

export default {
  name: "App",
  components:{
    LotteryGrid
  },
  data() {
    return {
      activeClass: false,
      activeMyClass: false,

    }
  },
  metaInfo() {
    return {
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  },
  mounted() {
    /*codeDetail("b12b252d794a47889b193531c359b808").then(response => {
      console.log("response:", response)
    })*/

  },
  methods: {
    showMaskRule() {
      this.activeClass = true
    },
    showMaskMy() {
      this.activeMyClass = true
    },
    hiddenMaskRule() {
      this.activeClass = false
    },
    hiddenMaskMy() {
      this.activeMyClass = false
    },

  },
};
</script>
<style type="text/css">
html {
  overflow-y: scroll
}

#box {
  width: 100%;
  position: relative;
  margin: 10px auto;
}

a {
  text-decoration: none
}

p {
  margin: 0px;
  padding: 0px;
}

ul, li {
  padding: 0;
  margin: 0;
  list-style: none;
}

.Detail ul li {
  display: inline-block;
  background-color: rgba(0, 0, 0, 0.06);
  border-radius: 10px;
  width: 27%;
  padding: 2%;
  text-align: center;
  margin: 0 0 2% 1%;
}

#roll {
  overflow: hidden;
  position: relative;
  width: 100%;
  height: 16.01333333rem
}

#mask {
  opacity: 1;
}

.scroll ul li {
  position: relative;
  height: 26px;
  top: 0;
  text-align: center;
  font-size: 0.29333333rem;
  line-height: 0.53333333rem;
  color: #fff;
  text-shadow: none;
  -o-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -webkit-transition: all 0.5s ease-in-out;
  transition: all 0.5s ease-in-out;
}

.active {
  display: block !important;
}

#lottery{width:570px;height:510px;margin:0px auto;border:4px solid #ba1809;}
#lottery table{background-color:yellow;}
#lottery table td{position:relative;width:190px;height:170px;text-align:center;color:#333;font-index:-999}
#lottery table td img{display:block;width:190px;height:170px;}
#lottery table td a{width:190px;height:170px;display:block;text-decoration:none;background:url(assets/lottery/lottery1.jpg) no-repeat top center;}
#lottery table td a:hover{background-image:url(assets/lottery/lottery2.jpg);}
#lottery table td.active .mask{display:block;}
.mask{
  width:100%;
  height:100%;
  position:absolute;
  left:0;
  top:0;
  background-color: rgba(252,211,4,0.5);
  display:none;
}

.flex-fix-play {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
