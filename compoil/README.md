# Compose

## PreviewParameterProvider

## Compose Arch (Architecture)
![](https://engineering.monstar-lab.com/assets/img/articles/2023-07-14-Jetpack-Compose-UI-Architecture/data_flow.svg)
- Contract

```
/**
 * UI State that represents HomeScreen
 * 存储 HomeScreen UI状态
 **/
data class PageState(
    val pageItems: MutableList<Records> = mutableListOf(),
    var isRefreshing: Boolean = false
)

/**
 * Home Actions emitted from the UI Layer
 * 来自UI层发送的操作
 * passed to the coordinator to handle
 * 传递给协调器处理
 **/
data class PageActions(
    val onRefresh: () -> Unit = {},
    val onFloatingButtonClick: (fragmentManager: FragmentManager) -> Unit = {},
)

/**
 * PreviewParameter Provider for HomeScreen Preview
 * Add values to the sequence to see the preview in different states
 **/
class HomeStatePreviewParameterProvider : PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState>
        get() = sequenceOf(

        )
}
```

- Coordinator

```
/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * 协调器 负责处理来自UI层的操作
 * and one-shot actions based on the new UI state
 * 以及 基于新的UI状态的一次性动作
 */
class PageCoordinator(
    val viewModel: PageViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun handleRefresh() {
        viewModel.refreshPage()
    }

    fun handleFloatingButtonClick(fragmentManager: FragmentManager) {
        viewModel.floatingButtonClick(fragmentManager)
    }
}

@Composable
fun rememberPageCoordinator(
    viewModel: PageViewModel = koinViewModel()
): PageCoordinator {
    return remember(viewModel) {
        PageCoordinator(
            viewModel = viewModel
        )
    }
}
```

- Route
```
@Composable
fun PageRoute(
    coordinator: PageCoordinator = rememberPageCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(PageState())

    // UI Actions
    val actions = rememberPageActions(coordinator)

    // UI Rendering
    PageScreen(uiState, actions)
}


@Composable
fun rememberPageActions(coordinator: PageCoordinator): PageActions {
    return remember(coordinator) {
        PageActions(
            onRefresh = coordinator::handleRefresh,
            onFloatingButtonClick = coordinator::handleFloatingButtonClick,
        )
    }
}
```
- Screen
```
@Composable
fun PageScreen(
    state: PageState,
    actions: PageActions
) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = { FloatingButton(state = state, actions = actions) },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { innerPadding ->
        PullRefreshIndicatorView(
            modifier = Modifier.padding(innerPadding),
            dataList = state.pageItems,
            isRefreshing = state.isRefreshing,
            refresh = actions.onRefresh
        )
    }
}
```
- ViewModel
```
class PageViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<PageState> = MutableStateFlow(PageState())

    val stateFlow: StateFlow<PageState> = _stateFlow.asStateFlow()

    fun refreshPage() {
        _stateFlow.update {
            it.copy(
                isRefreshing = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val data = getCacheRecords()
            delay(500)
            _stateFlow.update {
                it.copy(
                    isRefreshing = false,
                    pageItems = data
                )
            }
        }
    }

    fun floatingButtonClick(fragmentManager: FragmentManager) {
        CreateOilRecordsDialog.show(fragmentManager, object : ICreateOilRecords {
            override fun callback(currentMileage: Int, oilInjection: Float) {
                _stateFlow.update {
                    it.copy(
                        isRefreshing = true
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    addNewRecords(Records(System.currentTimeMillis(), currentMileage, oilInjection))
                    _stateFlow.update {
                        it.copy(
                            isRefreshing = false,
                            pageItems = getCacheRecords()
                        )
                    }
                }
            }
        })
    }
}
```

# Compose Arch with AI

## [Ollama](https://ollama.com)

## CodeGemma
```
ollama --help
Large language model runner

Usage:
  ollama [flags]
  ollama [command]

Available Commands:
  serve       Start ollama
  create      Create a model from a Modelfile
  show        Show information for a model
  run         Run a model
  stop        Stop a running model
  pull        Pull a model from a registry
  push        Push a model to a registry
  list        List models
  ps          List running models
  cp          Copy a model
  rm          Remove a model
  help        Help about any command

Flags:
  -h, --help      help for ollama
  -v, --version   Show version information

Use "ollama [command] --help" for more information about a command.
```

```
➜  ~ ollama run codegemma:latest
>>> ?
I am ready to assist you with anything you need. Please provide me with
the context or task you have in mind.

>>> Who r u?
I am a large language model, trained by Google. I am here to assist you
with information retrieval, language translation, and more. How can I help
you today?

>>> Can u speak Chinese?
是的，我很擅长中文。请问您需要我做些什么？

>>> 你的模型数据截至至什么时间
我的模型数据截至到 2023 年 10 月。

>>> Send a message (/? for help)
```

## Arch with CodeGemma


# Compose Multiplatform
