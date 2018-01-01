# vain-manager

#### 导语：
>  写这个目的就希望有需要的能拿起来直接用   
所以并没有业务逻辑包含在里面  
有的只是各个管理系统通用的部分   
以后了解更多功能也会添加进来

### 约束：
#### 日志：

 默认日志级别为*info*
  
 错误日志级别为*error*
 
#### 分层：

 controller用名称+Controller结尾
 
 service接口为I+名称+Service结尾
 
 具体实现类为名称加Impl结尾
 
 dao层为名称加dao结尾
 
### 项目结构:

    ----com.vain.manager           项目核心
         ----common                公用抽象类
             ----controller        controller抽象类 
             ----dao               dao抽象类
             ----entity            实体,分页,响应类
             ----exception         全局异常
             ----service           公用service类
         ----component             项目初始化组件
         ----constant              项目全局静态常量
         ----controller            具体controller层
         ----converter             扩展的json转换
         ----dao                   具体的dao层
         ----entity                实体类
         ----interceptor           拦截器           
         ----listener              监听器
         ----service               具体service层
         ----shiro                
             ----authenticator     shiro扩展subject等
             ----exception         shiro全局异常
             ----filter            过滤器
             ----realm             realm逻辑实现
             ----service           认证service
             ----session           shiro全局Session
             ----token             自定义扩展的token
             ----web               shiro的filter的管理类
         ----util                  各类帮助类
    ----org.apache.log4j           日志

           
 
 
 
### 大致功能为：
* 角色精细粒度权限控制
* 富文本公告发表
* 动态发表（表情存储 网络关键词过滤）
* 定时任务执行
* 邮件系统

 
后续继续完善
