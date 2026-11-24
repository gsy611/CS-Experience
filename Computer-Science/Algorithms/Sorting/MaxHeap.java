/*最大堆特点：父节点的值 >= 子节点的值*/
public class MaxHeap{
    private char[] heap;//用数组保存整个堆
    private int size;
    public MaxHeap(int capacity){//构造方法
        heap=new char[capacity+1];
        size=0;
    }
    public void insert(char value){
        heap[++size]=value;//++size是前置自增，他让size先加1,然后使用新的size作为数组索引
        swim(size);//让新插入的元素“上浮”,因为新元素刚放在最后，可能比父节点大，所以要往上调整
    }
    private void swim(int k){//定义上浮方法。
        while(k>1&&heap[k]>heap[k/2]){
            swap(k,k/2);
            k=k/2;
        }
    }
    private void swap(int i, int j) {//定义交换方法。
        char tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
    public void printHeap(){
        for (int i=1;i<=size;i++){
            System.out.print(heap[i]+"");
        }
        System.out.println();//打印完之后换行。
    }
    public static void main(String[] args) {
        MaxHeap heap = new MaxHeap(20);//创建对象
        char[] keys = {'E','A','S','Y','Q','U','E','S','T','I','O','N'};
        for (char c : keys) {
            heap.insert(c);
        }
        System.out.println("Final Max Heap (level-order):");
        heap.printHeap();
    }
}