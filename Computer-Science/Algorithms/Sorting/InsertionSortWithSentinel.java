import java.util.Arrays;
public class InsertionSortWithSentinel {
    public static <T extends Comparable<T>> void sort(T [] a){//Comparable[]:让插入排序算法能够处理不同类型的数据，而不只是 int 或 Integer。
        int n=a.length;
        if(n<=1)return;
        // 1. 找到最小元素并放到数组最左边（哨兵）
        int minIndex=0;
        for(int i=1;i<n;i++){
            if(less(a[i],a[minIndex])){//判断当前元素 a[i] 是否比当前记录的最小值 a[minIndex] 更小
                minIndex=i;
            }
        }
        /* Comparable temp = a[0];
           a[0] = a[minIndex];
           a[minIndex] = temp;*/
        swap(a,0,minIndex);//把最小元素放到数组最左边
        // 2. 从第二个元素开始插入排序（哨兵保证不越界）
        for (int i=1;i<n;i++){
            T key=a[i];//i：外循环索引，表示当前要插入的元素
            int j=i;//j：内循环索引，用来在已排序部分找到插入位置
            while(less(key,a[j-1])){
                a[j]=a[j-1];//把左边比 key 大的元素往右移动一格,腾出空位，让 key 可以插入
                j--;//续向左扫描已排序部分
            }
            a[j]=key;
        }
    }
    //封装比较函数
    private static <T extends Comparable <T>>boolean less(T v,T w){//<T extends Comparable<T>>	声明泛型 T，并限制 T 必须实现 Comparable
        return v.compareTo(w)<0;
    }
    //封装交换数组元素的方法
    private static <T> void swap(T[] a,int i,int j){
        T temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }
    public static void main(String[] args){
        Integer[]arr={5,3,6,2,4,1};
        System.out.println("before"+Arrays.toString(arr));
        sort(arr);
        System.out.println("After:"+Arrays.toString(arr));
    }
}
