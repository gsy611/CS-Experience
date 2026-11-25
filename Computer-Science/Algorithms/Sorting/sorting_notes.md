# 各种sorting algorithms

## Selection Sort
- Definition:Selection sort repeatedly selects the smallest (or largest) element from the unsorted part of the array and swaps it with the first unsorted element

- 轮次  i(当前要放最小值的位置)     未排序部分       找到最小值          交换操作               数组状态
  1      0                      8 4 6 2 7        2         swap arr[0]<->arr[3]       2 4 6 8 7
  2      1                      4 6 8 7          4         swap arr[1]<->arr[1]       2 4 6 8 7
  3      2                      6 8 7            6         swap arr[2]<->arr[2]       2 4 6 8 7
  4      3                      8 7              7         swap arr[3]<->arr[4]       2 4 6 7 8
  5      4                      8                8                无                  2 4 6 7 8

## Insertion Sort
- 定义：插入排序通过将每个新元素插入到其正确位置，一次一个元素地构建数组的有序部分。它将当前元素与有序部分中的元素进行比较，并将较大的元素向右移动。

- 假设数组为 A = [5, 2, 4, 6, 1, 3]。
步骤：
    1. 初始状态，第一元素5认为是已排序区 [5]
    2. 取第二元素2，将它插入已排序区[5]→[2,5]。
    3. 取第三元素4，插入[2,5]→[2,4,5]。
    4. 取第四元素6，插入[2,4,5]→[2,4,5,6]。
    5. 取第五元素1，插入[2,4,5,6]→[1,2,4,5,6]。
    6. 取第六元素3，插入[1,2,4,5,6]→[1,2,3,4,5,6]。
每次插入都是找到合适的位置，保证左边有序。

## Shell sorting
- 定义：希尔排序是插入排序的改进版。它的工作原理是先比较相距较远的元素，然后逐渐缩小元素之间的间距，直到列表排序完成
- Core Idea：Divide the array into groups using a gap, sort each group, and gradually reduce the gap until it becomes 1.
- Basic Steps:
    1. Choose a gap value (usually n/2, where n is the array length).
    2. Divide the array into groups according to the gap.
    3. Perform insertion sort within each group.
    4. Reduce the gap (usually gap = gap / 2).
    5. Repeat the process until gap = 1
- 例子
数组：8 5 3 7 6 2 9
数组长度n = 7
所以：gap = n/2 = 3
第一轮 gap = 3(数组按间隔3分组)
组1: 8 7 9
组2: 5 6
组3: 3 2
组1排序（插入排序）
8 7 9
比较 7 和 8
7 8 9
组2排序
5 6
已经有序
5 6
组3排序
3 2
排序后
2 3
第一轮结束数组
7 5 2 8 6 3 9
第二轮 gap = 1
gap = 3/2 = 1时，就变成普通插入排序。
当前数组：7 5 2 8 6 3 9
Pass1 插入5
7 5 2 8 6 3 9
↓
5 7 2 8 6 3 9
Pass2 插入2
5 7 2 8 6 3 9
↓
2 5 7 8 6 3 9
Pass3 插入8
2 5 7 8 6 3 9
不变
Pass4 插入6
2 5 7 8 6 3 9
↓
2 5 6 7 8 3 9
Pass5 插入3
2 5 6 7 8 3 9
↓
2 3 5 6 7 8 9
Pass6 插入9
不变
最终结果:
2 3 5 6 7 8 9

## Merge sorting:
- 定义：将两个有序数组合并成一个更大的有序数组。这一操作直接引出了一种简单的递归排序方法，称为归并排序：对数组进行排序，将其分成两半，分别对这两半进行递归排序，然后合并结果。
- 原地归并排序（in-place merge）：
假设有数组：
[8, 3, 5, 2, 9, 1]
① 分（Divide）
不断把数组一分为二：
[8,3,5]   [2,9,1]
[8] [3,5] [2] [9,1]
[3] [5]   [9] [1]
② 治（Conquer）
每个小数组（只有1个元素）默认是有序的
③ 合（Merge）关键步骤
把两个有序数组合并成一个有序数组：
例如：
[3] + [5] → [3,5]
[8] + [3,5] → [3,5,8]
最终：
[1,2,3,5,8,9]
核心：合并操作（最重要）
如何合并两个有序数组？
例如：
A = [3,5,8]
B = [1,2,9]
用两个指针比较：
    • 3 和 1 → 取 1 
    • 3 和 2 → 取 2 
    • 3 和 9 → 取 3 
    • 5 和 9 → 取 5 
    • 8 和 9 → 取 8 
    • 最后剩 9 
结果：
[1,2,3,5,8,9]

口诀：
左边小 → i走 
右边小 → 插进去（然后 i、j 一起走）

- 自顶向下的归并排序（Top-down Merge Sort）：

原始数组：E A S Y Q U E S T I O N

递归分解（Divide）
不断对半分：
E A S Y Q U E S T I O N
→ E A S Y Q U | E S T I O N
→ E A S | Y Q U | E S T | I O N
→ E A | S | Y Q | U | E S | T | I O | N
→ E | A | S | Y | Q | U | E | S | T | I | O | N

开始归并（Merge）
从最小开始两两合并 
Step 1（单个 → 两个）
E + A → A E
Y + Q → Q Y
E + S → E S
I + O → I O
数组变为：
A E | S | Q Y | U | E S | T | I O | N
Step 2（继续合并）
A E + S → A E S
Q Y + U → Q U Y
E S + T → E S T
I O + N → I N O
数组变为：
A E S | Q U Y | E S T | I N O
Step 3（更大块合并）
A E S + Q U Y → A E Q S U Y
E S T + I N O → E I N O S T
数组变为：
A E Q S U Y | E I N O S T
Step 4（最终合并）
A E Q S U Y + E I N O S T
→ A E E I N O Q S S T U Y
最终结果
A E E I N O Q S S T U Y

## Quick sorting
- 定义：选一个数，把比它小的放左边，比它大的放右边，然后对左右继续做同样的事
- 假设数组：
[7, 3, 9, 2, 6]
步骤 1：选 pivot
常见选择：第一个、最后一个、中间、或随机。
这里选 7。
步骤 2：划分成左右两部分
    • 小于 7：[3, 2, 6]
    • 大于 7：[9]
步骤 3：递归排序左右
继续对 [3, 2, 6] 做快速排序，对 [9] 不用做（只有一个元素）
最终合并（不是 merge，只是左右排好了就自然连起来）
结果：
[2, 3, 6, 7, 9]
