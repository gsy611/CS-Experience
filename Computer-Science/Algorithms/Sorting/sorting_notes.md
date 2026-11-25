# 各种sorting algorithms

## Selection Sort
- Definition:Selection sort repeatedly selects the smallest (or largest) element from the unsorted part of the array and swaps it with the first unsorted element

|轮次 | i(当前要放最小值的位置)   | 未排序部分      | 找到最小值       |交换操作              | 数组状态
|---|---|---|---|---|---|
| 1     |0                     |8 4 6 2 7       |2       |'swap arr[0]<->arr[3]      |2 4 6 8 7
| 2     |1                     |4 6 8 7         |4       |'swap arr[1]<->arr[1]      |2 4 6 8 7
| 3     |2                     |6 8 7           |6       |'swap arr[2]<->arr[2]      |2 4 6 8 7
| 4     |3                     |8 7             |7       |'swap arr[3]<->arr[4]      |2 4 6 7 8
| 5     |4                     |8               |8                无                 |2 4 6 7 8

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
数组：8 5 3 7 6 2 9<br>
数组长度n = 7<br>
所以：gap = n/2 = 3<br>
第一轮 gap = 3(数组按间隔3分组)<br>
组1: 8 7 9<br>
组2: 5 6<br>
组3: 3 2<br>
组1排序（插入排序）<br>
8 7 9<br>
比较 7 和 8<br>
7 8 9<br>
组2排序<br>
5 6<br>
已经有序<br>
5 6<br>
组3排序<br>
3 2<br>
排序后<br>
2 3<br>
第一轮结束数组<br>
7 5 2 8 6 3 9<br>
第二轮 gap = 1<br>
gap = 3/2 = 1时，就变成普通插入排序。<br>
当前数组：7 5 2 8 6 3 9<br>
Pass1 插入5<br>
7 5 2 8 6 3 9<br>
↓<br>
5 7 2 8 6 3 9<br>
Pass2 插入2<br>
5 7 2 8 6 3 9<br>
↓<br>
2 5 7 8 6 3 9<br>
Pass3 插入8<br>
2 5 7 8 6 3 9<br>
不变<br>
Pass4 插入6<br>
2 5 7 8 6 3 9<br>
↓<br>
2 5 6 7 8 3 9<br>
Pass5 插入3<br>
2 5 6 7 8 3 9<br>
↓<br>
2 3 5 6 7 8 9<br>
Pass6 插入9<br>
不变<br>
最终结果:<br>
2 3 5 6 7 8 9<br>

## Merge sorting:
- 定义：将两个有序数组合并成一个更大的有序数组。这一操作直接引出了一种简单的递归排序方法，称为归并排序：对数组进行排序，将其分成两半，分别对这两半进行递归排序，然后合并结果。
- 原地归并排序（in-place merge）：
假设有数组：<br>
[8, 3, 5, 2, 9, 1]<br>
① 分（Divide）<br>
不断把数组一分为二：<br>
[8,3,5]   [2,9,1]<br>
[8] [3,5] [2] [9,1]<br>
[3] [5]   [9] [1]<br>
② 治（Conquer）<br>
每个小数组（只有1个元素）默认是有序的<br>
③ 合（Merge）关键步骤<br>
把两个有序数组合并成一个有序数组：<br>
例如：<br>
[3] + [5] → [3,5]<br>
[8] + [3,5] → [3,5,8]<br>
最终：<br>
[1,2,3,5,8,9]<br>
核心：合并操作（最重要）<br>
如何合并两个有序数组？<br>
例如：<br>
A = [3,5,8]<br>
B = [1,2,9]<br>
用两个指针比较：<br>
    • 3 和 1 → 取 1 <br>
    • 3 和 2 → 取 2 <br>
    • 3 和 9 → 取 3 <br>
    • 5 和 9 → 取 5 <br>
    • 8 和 9 → 取 8 <br>
    • 最后剩 9 <br>
结果：<br>
[1,2,3,5,8,9]<br>

口诀：<br>
左边小 → i走 <br>
右边小 → 插进去（然后 i、j 一起走）<br>

- 自顶向下的归并排序（Top-down Merge Sort）：<br>

原始数组：E A S Y Q U E S T I O N<br>

递归分解（Divide）<br>
不断对半分：<br>
E A S Y Q U E S T I O N<br>
→ E A S Y Q U | E S T I O N<br>
→ E A S | Y Q U | E S T | I O N<br>
→ E A | S | Y Q | U | E S | T | I O | N<br>
→ E | A | S | Y | Q | U | E | S | T | I | O | N<br>

开始归并（Merge）<br>
从最小开始两两合并 <br>
Step 1（单个 → 两个）<br>
E + A → A E<br>
Y + Q → Q Y<br>
E + S → E S<br>
I + O → I O<br>
数组变为：<br>
A E | S | Q Y | U | E S | T | I O | N<br>
Step 2（继续合并）<br>
A E + S → A E S<br>
Q Y + U → Q U Y<br>
E S + T → E S T<br>
I O + N → I N O<br>
数组变为：<br>
A E S | Q U Y | E S T | I N O<br>
Step 3（更大块合并）<br>
A E S + Q U Y → A E Q S U Y<br>
E S T + I N O → E I N O S T<br>
数组变为：<br>
A E Q S U Y | E I N O S T<br>
Step 4（最终合并）<br>
A E Q S U Y + E I N O S T<br>
→ A E E I N O Q S S T U Y<br>
最终结果<br>
A E E I N O Q S S T U Y<br>

## Quick sorting
- 定义：选一个数，把比它小的放左边，比它大的放右边，然后对左右继续做同样的事<br>
- 假设数组：<br>
[7, 3, 9, 2, 6]<br>
步骤 1：选 pivot<br>
常见选择：第一个、最后一个、中间、或随机。<br>
这里选 7。<br>
步骤 2：划分成左右两部分<br>
    • 小于 7：[3, 2, 6]<br>
    • 大于 7：[9]<br>
步骤 3：递归排序左右<br>
继续对 [3, 2, 6] 做快速排序，对 [9] 不用做（只有一个元素）<br>
最终合并（不是 merge，只是左右排好了就自然连起来）<br>
结果：<br>
[2, 3, 6, 7, 9]<br>
