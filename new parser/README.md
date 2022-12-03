这里是新版的解析器

还是从turanar 这位大佬这里搬运来的https://github.com/turanar/stellaris-config-parser

目前正在调试中，遇到了困难，正在用英语测试解析器是否能正常工作

启动时要输入参数：stellaris所在的目录

以及要把localisation/english下的subterranean_l_english.yml（空文件）文件移除，不然会报错

因为新版本，蠢驴在trigger脚本中嵌套了trigger，解析器总是报错 解析器中使用了antlr4语言来分析文件

我找不到解决办法，急需帮助。