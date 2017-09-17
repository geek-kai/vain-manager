/**
 * 创建angularJS程序的入口
 * 程序进来需要依赖的angularJS模板，angularJS程序的入口
 * 通用的业务工厂common。services，处理控制器的公共的访问请求
 * 页面的控制器home.controller，页面一进去处理的数据，进行展示
 * 页面控制器需要请求访问返回数据的操作，通过业务工厂 home.service 进行访问
 * ngCookies，angularJS的缓存，可以将数据保存到cookies里，在其他页面取到，这里作登录和未登录的验证
 * 自定义标签 directive，处理页面一些复杂的dom元素的操作
 */
angular.module("home.app", ["common.services", "home.controllers", "home.services", "ngCookies"]);


