AndroidFlux 最初计划的三个项目之一，另外两个是

* [Hello World](https://github.com/androidflux/flux)
* [Todo List](https://github.com/androidflux/AFlux-TodoList)

由于某些原因，这个项目一直没有进行。

在FB的flux-chat项目中，Store之间存在依赖关系重点演示的是waitFor方法的使用，但是一直以来FB的waitFor一直为人诟病。
使用waitFor的原因是Store之间存在依赖关系，需要按续更新。为了解决这个问题，出现了很多方案，比如Redux，只有一个Store保存整个App的状态，又或者是通过一个StoreGroup来管理Store，并保证在个Store更新之后再通知UI的变化。Store之间的依赖使问题变得复杂，所以在设计Android版本Chat应用的时候，故意避免了Store依赖的情况。

Store的自包含，每个Store应该维护自己的数据，理解这点非常重要。在实现中，把所有的消息（包括和其他人的对话消息）都维护在 `MessageStore`中，这样`MessageStore`拥有对话的全部知识，所以如果需要切换不同人之间的对话，仅仅根据`ThreadID`进行数据操作即可。但是在典型的 "MV*" 架构中，这样角色是在"M"中完成的，更确切的说是领域层。

PS：这个项目还在开发进行中
