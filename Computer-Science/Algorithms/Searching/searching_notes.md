# Searching

## Symbol Table（符号表）
是一种数据结构，用于存储 Key-Value Pairs<br>
每个数据由两部分组成：<br>
- Key：键，用于识别和查找数据。<br>
- Value：值，与 Key 对应的数据。<br>
## Binary search Symbol Table
BinarySearchST：使用有序数组存储，通过二分查找寻找key,二分查找（Binary Search）非常快，但它只能在“静态、有序”的数组上使用,如果符号表需要频繁插入，就必须用更高级的结构，例如二叉查找树、红黑树或散列表,但如果你的数据不需要插入，只需要查找,例如：身份证数据库、课本给的例子、词典,那么有序数组+二分查找=最快方案

## Sequential Search Symbol Table
SequentialSearchST：使用 Linked List（链表）存储，通过从头到尾逐个查找key<br>
- 它的工作方式是：<br>
1. 从第一个元素开始<br>
2. 比较当前元素的 Key 和目标 Key<br>
3. 如果相同，找到目标<br>
4. 如果不同，继续检查下一个元素<br>
5. 如果到达末尾仍然没找到，说明 Key 不存在<br>