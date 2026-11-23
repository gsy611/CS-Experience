public class MergeSort{
    public static void sort(int[] a){
        if (a.length<=1){
            return;
        }
        int mid=a.length/2;
        //创建两个新数组
        int[] left=new int[mid];
        int[] right=new int[a.length-mid];
        for (int i=0;i<mid;i++){
            left[i]=a[i];
        }
        for(int i=mid;i<a.length;i++){
            right[i-mid]=a[i];
        }
        sort(left);
        sort(right);
        merge(a,left,right);
    }
    public static void merge(int[]a,int[]left,int[]right){
        int i=0;
        int j=0;
        int k=0;//k负责指向结果数组中下一个要填入的位置
        while(i<left.length&&j<right.length){
            if(left[i]<=right[j]){
                a[k]=left[i];
                i++;
            }
            else{
                a[k]=right[j];
                j++;
            }
            k++;
        }
        //只要左边还有剩余元素，就全部复制到 a
        while(i<left.length){
            a[k]=left[i];
            i++;
            k++;
        }
        //只要右边还有剩余元素，就全部复制到 a
        while(j<right.length){
            a[k]=right[j];
            j++;
            k++;
        }
    }
    public static void main(String[]args){
        int[] a={8,3,5,2,9,1,6,4};
        sort(a);
        for(int x:a){
            System.out.print(x+"");
        }
    }
}