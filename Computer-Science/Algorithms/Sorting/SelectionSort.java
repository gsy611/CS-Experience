public class SelectionSort{
    public static void selectionSort(char[] a,int N){
        for (int i=0;i<N-1;i++){
            int min=i;
            for(int j=i+1;j<N;j++){
                if(a[j]<a[min])
                min=j;
            }
            exch(a,i,min);
        }
    }
    public static void exch(char[] a,int i,int j){
        char temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }
    public static void main(String[] args){
        char[] arr="EASYQUESTION".toCharArray();
        int N=arr.length;
        //输出排序前的数组
        System.out.print("Original:");
        for (char c:arr){
            System.out.print(c);
        }
        System.out.println();//只是负责换行
        //调用选择排序方法
        selectionSort(arr,N);
        //输出排序后的数组
        System.out.print("Sorted:");
        for (char c:arr){
            System.out.print(c);
        }
        System.out.println();
    }
}