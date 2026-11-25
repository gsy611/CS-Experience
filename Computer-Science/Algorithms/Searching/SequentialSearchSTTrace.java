public class SequentialSearchSTTrace{
        private static class Node{//定义链表节点
            String key;
            int val;
            Node next;//指向下一个节点的引用（链表连接）
            Node(String key,int val,Node next){//这是 Node 的构造方法，用来创建节点
                this.key=key;//将传入的 key 存入节点
                this.val=val;//存入值
                this.next=next;//将新节点的 next 指向传入的 next
            }
        }
        public static class SequentialSearchST{//符号表实现
            private Node first=null;
            private int compares=0;
            public void put(String key,int val){//把一个 (key, val) 放入符号表。
                for(Node x=first;x!=null;x=x.next){//从 first 开始顺序扫描链表，每次访问下一个节点直到链表末端（null）
                    compares++;//每访问一个节点，表示要比较 key 与 x.key，因此把 compares 加 1
                    if (x.key.equals(key)){//如果找到key
                        x.val=val;
                        return;
                    }
                }
                first=new Node(key,val,first);//如果没找到key,头插法：创建新节点，next 指向旧的 first，并把 first 更新为新节点。
            }
            public int getCompares(){
                return compares;//返回总比较次数
            }
            public void printList(){//打印当前链表,从 first 开始遍历输出每个节点的 key，用空格分隔，最后换行
                Node x=first;
                while(x!=null){
                    System.out.print(x.key+" ");
                    x=x.next;
                }
                System.out.println();
            }
        }
        public static void main(String[]args){
            SequentialSearchST st=new SequentialSearchST();
            String[] keys = {"E","A","S","Y","Q","U","E","S","T","I","O","N"};
            System.out.println("Insertion trace for SequentialSearchST:");
            System.out.println("----------------------------------------");
            for (String k : keys) {//从 keys 数组中依次取出一个 String 类型的元素，并将它暂时存放在变量 k 中
                st.put(k,1);
                System.out.print("After inserting " + k + ": ");
                st.printList();
            }
            System.out.println("----------------------------------------");
            System.out.println("Total compares = " + st.getCompares());
        }
}
