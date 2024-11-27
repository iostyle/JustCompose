# Compose

## PreviewParameterProvider

## Compose Arch (Architecture)

- Contract

```
/**
 * UI State that represents HomeScreen
 * 存储 HomeScreen UI状态
 **/
class HomeState

/**
 * Home Actions emitted from the UI Layer
 * 来自UI层发送的操作
 * passed to the coordinator to handle
 * 传递给协调器处理
 **/
data class HomeActions(
    val onClick: () -> Unit = {}
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
class HomeCoordinator(
    val viewModel: HomeViewModel
) {
    val screenStateFlow = viewModel.stateFlow

}

@Composable
fun rememberHomeCoordinator(
    viewModel: HomeViewModel = koinViewModel()
): HomeCoordinator {
    return remember(viewModel) {
        HomeCoordinator(
            viewModel = viewModel
        )
    }
}
```

- Route
- Screen
- ViewModel

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
