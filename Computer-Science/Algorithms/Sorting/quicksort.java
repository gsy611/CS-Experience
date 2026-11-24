public class quicksort{
    public static void quicksort(int[] arr,int left,int right){
    if(left>=right)return;
    int pivot=arr[left];
    int i=left;
    int j=right;
    while(i<j){
        while(i<j&&arr[j]>=pivot)j--;
        while(i<j&&arr[i]<=pivot)i++;
        if(i<j){
            int temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
        }
    }
    arr[left]=arr[i];
    arr[i]=pivot;//最终保证i停下来的位置，左边都 ≤ pivot，右边都 ≥ pivot，所以arr[i]天然就是pivot的正确位置
    quicksort(arr,left,i-1);
    quicksort(arr,i+1,right);
}
}