'use strict'
const utils = require('./utils')
const webpack = require('webpack')
const config = require('../config')
const merge = require('webpack-merge')
const path = require('path')
const baseWebpackConfig = require('./webpack.base.conf')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin')
const portfinder = require('portfinder')


const HOST = process.env.HOST
const PORT = process.env.PORT && Number(process.env.PORT)

const hbjWebpackconfig = merge(baseWebpackConfig, {
  module: {
    rules: utils.styleLoaders({ sourceMap: config.hbj.cssSourceMap, usePostCSS: true })
  },
  // cheap-module-eval-source-map is faster for development
  devtool: config.hbj.devtool,

  // these hbjServer options should be customized in /config/index.js
  hbjServer: {
    clientLogLevel: 'warning',
    historyApiFallback: {
      rewrites: [
        { from: /.*/, to: path.posix.join(config.hbj.assetsPublicPath, 'index.html') },
      ],
    },
    hot: true,
    contentBase: false, // since we use CopyWebpackPlugin.
    compress: true,
    host: HOST || config.hbj.host,
    port: PORT || config.hbj.port,
    open: config.hbj.autoOpenBrowser,
    overlay: config.hbj.errorOverlay
      ? { warnings: false, errors: true }
      : false,
    publicPath: config.hbj.assetsPublicPath,
    proxy: config.hbj.proxyTable,
    quiet: true, // necessary for FriendlyErrorsPlugin
    watchOptions: {
      poll: config.hbj.poll,
    }

  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': require('../config/hbj.env')
    }),
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NamedModulesPlugin(), // HMR shows correct file names in console on update.
    new webpack.NoEmitOnErrorsPlugin(),
    // https://github.com/ampedandwired/html-webpack-plugin
    new HtmlWebpackPlugin({
      filename: 'index.html',
      template: 'index.html',
      inject: true
    }),
    // copy custom static assets
    new CopyWebpackPlugin([
      {
        from: path.resolve(__dirname, '../static'),
        to: config.hbj.assetsSubDirectory,
        ignore: ['.*']
      }
    ])
  ]
})

module.exports = new Promise((resolve, reject) => {
  portfinder.basePort = process.env.PORT || config.hbj.port
  portfinder.getPort((err, port) => {
    if (err) {
      reject(err)
    } else {
      // publish the new Port, necessary for e2e tests
      process.env.PORT = port
      // add port to hbjServer config
      hbjWebpackconfig.hbjServer.port = port

      // Add FriendlyErrorsPlugin
      hbjWebpackconfig.plugins.push(new FriendlyErrorsPlugin({
        compilationSuccessInfo: {
          messages: [`Your application is running here: http://${hbjWebpackconfig.hbjServer.host}:${port}`],
        },
        onErrors: config.hbj.notifyOnErrors
        ? utils.createNotifierCallback()
        : undefined
      }))

      resolve(hbjWebpackconfig)
    }
  })
})
