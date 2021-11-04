# off-heap-memory-sample

[堆外内存与堆内内存详解](https://blog.csdn.net/ZYC88888/article/details/80228531)

加上jvm启动参数，注意是jvm参数
```
--add-exports java.base/jdk.internal.ref=ALL-UNNAMED
```

如果没设置-XX:MaxDirectMemorySize，则默认与-Xmx参数值相同（如： -Xmx100m）

[深入浅出MappedByteBuffer](https://blog.csdn.net/qq_41969879/article/details/81629469)


[Java魔法类：Unsafe应用解析](https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html)
