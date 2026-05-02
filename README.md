# 🍰 甜点管理系统

一个基于 Vue 3 + Spring Boot + MySQL 的现代化甜点管理系统。

## ✨ 功能特性

- 📊 首页数据统计与展示
- 📁 分类管理（增删改查）
- 🍰 甜点管理（增删改查、图片上传）
- 👤 用户登录/注册
- 🎨 现代化 UI 设计
- 📱 响应式布局

## 🛠️ 技术栈

### 前端
- Vue 3 (Composition API)
- Vite 8
- Element Plus
- Vue Router 5
- Axios

### 后端
- Spring Boot 3.2
- MyBatis
- MySQL 8
- JWT 认证
- Lombok

## 📁 项目结构

```
trae0430/
├── vue-test/              # 前端项目
│   ├── src/
│   │   ├── api/          # API 接口
│   │   ├── components/   # 组件
│   │   ├── views/        # 页面
│   │   ├── router/       # 路由
│   │   └── utils/        # 工具
│   ├── public/           # 静态资源
│   └── package.json
│
├── springboot-test/      # 后端项目
│   ├── src/
│   │   └── main/
│   │       ├── java/     # Java 源代码
│   │       └── resources/
│   ├── pom.xml
│   └── uploads/          # 上传文件目录
│
└── db.sql               # 数据库脚本
```

## 🚀 快速开始

### 环境要求

- Node.js >= 18
- JDK >= 17
- MySQL >= 8.0
- Maven >= 3.8

### 1. 数据库配置

```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE dessert_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入数据
mysql -u root -p dessert_shop < db.sql
```

修改后端配置文件 `springboot-test/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dessert_shop?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password  # 修改为你的密码
```

### 2. 启动后端

```bash
cd springboot-test
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动

### 3. 启动前端

```bash
cd vue-test
npm install
npm run dev
```

前端服务将在 `http://localhost:5173` 启动

## 👤 测试账号

| 用户名 | 密码 | 说明 |
|--------|------|------|
| admin | 123456 | 管理员 |
| test | 123456 | 测试用户 |
| guest | 123456 | 访客 |

## 📸 功能截图

### 首页
- 轮播图展示
- 数据统计卡片
- 快捷操作入口

### 分类管理
- 分类列表
- 新增/编辑/删除分类
- 分类图标上传

### 甜点管理
- 甜点列表
- 新增/编辑/删除甜点
- 甜点图片上传
- 分类筛选
- 关键词搜索

## 🎨 界面设计

- **配色方案**：清新蓝色系 (#3b82f6)
- **圆角设计**：16px 圆角卡片
- **阴影效果**：柔和的多层阴影
- **动画效果**：流畅的过渡动画

## 📝 API 接口

### 用户认证
- POST `/api/account/login` - 用户登录
- POST `/api/account/register` - 用户注册
- POST `/api/account/logout` - 用户登出

### 分类管理
- GET `/api/category/list` - 获取分类列表
- POST `/api/category/add` - 新增分类
- PUT `/api/category/update` - 更新分类
- DELETE `/api/category/delete/{id}` - 删除分类

### 甜点管理
- GET `/api/product/page` - 分页获取甜点列表
- GET `/api/product/detail/{id}` - 获取甜点详情
- POST `/api/product/add` - 新增甜点
- PUT `/api/product/update` - 更新甜点
- DELETE `/api/product/delete/{id}` - 删除甜点

### 文件上传
- POST `/api/file/upload` - 上传文件

### 数据统计
- GET `/api/statistics/home` - 获取首页统计数据

## 🔧 开发说明

### 前端开发

```bash
# 安装依赖
npm install

# 开发模式
npm run dev

# 构建生产版本
npm run build

# 预览生产版本
npm run preview
```

### 后端开发

```bash
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 打包项目
mvn clean package

# 运行项目
mvn spring-boot:run
```

## 📄 数据库

### 数据表
- `account` - 用户表
- `category` - 分类表
- `product` - 甜点表

### 数据库配置
- 数据库名：`dessert_shop`
- 字符集：`utf8mb4`
- 排序规则：`utf8mb4_unicode_ci`

## 📄 许可证

MIT License

## 👨‍💻 作者

甜点管理系统开发团队

---

⭐ 如果这个项目对你有帮助，请给个 Star！
