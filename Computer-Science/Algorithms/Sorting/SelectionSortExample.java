public class SelectionSortExample{
    public static void main(String[]args){
        char[] arr="EASYQUESTION".toCharArray();//把字符串转换成字符数组（char[]）
        // ['E', 'A', 'S', 'Y', 'Q', 'U', 'E', 'S', 'T', 'I', 'O', 'N']
        //String 使用双引号："ABC"；char 使用单引号：'A'
        //外层循环i,控制排序轮数，确定当前要放最小值的位置
        //内层循环j,遍历未排序的数组部分，用来找最小值
        for (int i=0;i<arr.length-1;i++){//循环到arr.length-1 即可，因为最后一个元素不用再比较
            int minIndex=i;//暂时假设当前未排序部分的第一个元素就是最小值
            for (int j=i+1;j<arr.length;j++){
                if(arr[j]<arr[minIndex]){
                    minIndex=j;
                }
            }
            //交换最小值与当前位置元素
            char temp=arr[i];
            arr[i]=arr[minIndex];
            arr[minIndex]=temp;
            System.out.print("Pass"+(i+1)+":");//显示当前是第几轮排序
            //Pass 1:
            //Pass 2: 
            for (char c:arr){
                System.out.print(c);
            }
            //输出当前数组的所有元素
            /*增强型 for 循环（for-each）语法：
            for (元素类型 元素变量名 : 集合或数组) {// 循环体}
            解释： 元素类型：集合或数组中每个元素的类型（这里是char形式）
                  元素变量名：每次循环得到的当前元素的临时变量（这里是c）
                  冒号 :：读作 “in”，意思是 “在 … 中”
                  集合或数组：要遍历的数据（这里是 arr)*/
            System.out.println("|swap"+temp+"and"+arr[i]);
            //println → 打印后换行
            /*作用：打印本轮交换了哪两个元素
            输出格式类似：|swap E and A,帮助我们跟踪选择排序的每次交换操作
            temp：原本 arr[i] 的值（外层循环当前位置的元素）
            arr[i]：经过交换后的位置 i 的新值（最小值）
            拼接成一行文字 → 显示本次交换的元素*/ 
        }
    }
}
