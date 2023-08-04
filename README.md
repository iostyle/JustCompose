# JustCompose

#### Flutter(cross-platform) NDK Skia 绘制

#### Compose(multi-platform) Canvas 绘制

### 布局转化

- FrameLayout、RelativeLayout -> Box
- LinearLayout -> Column、Row
- RecyclerView -> lazyColumn、lazyRow
- ScrollView -> Modifier.VerticalScroll 、Modifier.HorizonScroll
- ViewPager -> Page

### Modifier 通用设置

- 代码存在执行顺序，显示效果会有差异，比如 padding 和 background 书写顺序不同，影响background绘制区域。
  click 点击区域亦是如此
- 非 Builder 性质

专项设置用函数参数，如 Text 设置字体大小、字体颜色

### Compose 分层

|     分层      |        作用         |
|:-----------:|:-----------------:| 
| material(3) |    Material组件     |
| foundation  |      基础视图组件       |
|  animation  |        动画         |
|     ui      |    绘制、测量、布局反馈     | 
|   runtime   | 底层概念模型，数据结构、状态转换等 | 
|  compiler   |       编译过程        | 

面相切面编程(AOP) 常见两种方式实现 1.Annotation Processor 注解处理器 2.修改字节码

@Composable - 识别符 使用的是编译器插件 Compiler Plugin

### MutableState 可变可被订阅

### 刷新包含

- 组合（Composition）也就是执行Composable代码的过程
- 布局
- 绘制

### MutableState -> StateObject -> StateRecord -> Compose 支持事务功能
stateRecord 通过链表存储