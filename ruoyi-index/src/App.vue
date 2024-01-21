<template>
  <div id="app">
    <h2>hello index</h2>
  </div>
</template>

<script>

import {codeDetail} from "@/api/lottery/play";

export default {
  name: "App",
  metaInfo() {
    return {
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  },
  mounted() {
    codeDetail("b12b252d794a47889b193531c359b808").then(response => {
      console.log("response:",response)
    })

  }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
