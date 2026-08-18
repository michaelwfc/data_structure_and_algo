# Reference
## CS

- https://labuladong.online/en/algo/home/
- https://github.com/labuladong/fucking-algorithm
- https://leetcode.com/
- https://leetcode.cn/
- https://leetcode.com/studyplan/top-100-liked/
- https://leetcode.cn/studyplan/top-100-liked/
- https://www.nowcoder.com/exam/intelligent?questionJobId=10&subTabName=intelligent_page&tagId=273590

## AI

- https://www.xiaolincoding.com/project/xiaolinnote.html
- https://github.com/guocong-bincai/ai-interview-guide

# 1. 数据结构

## 1. 静态数组 vs 动态数组
- https://labuladong.online/zh/algo/essential-technique/linked-list-skills-summary/
 

我认为暂且可以把「数组」分为两大类，一类是「静态数组」，一类是「动态数组」。

「静态数组」就是一块连续的内存空间，我们可以通过索引来访问这块内存空间中的元素，这才是数组的原始形态。

而「动态数组」是编程语言为了方便我们使用，在静态数组的基础上帮我们添加了一些常用的 API，比如 push, insert, remove 等等方法，这些 API 可以让我们更方便地操作数组元素，不用自己去写代码实现这些操作。

本章的内容就是带大家仅仅使用最原始的静态数组，自己实现一个动态数组，实现增删查改的常见 API。以后你在使用标准库提供的数据结构时，就知道它们的底层运行原理了。

有了动态数组，后面讲到的队列、栈、哈希表等复杂数据结构都会依赖它进行实现。

### 静态数组

```c++
// 定义一个大小为 10 的静态数组
int arr[10];

// 用 memset 函数把数组的值初始化为 0
memset(arr, 0, sizeof(arr));

// 使用索引赋值
arr[0] = 1;
arr[1] = 2;

// 使用索引取值
int a = arr[0];
```

我梳理一下上面的因果逻辑，静态数组本质上就是一块连续的内存空间，int arr[10] 这个语句我们可以得知：

1、我们知道这块内存空间的首地址（数组名 arr 就指向这块内存空间的首地址）。

2、我们知道了每个元素的类型（比如 int），也就是知道了每个元素占用的内存空间大小（比如一个 int 占 4 字节，32 bit）。

3、这块内存空间是连续的，其大小为 10 \* sizeof(int) 即 40 字节。

所以，我们获得了数组的超能力「随机访问」：只要给定任何一个数组索引，我可以在 O(1) 的时间内直接获取到对应元素的值。

因为我可以通过首地址和索引直接计算出目标元素的内存地址。计算机的内存寻址时间可以认为是 O(1)，所以数组的随机访问时间复杂度是 O(1)。

但是，一个人最大的优势往往也是他的最大劣势。数组连续内存的特性给了他随机访问的超能力，但它也因此吃了不少苦，下面介绍。

#### 增删查改

数据结构的职责就是增删查改，再无其他。

那么刚刚介绍数组这种数据结构的底层原理，我们其实只介绍了「查」和「改」的部分，也就是通过索引修改和访问对应元素的值。那么「增删」这两个操作又是如何实现的呢？

总结
综上，静态数组的增删查改操作的时间复杂度是：

增：
在末尾追加元素：O(1)。
在中间（非末尾）插入元素：O(N)。

删：
删除末尾元素：O(1)。
删除中间（非末尾）元素：O(N)。

查：给定指定索引，查询索引对应的元素的值，时间复杂度 O(1)。
改：给定指定索引，修改索引对应的元素的值，时间复杂度 O(1)。

有读者可能问，刚才不是还探讨过数组的扩容操作吗，扩容涉及到新数组空间的开辟和数据的复制，时间复杂度是 O(N)，这个复杂度为什么没有算到「增」的复杂度里面呢？

这个问题很好，但并不是每次增加元素的时候都会触发扩容，所以扩容的复杂度要用「均摊时间复杂度」来分析，这个概念我在
时空复杂度分析方法
中有详细的讲解，这里就不展开了。

还有个问题初学者要注意，我们说数组的查、改复杂度是 O(1)，这个仅仅适用于给定索引的情况。如果反过来，比方说给你一个值，让你去找这个值在数组中对应的索引，那你只能遍历整个数组去寻找对吧，这个复杂度就是 O(N) 了。

所以说要搞清楚原理，而不要去背概念。原理懂了，概念你自己都能推导出来的。

### 动态数组

首先，你不要以为动态数组可以解决静态数组在中间增删元素效率差的问题，不可能解决的。数组随机访问的超能力源于数组连续的内存空间，而连续的内存空间就不可避免地面对数据搬移和扩缩容的问题。

动态数组底层还是静态数组，只是自动帮我们进行数组空间的扩缩容，并把增删查改操作进行了封装，让我们使用起来更方便而已。

```c++
// 创建动态数组
// 不用显式指定数组大小，它会根据实际存储的元素数量自动扩缩容
vector<int> arr;

for (int i = 0; i < 10; i++) {
    // 在末尾追加元素，时间复杂度 O(1)
    arr.push_back(i);
}

// 在中间插入元素，时间复杂度 O(N)
// 在索引 2 的位置插入元素 666
arr.insert(arr.begin() + 2, 666);

// 在头部插入元素，时间复杂度 O(N)
arr.insert(arr.begin(), -1);

// 删除末尾元素，时间复杂度 O(1)
arr.pop_back();

// 删除中间元素，时间复杂度 O(N)
// 删除索引 2 的元素
arr.erase(arr.begin() + 2);

// 根据索引查询元素，时间复杂度 O(1)
int a = arr[0];

// 根据索引修改元素，时间复杂度 O(1)
arr[0] = 100;

// 根据元素值查找索引，时间复杂度 O(N)
int index = find(arr.begin(), arr.end(), 666) - arr.begin();
```

# 2. 算法

## tWO_pointers
- https://labuladong.online/zh/algo/essential-technique/array-two-pointers-summary/
- https://www.bilibili.com/video/BV1iG411W7Wm?spm_id_from=333.788.videopod.sections&vd_source=b3d4057adb36b9b243dc8d7a6fc41295
 