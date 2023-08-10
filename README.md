# JustCompose

#### Flutter(cross-platform) NDK Skia 绘制

#### Compose(multi-platform) Canvas 绘制

---

### 布局转化参考

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

---

# 状态订阅和自动更新 😁

### MutableState 可变可被订阅

### 刷新包含

- 组合（Composition）也就是执行Composable代码的过程
- 布局
- 绘制

### MutableState -> StateObject -> StateRecord -> Compose 支持事务功能

stateRecord 通过链表存储

### Recompose 重组

readable() 遍历 StateRecord 链表，找到一个最新的、可用的 StateRecord

### Snapshot 同时间刷新支持，多线程刷新支持

## remember: 起到缓存作用，防止多次初始化，用在重组作用域中防止被重复初始化

remember(key ...) 根据key是否相同（地址）来决定是否使用缓存记录
相当于 DataBinding中 的 ObservableField(any ...)

### Compose: Stateless 无状态 (非功能，是特点)

状态：控件属性

### State Hoisting 状态提升

尽量不往上提状态

### Single Source of Truth 单一信息源

### Unidirectional Data Flow 单向数据流

### Mutable集合的内部变化并不会触发get set的hook去recompose。

可以使用MutableState集合，当内部元素变化时会触发recompose。（MutableStateListOf() /
MutableStateMapOf()）

## 重组的风险和智能优化

### Recompose Scope 重组作用域

Compose ：自动更新 -> 「更新范围过大、超过需求」 -> 「跳过没必要的更新」

作用域内发生重组，整个作用域都会被重新调用，没有参数或者说没有参数变化(equals 并且值是可靠的val)
的方法会被优化掉不被重复执行

### Structural Equality 结构性相等。 相当于 Kotlin 的 ==

判断参数是否变化，是否需要recompose执行

比对的对象如果是可靠（参数都为val）的，比对成功则会自动跳过recompose，如果是不可靠的，则绝对不会触发跳过recompose!
这个问题的本质是可靠性问题

## @Stable 标记为可靠的/稳定的

1. The result of equals will always return the same result for the same two instances.

   现在相等就永远相等
2. When a public property of the type changes, composition will be notified.

   当公开属性改变的时候，通知到用到这个属性的 Composition (如果所有属性都是mutableState的，compose
   自动认定为是可靠的对象)
3. All public property types are stable.

   所有公开属性都是Stable的

Compose 只能做到判断第二条

Int Float Boolean 等基本类型和 String 都是 Stable 的

## Compose 中不使用 data class

```kotlin
class User(name: String) {
    val name by mutableStateOf(name)
}
```

### @Immutable 标记为不可变的，在 Compose 中，与 @Stable 效果相同

## **derivedStateOf

### remember(参数) 判断的是参数的地址是否发生变化

这里要注意，如果参数是基本数据类型，则数据变化时，地址也发生变化，所以监听可以被触发；
但如果监听的是 list 等，常规的增删并不会导致内存地址的变化，所以监听不会被触发，recompose 会被跳过。
所以如果监听这种 state 变化，使用 derivedStateOf {}

1. 监听状态变化从而自动刷新，有两种写法：带参数的remember(); 不带参数的 remember() + {
   derivedStateOf{} }
2. 对于状态对象来说（mutableStateListOf、mutableStateOf），带参数的 remember() 不能使用，所以只能使用
   derivedStateOf
3. 对于函数参数里的字符串(基础数据类型)，监听链条会被掐断，所以不能用 derivedStateOf()，而只能用带参数的
   remember()

带参数的 remember() 可以判断对象的重新赋值，而 derivedStateOf() 不能完美做到，所以带参数的 remember()
适用于函数参数的监听

derivedStateOf() 适用于 **状态对象** 的监听

by mutableStateOf() 所代理的对象，用两种方式都行

当一个状态对象作为参数时，既要使用 remember 也要使用 derivedStateOf，来保证既 对象地址变化
时可以被监听，也保证内部属性变化时，可以被监听。

### CompositionLocal (Composition 局部变量)

共识：创建成一个「不怕影响到更大范围」的对象

场景：上下文 / 环境 / 主题

LocalContext.current

**参数列表**是**由内向外**的需求，**CompositionLocal**是**由外向内**的提供

不一定两种方式一定要二选其一，参考Text

```Kotlin
val textColor = color.takeOrElse {
    style.color.takeOrElse {
        LocalContentColor.current
    }
}
```

### StaticCompositionLocal

不会记录state、 当数据变化时，全量更新

数据经常刷新使用 CompositionLocal 精准刷新，不经常刷新使用 StaticCompositionLocal

--- 

# 动画 😎

### 状态转移型动画 animateXxxAsState()

### LaunchedEffect

Compose 中使用协程，recompose时，参数列表的值没有发生变化则跳过，发生变化则重启

## AnimationSpec

- spring 弹性
- tween 补间
- snap 切换
- keyFrame 关键帧
- repeatable 重复动画

#### easing 缓动 也可以理解为插值器

- LinearEasing 线性
- FastOutSlowInEasing 先加速再减速
- LinearOutSlowInEasing 匀速然后减速
- FastOutLinearInEasing 持续加速

#### spring： dampingRatio 阻尼比  stiffness 刚度  visibilityThreshold 可见性阈值

---

### animateDecay: 惯性衰减，消散型动画

#### rememberSplineBasedDecay 样条衰减

与 Android 自带滑动惯性相同, 像素密度越大，摩擦力越大，更快停止

只能面向像素来使用，否则会受dpi影响导致摩擦力不同

#### exponentialDecay 指数衰减

面向dp 使用指数衰减

frictionMultiplier 摩擦系数

absVelocityThreshold ABS速度阈值 与 spring visibilityThreshold 类似，达到阈值停止

---

