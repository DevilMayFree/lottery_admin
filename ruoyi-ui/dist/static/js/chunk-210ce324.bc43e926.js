(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-210ce324"],{"061b":function(e,t,n){"use strict";var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],style:"height:"+e.height},[n("iframe",{staticStyle:{width:"100%",height:"100%"},attrs:{src:e.src,frameborder:"no",scrolling:"auto"}})])},r=[],a={props:{src:{type:String,required:!0}},data:function(){return{height:document.documentElement.clientHeight-94.5+"px;",loading:!0,url:this.src}},mounted:function(){var e=this;setTimeout((function(){e.loading=!1}),300);var t=this;window.onresize=function(){t.height=document.documentElement.clientHeight-94.5+"px;"}}},o=a,u=n("2877"),c=Object(u["a"])(o,i,r,!1,null,null,null);t["a"]=c.exports},"4a49":function(e,t,n){"use strict";n.r(t);var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("i-frame",{attrs:{src:e.url}})},r=[],a=n("061b"),o={name:"Swagger",components:{iFrame:a["a"]},data:function(){return{url:"http://139.180.213.111:8080//swagger-ui/index.html"}}},u=o,c=n("2877"),l=Object(c["a"])(u,i,r,!1,null,null,null);t["default"]=l.exports}}]);