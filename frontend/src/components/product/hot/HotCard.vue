<template>
  <v-hover v-slot="{ hover }">
    <v-card
      :elevation="hover ? 12 : 2"
      :class="{ 'ma-5': 'ma-5', hoverOnImg: hover }"
      height="400"
      width="350"
      @click="goDetail()"
    >
      <div v-show="hover">
        <v-card-text>
          <div>Word of the Day</div>
          <p class="display-1 text--primary">be•nev•o•lent</p>
          <p>adjective</p>
          <div class="text--primary">
            well meaning and kindly.<br />
            "a benevolent smile"
          </div>
        </v-card-text>
      </div>
      <v-img
        v-show="!hover"
        src="https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2100&q=80"
        height="400"
        width="350"
      ></v-img>
    </v-card>
  </v-hover>
</template>

<script>
export default {};
</script>

<style>
.hoverOnImg {
  max-height: 400;
  max-width: 350;
  position: relative;
  z-index: 1;
}
.hoverOnImg::after {
  position: absolute;
  height: 100%;
  width: 100%;
  content: "";
  background: url("https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2100&q=80");
  background-size: cover;
  opacity: 0.4;
  top: 0;
  left: 0;
  z-index: -1;
}
</style>
