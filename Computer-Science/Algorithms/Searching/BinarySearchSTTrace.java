public class BinarySearchSTTrace {
        public static class BinarySearchST {
            private String[] keys;    // 有序数组
            private int[] vals;       // 对应值
            private int N = 0;        // 当前键值对数量
            private int compares = 0; // 比较次数统计
            public BinarySearchST(int capacity) {//构造方法定义：1.名称必须与类名相同 2.没有返回值类型，连void也不写 3.创建对象时自动执行
                keys = new String[capacity];
                vals = new int[capacity];
            }
            // rank() ：寻找 key 应该在哪里
            private int rank(String key) {//二分查找核心,是 BinarySearchST 内部实现的一部分,外部程序其实不需要知道它是如何寻找插入位置的
                int lo = 0, hi = N - 1;
                while (lo <= hi) {
                    int mid = lo + (hi - lo) / 2;
                    compares++;
                    int cmp = key.compareTo(keys[mid]);
                    if (cmp < 0) hi = mid - 1;
                    else if (cmp > 0) lo = mid + 1;
                    else return mid;
                }
                return lo;
            }
            public void put(String key, int val) {//put()：根据 rank 的结果插入或更新
                int i = rank(key);
                if (i < N && keys[i].equals(key)) {//key已经存在
                    vals[i] = val; // 更新值
                    return;
                }
                // 移动数组元素空出位置
                for (int j = N; j > i; j--) {//当 key 不存在时，要插入新元素,为了保持数组有序，需要将插入位置之后的所有元素向右移动一格。
                    keys[j] = keys[j - 1];
                    vals[j] = vals[j - 1];
                }
                keys[i] = key;//把新 key 插入到 正确的位置 i。
                vals[i] = val;//对应值存入 vals[i]。
                N++;
            }
            public void printKeys() {
                for (int i = 0; i < N; i++) {
                    System.out.print(keys[i] + " ");
                }
                System.out.println();
            }
    
            public int getCompares() {
                return compares;
            }
        }
        public static void main(String[] args) {
            String[] input = {"E","A","S","Y","Q","U","E","S","T","I","O","N"};
            BinarySearchST st = new BinarySearchST(100); //假设容量够大
            System.out.println("BinarySearchST insertion trace:");
            System.out.println("--------------------------------");
            for (String key : input) {
                st.put(key, 1);
                System.out.print("After inserting " + key + ": ");
                st.printKeys();
            }
            System.out.println("--------------------------------");
            System.out.println("Total compares = " + st.getCompares());
        }
    }
    
    
