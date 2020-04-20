package com.study.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二叉树
 *
 * 二叉树的规则
 * 1. 必定有根节点
 * 2. 比根结点小的放在根结点下子节点的左面
 * 3. 比根结点大的放在根结点下子节点的右面
 *
 * 遍历规则：
 * 1. 前序遍历: 根 - 左 - 右
 * 2. 中序遍历: 左 - 根 - 右
 * 3. 后序遍历: 右 - 根 - 左
 */
@Data
public class BinaryTree<T extends Comparable<T>> {

    private Node<T> data;

    private int size = 0;


    /**
     * 声明子节点
     * @param <T>
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Node<T extends Comparable<T>>{

        private T item;

        private Node<T> left;

        private Node<T> right;

        private Node<T> parent;

        public Node(T item , Node<T> parent){
            this.item = item;
            this.parent = parent;
        }


        /**
         * 添加节点
         * @param newNode 待添加节点
         * @return 增加的数量
         */
        public int addNode(T newNode){

            if(null == newNode)
                return 0;

            if(this.item == null){
                this.item = newNode;
                return 1;
            }

            // 小于当前节点，左边
            if(this.item.compareTo(newNode) > 0){
                if(this.left == null){
                    this.left = new Node<>(newNode , this);
                    return 1;
                }
                else
                    return this.left.addNode(newNode);
            }
            // 大于当前节点，右边
            else if(this.item.compareTo(newNode) < 0){
                if(this.right == null){
                    this.right = new Node<>(newNode , this);
                    return 1;
                }
                else
                    return this.right.addNode(newNode);
            }
            // 等于当前节点，替换
            else {
                this.item = newNode;
                return 0;
            }
        }

        /**
         * 查询是否包含数据
         * @param data 待查询的数据
         * @return true/false
         */
        public boolean contains(T data){

            if(data == null || this.item == null)
                return false;

            if(this.item.compareTo(data) == 0){
                return true;
            }

            else if(this.item.compareTo(data) > 0){
                if(this.left != null)
                    return this.left.contains(data);

                return false;
            }

            else {
                if(this.right != null)
                    return this.right.contains(data);

                return false;
            }
        }

        /**
         * 获取要删除的节点
         * @param data 待查询的节点
         * @return 查询到的节点
         */
        private Node<T> getNode(T data){

            if(data == null)
                return null;

            if(this.item.compareTo(data) == 0){
                return this;
            }
            else if(this.item.compareTo(data) > 0){
                if(this.left != null)
                    return this.left.getNode(data);
                return null;
            } else {
                if(this.right != null)
                    return this.right.getNode(data);
                return null;
            }
        }

        /**
         * 中序遍历
         * @param arrays
         * @param foot
         */
        private void toMediumArray(Object [] arrays , int foot){

            if(arrays == null)
                return;

            if(this.left != null)
                toMediumArray(arrays , foot);

            arrays[++foot] = this.item;

            if(this.right != null)
                toMediumArray(arrays , foot);
        }

    }

    /**
     * 添加元素
     * @param data 待添加元素
     * @return 已添加元素
     */
    public T addData(T data){

        if(null == data)
            return null;

        int addCount = this.data.addNode(data);
        size += addCount;

        return data;
    }


    /**
     * 判断是否包含节点
     * @param data 待查询的数据
     * @return true/false
     */
    public boolean contains(T data){

        if(data == null || this.data == null)
            return false;

        return this.data.contains(data);
    }

    /**
     * 查询节点
     * @param data 待查询的数据
     * @return 查询到的节点
     */
    public Node<T> getNode(T data){

        if(null == data || this.data == null)
            return null;

        return this.data.getNode(data);
    }

    /**
     * 删除元素
     * 注意：
     * 1. 如果待删除的节点后没有子节点，直接删除即可
     * 2. 如果待删除的节点只有一个子节点，那么删除节点后，用该节点的子节点顶替被删除的节点即可
     * 3. 如果待删除的节点有两个子节点
     *
     *
     * @param data 待删除的元素
     * @return 删除的元素
     */
    public T removeData(T data){

        if(!this.contains(data))
            return null;

        Node<T> removeNode = this.data.getNode(data);
        if(removeNode == null)
            return null;

        // 删除父节点
        Node<T> parentNode = removeNode.parent;

        // 如果待删除的节点后没有子节点
        if(removeNode.left == null && removeNode.right == null){
            removeNode.parent = null;
        }

        // 如果被删除的节点有两个子节点的
        // 如果被删除的右节点下没有左节点了，则使用被删除节点的右节点替换被删除的节点。
        // 如果被删除的右节点下仍然右左节点，一直找到最被删除节点的右节点下最后一个左节点来替换被删除节点
        else if(removeNode.left != null && removeNode.right != null){

            Node<T> moveSubNode = removeNode.right;
            while (moveSubNode.left != null)
                moveSubNode = moveSubNode.left;

            // 节点被移走了，所以要删除
            moveSubNode.parent.left = null;

            moveSubNode.left = removeNode.left;
            moveSubNode.right = removeNode.right;
            moveSubNode.parent = parentNode;

            if(parentNode != null){
                if(parentNode.item.compareTo(moveSubNode.item) > 0)
                    parentNode.left = moveSubNode;
                else
                    parentNode.right = moveSubNode;
            }
        }

        //如果只有一个节点，那么需要把节点顶到被删除节点的位置
        //如果被删除的节点没有父节点，那么子节点要顶替到父节点，父节点是没有parent的
        else {
            Node<T> moveSubNode = removeNode.left != null ? removeNode.left : removeNode.right;
            moveSubNode.parent = parentNode;
            if (parentNode != null) {
                if (parentNode.item.compareTo(moveSubNode.item) > 0)
                    parentNode.left = moveSubNode;
                else
                    parentNode.right = moveSubNode;
            }
        }

        size --;

        return data;
    }


    /**
     * 中序遍历
     * @return node集合
     */
    public Object [] toMediumArray(){

        Object [] result = new Object [size];

        this.data.toMediumArray(result , 0);

        return result;
    }


    public static void main(String[] args) {
        String a = "http://hy-oa-prod.oss-cn-hangzhou.aliyuncs.com/img/20190425194641/3333.jpeg";

        System.out.println(a.substring(a.lastIndexOf("/") + 1));
    }


}
