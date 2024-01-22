<template>
  <div style="font-size: 101.36px;">
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
        <div class="boxx" id="booxxx">
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
            <LotteryGrid ref="grid" @lottery-done="showMaskModel" @lottery-count="updateCount" :invalid-code="code"/>
          </div>

        </div>
      </div>
    </div>

    <!--未携带邀请码弹窗-->
    <div id="code-rule" ref="code-rule" :class="activeCodeClass? 'active':''  ">
      <div class="box-rule">
        <span class="star"></span>
        <h2>请输入邀请码</h2>
        <!--        <span id="close-rule" @click="hiddenCodeRule"></span>-->
        <div class="con">
          <div class="text">
            <el-input placeholder="请输入邀请码" v-model="code">
              <template slot="append">
                <el-button type="primary" round @click="clickCode">确认</el-button>
              </template>
            </el-input>
          </div>
          <!--          <div class="el-button-center">
                      <el-button type="primary" round>确认</el-button>
                    </div>-->
        </div>
        <div>
        </div>
      </div>
    </div>

    <!--游戏规则弹窗-->
    <div id="mask-rule" ref="mask-rule" :class="activeClass? 'active':''  ">
      <div class="box-rule">
        <span class="star"></span>
        <h2>活動說明</h2>
        <span id="close-rule" @click="hiddenMaskRule"></span>
        <div class="con">
          <div class="text">
            活動規則： <br>
            1、憑手氣贏獎品！百分百中獎！ <br>
            2、中獎後聯系下方客服發送邀請碼進行兌獎。 <br>
            3、填寫的姓名、電話和聯系地址必須真實，否則獎品沒辦法寄送。 <br>
            <p>
              <br>
            </p>                       <br>
            <div id="times">
              <div>
                剩余抽獎次數：<span id="curtimes5">{{ lotteryCountNum }}</span><br>
              </div>
            </div>
            <hr class="hr0">
            活動時間：<br>2024-08-08 08:00至2088-08-08 08:00 <br><br>
          </div>
        </div>
      </div>
    </div>

    <!--中奖记录弹窗-->
    <div id="mask-my" ref="mask-my" :class="activeMyClass? 'active':''  ">
      <div class="box-my">
        <span class="star"></span>
        <h2>中獎記錄</h2>
        <span id="close-my" @click="hiddenMaskMy"></span>
        <div class="con">
          <div class="text">
            <div>
              <h4>您的邀请码为:<br>
                {{ code }}</h4>
            </div>
            <div class="Detail" v-for="(item,index) in lotteryList">
              <b style='color:#000'>獎品：{{ item.name }}</b> <span
              style='color:gray; white-space:nowrap'>[{{ item.lotteryTime }}]</span><br>
              <hr>
            </div>
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

    <!--中奖提示-->
    <div id="mask" ref="mask-model" :class="activeMaskModelClass? 'active':''  ">
      <div class="blin"></div>
      <div class="caidai"></div>
      <div class="winning">
        <!--        <div class="red-head"></div>-->
        <div>
          <img style="height: 9rem;width: 8rem;" :src="lotteryImgSrc">
        </div>
        <!--        <div class="red-body"></div>-->
        <!--        <div id="card">
                  <a href="" target="_self" class="win"></a>
                </div>
                <div class="btn"></div>-->
        <span id="close" @click="hiddenMaskModel"></span>
      </div>
    </div>
  </div>
</template>

<script>

import $ from 'jquery';
import {codeDetail} from "@/api/lottery/play";
import LotteryGrid from "@/components/LotteryGrid"

export default {
  name: "App",
  components: {
    LotteryGrid
  },
  created() {
    const urlParams = new URLSearchParams(window.location.search);
    // this.code = urlParams.get('code');
    let newInvalidCode = urlParams.get('code');
    if(newInvalidCode.indexOf("?code=") !== -1){
      newInvalidCode = newInvalidCode.replace("?code=","");
    }
    this.code = newInvalidCode;
  },
  data() {
    return {
      code: '',
      activeCodeClass: false,
      activeClass: false,
      activeMyClass: false,
      activeMaskModelClass: false,
      lotteryImgSrc: '',
      lotteryList: [],
      lotteryCountNum: 0,
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
    this.resetView();
    this.checkCode();
  },
  methods: {
    checkCode() {
      if (this.code == '') {
        this.activeCodeClass = true
      }
    },
    resetView() {
      var innerWidth = window.innerWidth;
      if (innerWidth < 325) {
        $('html').css({
          'font-size': '31.36px'
        })
        $('#booxxx').css({
          'transform': 'scale(0.5)'
        })
      }

      if (innerWidth >= 325 && innerWidth < 400) {
        $('html').css({
          'font-size': '38.36px'
        })

        $('#booxxx').css({
          'transform': 'scale(0.6)'
        })
      }

      if (innerWidth >= 400 && innerWidth < 600) {
        $('html').css({
          'font-size': '41.36px'
        })

        $('#booxxx').css({
          'transform': 'scale(0.6)'
        })
      }

      if (innerWidth > 600 && innerWidth <= 770) {
        $('html').css({
          'font-size': '79.36px'
        })

        $('#booxxx').css({
          'transform': 'scale(1.0)'
        })
      }

      if (innerWidth > 770 && innerWidth <= 1024) {
        $('html').css({
          'font-size': '92.36px'
        })

        $('#booxxx').css({
          'transform': 'scale(1.0)'
        })
      }

      if (innerWidth > 1024) {
        $('html').css({
          'font-size': '101.36px'
        })

        $('#booxxx').css({
          'transform': 'scale(1.0)'
        })
      }
    },
    showMaskRule() {
      this.activeClass = true
    },
    showMaskMy() {
      this.activeMyClass = true
    },
    showMaskModel(target) {
      $('html').css({
        'font-size': '401.36px'
      })
      this.lotteryImgSrc = target.image;
      var currentDate = new Date();
      var year = currentDate.getFullYear();
      var month = String(currentDate.getMonth() + 1).padStart(2, '0');
      var day = String(currentDate.getDate()).padStart(2, '0');
      var hours = String(currentDate.getHours()).padStart(2, '0');
      var minutes = String(currentDate.getMinutes()).padStart(2, '0');
      var seconds = String(currentDate.getSeconds()).padStart(2, '0');
      var formattedDateTime = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
      target['lotteryTime'] = formattedDateTime;
      this.lotteryList.push(target);

      if (this.lotteryCountNum > 0) {
        this.lotteryCountNum = this.lotteryCountNum - 1;
      }
      this.activeMaskModelClass = true;
    },
    clickCode() {
      if (this.code != '') {
        // this.$refs.grid.updateCode(this.code);
        let currentUrl = window.location.href;
        console.log('currentUrl.indexOf(\'?code=\'):',currentUrl.indexOf('?code='))
        if(currentUrl.indexOf('?code=') !== -1){
          currentUrl = currentUrl.replace('?code=','');
        }
        const params = 'code='+this.code;
        window.location.href = currentUrl + '?' + params;
        // this.hiddenCodeRule();
      }
    },
    hiddenMaskRule() {
      this.activeClass = false
    },
    hiddenCodeRule() {
      this.activeCodeClass = false;
    },
    hiddenMaskMy() {
      this.activeMyClass = false
    },
    hiddenMaskModel() {
      this.resetView();
      this.lotteryImgSrc = '';
      this.activeMaskModelClass = false
    },
    updateCount(target) {
      this.lotteryCountNum = target;
    }

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
  font-index: -999
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
  background: url(assets/lottery/lottery1.jpg) no-repeat top center;
}

#lottery table td a:hover {
  background-image: url(assets/lottery/lottery2.jpg);
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

.flex-fix-play {
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
