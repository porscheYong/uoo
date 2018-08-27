# Git Commit Message规范

每次提交，Commit Message都包括三个部分：Header，Body和Footer。

    <type>(<scope>): <subject>
    // 空一行
    <body>
    // 空一行
    <footer>

其中Header是必须的，Body和Footer可以省略。

不管是哪一个部分，任何一行都不得超过72个字节（或100个字符）。这是为了避免自动换行影响美观。

### Header

Header部分只有一行，包括三个字段：`type`（必需）、`scope`（可选）和`subject`（必需）。

##### 1. type

`type`用来说明commmit的类别，只允许使用下面7个标识：

* feat：新功能（feature）
* fix：修补bug
* docs：文档（documentation）
* style：格式（不影响代码运行的变动）
* refactor：重构（既不是新增功能，也不是修改bug的代码变动）L
* test：增加测试
* chore：构建过程或辅助工具的变动

如果`type`为`feat`和`fix`，则该commit将肯定出现在Change Log之中。其他情况（`docs`、`chore`、`style`、`refactor`、`test`）由你决定要不要放入Change Log，建议是不要。

##### 2. scope

`scope`用于说明commit影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同。

##### 3. subject

`subject`是commit目的的简短描述，不超过50个字符。

* 以动词开头，使用第一人称现在时，比如change，而不是changed或changes
* 第一个字母小写
* 结尾不加句号（.）

### Body

Body部分是对本次commit的详细描述，可以分成多行。下面是一个范例：

    More detailed explanatory text, if necessary. Wrap it to 
    about 72 characters or so. 

    Further paragraphs come after blank lines.

    - Bullet points are okay, too
    - Use a hanging indent

有两个注意点：

1. 使用第一人称现在时，比如使用`change`而不是`changed`或`changes`。
2. 应该说明代码变动的动机，以及与以前行为的对比

### Footer

Footer部分只用于两种情况。

##### 1. 不兼容变动

如果当前代码与上一个版本不兼容，则Footer部分以`BREAKING CHANGE`开头，后面是对变动的描述、以及变动理由和迁移方法。

    BREAKING CHANGE: isolate scope bindings definition has changed.

    To migrate the code follow the example below:

    Before:

    scope: {
      myAttr: 'attribute',
    }

    After:

    scope: {
      myAttr: '@',
    }

    The removed `inject` wasn't generaly useful for directives so there should be no code using it.

##### 2. 关闭Issue

如果当前commit针对某个issue，那么可以在Footer部分关闭这个issue。

    Closes #234

也可以一次关闭多个issue

    Closes #123, #245, #992

### Revert

还有一种特殊情况，如果当前commit用于撤销以前的commit，则必须以`revert:`开头，后面跟着被撤销commit的Header。

Example：

    revert: feat(pencil): add 'graphiteWidth' option
    This reverts commit 667ecc1654a317a13331b17617d973392f415f02.

Body部分的格式是固定的，必须写成`This reverts commit @lt;hash>.`，其中的`hash`是被撤销commit的SHA标示符。

如果以前commit与被撤销的commit，在同一个发布（release）里面，那么它们都不会出现在Change Log里面。如果两者在不同的发布，那么当前commit，会出现在Change Log的`Reverts'小标题下面。

### 参考文档

* [Commit message 和 Change log 编写指南 - 阮一峰](http://www.ruanyifeng.com/blog/2016/01/commit_message_change_log.html)
