# Java 业务开发常见错误 100 例

### 1. 并发工具类库

- 没有意识到线程重用导致用户信息错乱的Bug：ThreadLocal 
- 使用了线程安全的并发工具，并不代表解决了所有线程安全问题：ConcurrentHashMap misuse
- 没有充分了解并发工具的特性，从而无法发挥其威力：Concurrent HashMap performance
- 没有认清并发工具的使用场景，因而导致性能问题：CopyOnWriteList misuse

知识点
1. Stream API
2. 并发容器及使用场景
3. StopWatch 使用

### 2. 代码加锁问题
- 加锁前要清楚 **锁和被保护的对象** 是不是一个层面的：LockScope
- 加锁要考虑锁的粒度和场景问题：Lock Granularity --- 注意控制临界区的范围，避免锁持有的时间过长而导致其它处理阻塞等待
- 多把锁要小心死锁问题：deadlock

知识点：
- 锁粒度问题
- 死锁处理方式

### 3. 线程池使用

- 线程池的声明需要手动进行：ThreadPool oom
- 线程池线程管理策略详解：ThreadPool oom
- 务必确认清楚线程池本身是不是复用的：ThreadPool reuse ---- 不要只创建不复用
- 需要仔细斟酌线程池的混用策略：ThreadPool mix-use --- 分析业务的轻重缓急，分析线程是I/O操作还是计算操作

知识点：
- JDK 原生线程池的任务执行策略
  - 不会 **初始化** corePoolSize 个线程，有任务来了才创建工作线程；
  - 当核心线程满了之后不会立即扩容线程池，先把任务堆积到工作队列中；
  - 当工作队列满了后扩容线程池，一直到线程个数达到 maximumPoolSize 为止；
  - 如果队列已满且达到了最大线程后还有任务进来，按照拒绝策略处理；
  - 当线程数大于核心线程数时，线程等待 keepAliveTime 后还是没有任务需要处理的话，收缩线程到核心线程数

- Tomcat 线程池的任务执行策略
  1. 不会 **初始化** corePoolSize 个线程，有任务来了才创建工作线程；
  2. 当核心线程满了之后不会立即扩容线程池，会调用工作队列的 offer()
  3. 在任务队列中会判断线程池创建的线程个数是否到达 maximumPoolSize ,未到达会返回 false
  4. 线程池线程数达到了 maximumPoolSize 之后，开始真正向任务队列添加任务
  5. 当线程数大于核心线程数时，线程等待 keepAliveTime 后还是没有任务需要处理的话，收缩线程到核心线程数


### 4. 连接池使用
- 注意鉴别客户端SDK是否基于连接池：jedis
- 使用连接池务必确保复用：httpclient
- 连接池的配置不是一成不变的：datasource --- 注意验证配置参数是否生效

知识点总结

- TCP连接客户端 SDK 对外提供 API 的三种方式：
  - 连接池与连接分离：有一个XXXPool类负责连接池实现，先从其获得连接XXXConnection，然后用获得的连接进行服务端请求，完成后使用者需要归还连接
  - 直接进行服务端请求；这个类内部维护了连接池，SDK使用者无需考虑连接的获取和归还问题
  - 非连接池的API：一般命名为XXXConnection，以区分其是基于连接池还是单连接的，一般为短连接

- 连接池配置参数
  - connectRequestTimeout/connectWaitTimeout : 获取连接的超时时间
  - connectTimeout: TCP 连接超时时间
  - MaximumPoolSize: 连接池中最大连接数
  - IdleTimeout: 连接空闲检测