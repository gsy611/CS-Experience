//最大堆（Max Heap）就是用来“快速找到并移除最大元素”的数据结构
//用数组表示完全二叉树可通过索引快速找到父子：父 = k/2，子 = 2k、2k+1，省去指针开销。
public class maxPQ<Key extends Comparable<Key>> {//Key 这个类型必须可以比较大小。
    private Key[] pq;     // 这里不用 char[]，而是用 Key[]，所以它可以存不同类型的数据。
    private int N = 0;    // N:当前堆的元素数量
    // 构造：初始化容量
    public maxPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
    }
    public boolean isEmpty() {//常用于客户端检查或循环条件。
        return N == 0;
    }
    public int size() {
        return N;
    }
    // 插入元素 v
    public void insert(Key v) {//定义插入方法，插入一个元素 v
        pq[++N] = v;   // 先让 N + 1，再把 v 放到最后一个位置。
        swim(N);       // 上浮恢复堆序
    }
    // 删除最大值（根节点）
    public Key delMax() {
        Key max = pq[1];     // 根即最大,先把最大值保存下来。
        exch(1, N--);        // 与最后一个交换
        pq[N + 1] = null;    // 把原来最后那个位置清空。防止“游离引用”（loitering）
        sink(1);             // 把新的根节点下沉,因为刚刚换上来的元素可能太小，不符合最大堆规则。
        return max;
    }
    // ---------- 辅助方法 ----------
    // 比较 pq[i] < pq[j] ?
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }
    // 交换 pq[i] 和 pq[j]
    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }
    // 上浮：如果 k 比父节点大，则交换
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) { //less(k / 2, k)意思是：父节点 < 当前节点
            exch(k, k / 2);
            k = k / 2;  // 移动到父节点
        }
    }
    // 下沉：k 与两个子中较大的比较；若子较大则下沉
    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;        // 左子
            if (j < N && less(j, j + 1)) j++; // 选较大的子节点
            if (!less(k, j)) break;           // 若父 ≥ 子，结束
            exch(k, j);
            k = j;                 // 移动到子节点
        }
    }
}

    

