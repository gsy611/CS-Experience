public class InsertionSortExample{
    public static void main(String[] args){
        char[]arr="EASYQUESTION".toCharArray();
        for(int i=1;i<arr.length;i++){
            char key=arr[i];//i=当前要插入的元素位置;key = 当前要插入的元素
            int j=i-1;//j = 已排序部分的最后一个元素索引
            //从右向左扫描已排序部分，找到插入位置
            /*数组：[E, A, S, Y]，i=1（第二个元素A）
              已排序部分=[E]
              j=i-1=0 → 指向E
              比较arr[j] (E) 和 key (A)：
              因为E>A → 右移E → 插入A */
            while (j>=0 && arr[j]>key){
                arr[j+1]=arr[j];//元素右移，为 key 腾出位置
                j--;
            }
            arr[j+1]=key;//插入 key 到正确位置
            System.out.print("Pass"+i+":");
            for(char c:arr){
                System.out.print(c+"");
            }
            System.out.println("|insert"+key);
        }      
    }   
} 