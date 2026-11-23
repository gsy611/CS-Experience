public class MergeSortTrace {
    public static void main(String[] args){
        char[] a={'E','A','S','Y','Q','U','E','S','T','I','O','N'};
        sort(a,0,a.length-1);//参数 0 和 a.length-1 表示数组的起始和结束索引（整个数组）
    }
    //递归拆分方法 sort
    public static void sort(char[] a,int left,int right){//a 是待排序数组，left 和 right 表示当前处理的子数组区间 [left, right]
        if(left>=right){//终止条件
            System.out.println("sort("+left+","+right+")="+a[left]);//显示当前递归处理到哪个子数组
            return;
        }
        int mid=(left+right)/2;
        System.out.println("sort("+left+","+right+")->split:sort("+left+","+mid + "), sort(" + (mid + 1) + "," + right + ")");
        sort(a,left,mid);
        sort(a,mid+1,right);
        merge(a,left,mid,right);//递归回溯时调用 merge,作用：把左半部分和右半部分合并为有序数组
        System.out.print("merge("+left+","+mid+","+right+")=");
        printArray(a,left,right);//显示当前子数组的状态
    }
    public static void merge(char[] a, int left, int mid, int right) {
        char[] temp = new char[right - left + 1];
        int i = left, j = mid + 1, k = 0;//k 指向 temp 的当前写入位置
        /*temp 是一个临时数组，用来安全地存放合并后的元素 
        k 是临时数组的写入位置
        每次比较后，把最小的元素放到 temp[k]，不会破坏原数组未处理的元素*/
        while(i <= mid && j <= right) {
            if(a[i] <= a[j]) temp[k++] = a[i++];
            else temp[k++] = a[j++];//k++ 用于在 temp 中依次写入合并后的元素
        }
        while(i <= mid) temp[k++] = a[i++];//把 左子数组剩余的所有元素 依次放入临时数组 temp
        while(j <= right) temp[k++] = a[j++];//把 右子数组剩余的所有元素 依次放入 temp
        /*将 temp 中的合并结果回写到原数组 a 的对应位置 [left, right]。
        保持原数组更新，便于下一轮合并。 */
        for(int t = 0; t < temp.length; t++) {
            a[left + t] = temp[t];//left + t 对应原数组 [left, right] 的索引
        }
    }
    // 打印子数组
    public static void printArray(char[] a, int left, int right) {
        for(int i = left; i <= right; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }  
}
