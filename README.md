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

### Recompose 重组

readable() 遍历 StateRecord 链表，找到一个最新的、可用的 StateRecord

### Snapshot 同时间刷新支持，多线程刷新支持

## remember: 起到缓存作用，防止多次初始化，用在重组作用域中防止被重复初始化

remember(key ...) 根据key是否相同来决定是否使用缓存记录
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
2. When a public property of the type changes, composition will be notified.
3. All public property types are stable.


1. 现在相等就永远相等
2. 当公开属性改变的时候，通知到用到这个属性的 Composition (如果所有属性都是mutableState的，compose
   自动认定为是可靠的对象)
3. 所有公开属性都是Stable的

Compose 只能做到判断第二条

Int Float Boolean 等基本类型和 String 都是 Stable 的

## Compose 中不使用 data class

```kotlin
class User(name: String) {
    val name by mutableStateOf(name)
}

```

### @Immutable 标记为不可变的，在 Compose 中，与 @Stable 效果相同