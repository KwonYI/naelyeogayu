module.exports = {
  chainWebpack: (config) => {
    config.module
      .rule("eslint")
      .use("eslint-loader")
      .tap((options) => {
        options.fix = true; // auto-fix 옵션
        return options;
      });
  },
};
