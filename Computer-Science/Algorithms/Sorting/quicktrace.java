/*Hoare Partition Scheme */
import java.util.Arrays;//从 Java 的标准库中导入 java.util 包里的 Arrays 类。我们之后就可以直接写Arrays.toString(a)
public class quicktrace{
    public static void main(String[] args){
        char[]a="EASYQUESTION".toCharArray();
        System.out.println("Original:"+Arrays.toString(a));
        quicksort(a);//调用快速排序方法，main不应该关心 lo、hi、depth 这些“内部细节”
        System.out.println("Sorted:" + Arrays.toString(a));
    }
    private static void quicksort(char[] a) {//提供简单入口
        quicksort(a, 0, a.length - 1, 0);
    }
    private static void quicksort(char[]a,int lo,int hi,int depth){//内部自动调用这个函数用来递归地排序数组 a 的某个部分（从索引 lo 到 hi）
        if (hi<=lo)return;
        int p=partition(a,lo,hi,depth);//调用 partition() 函数，对区间 [lo..hi] 进行 分区（partition） 操作。
        System.out.printf("Partition (%d,%d) pivot=%c -> %s%n", lo, hi, a[p], Arrays.toString(a));//打印这一轮分区的结果，用于观察排序过程。
        quicksort(a,lo,p-1,depth+1);
        quicksort(a,p+1,hi,depth+1);
    }
    private static int partition(char[] a,int lo,int hi,int depth){//把数组分成左右两部分，并返回枢轴位置
        char v=a[lo];//选择最左边的元素作为 pivot。
        int i=lo,j=hi+1;
        while(true){
            while(++i<=hi&&a[i]<v);//(左边负责找“放错位置的大元素”)跳过所有已经“正确”的元素（小的）,停在第一个“错误的元素”（≥ pivot）
            while(--j>=lo&&a[j]>v);
            if(i>=j)break;//左右指针相遇/交叉,扫描结束
            swap(a,i,j);
        }
        swap(a,lo,j);//把 pivot 从左边搬到它真正应该在的位置
        return j;
    }
    private static void swap(char[]a,int i,int j){
        char t=a[i];
             a[i]=a[j];
             a[j]=t;
    }
}
/*main
  ↓
简单接口 quicksort(a)
  ↓
真正算法 quicksort(a, lo, hi, depth)
这叫：API（接口） + 实现（implementation）分离 
也是封装（encapsulation)的含义：
把“数据 + 操作”包在一起，并隐藏内部细节，只对外提供简单接口*/