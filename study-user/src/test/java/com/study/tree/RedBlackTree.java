package com.study.tree;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 红黑树
 * 普通的二叉树的时间复杂度为log(n),但是如果给定的对象是正序或倒序的，那么生成的二叉树就会编程一条斜线，那么这个时候连树都算不上，跟别说时间复杂度了
 * 为了满足这种情况，就出现了红黑树，及时的自我修复（均衡二叉树），避免出现斜线的发生
 *
 * 特点：
 * 1: 每个节点必须是黑色或者红色
 * 2: 根节点必须是黑色
 * 3: 每个叶子节点必定为黑色(在java中可以忽略这一点，因为java中最后一个节点如果不是黑色，那么需要在红色下补充2个null叶子成为黑色叶子节点)
 * 4: 如果一个节点是红色，那么他的子节点必定为黑色(从每个根到节点的路径上绝不会出现两个连续的红色节点，但是黑色节点是可以重复的。)
 *   4.1: 若给定的黑色节点是N个，最短路径是连续N个黑色节点，树的高度是N-1个。最长路径就是黑红相交，即2(N-1)个
 * 5: 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑色节点
 *
 *
 * 具体操作：
 * 添加：（除了根节点，剩下的节点默认插入都是红色的）
 * 1: 第一次插入，由于原树为空，只要把根节点变为黑色就行
 * 2: 如果插入节点的父节点为黑色，那么什么都不用动，直接插入就行
 * 3: 以下三种情况就要变色了或旋转了
 *   3.1: 插入节点的父节点和叔叔(就是父节点的另一个平行节点)节点均为红色
 *      将当前插入的节点的父节点、叔叔节点涂黑，将爷爷节点涂红
 *
 *   3.2: 插入节点的父节点是红色，且插入节点的叔叔节点是黑色
 *
 *   3.2.1: 插入节点的父节点是插入节点的爷爷节点的左节点，且插入节点是父节点的左节点
 *      先将当前插入的节点的父节点和祖父节点进行颜色互换，然后再进行右旋处理
 *
 *   3.2.2: 插入节点的父节点是插入节点的爷爷节点的右节点，且插入节点是父节点的右节点
 *      先将当前插入的节点的父节点和祖父节点进行颜色互换，然后再进行左旋处理
 *
 *   3.2.3: 插入节点的父节点是插入节点的爷爷节点的左节点，且插入节点是父节点的右节点
 *      先将插入节点的父节点作为支点，进行左旋。之后在执行3.2.1
 *
 *   3.2.4: 插入节点的父节点是插入节点的爷爷节点的右节点，且插入节点是父节点的左节点
 *      先将插入节点的父节点作为支点，进行右旋。之后在执行3.2.2
 *
 * 删除：
 *
 *
 *
 *  针对于替换节点是黑色而言
 *  1. 如果替换节点的父节点是黑色，且兄弟节点红色
 *
 *  2. 替换节点的父亲和兄弟都是黑色，而且没有红色的侄子
 *     将兄弟染成黑色，将指针放在父亲继续递归，一直到根
 *
 *  3. 替换节点的兄弟是黑色，且替换节点的左儿子不为空，与4冲突，先执行3后return
 *      头疼，有时间回来总结
 *
 *  4. 替换节点的兄弟是黑色，且替换节点的右儿子不为空 ，与3冲突，先执行3后return
 *
 *  5. 父亲为红色，兄弟为黑色，没有红色的侄子
 *      互换父亲和兄弟的颜色即可
 *
 *
 */
public class RedBlackTree<K extends Comparable<K> , V> {

    private static final boolean RED = true;

    private static final boolean BLACK = false;

    enum Direction{

        LEFT,
        RIGHT;

    }


    private Node<K , V> root;


    @Setter
    @Getter
    static class Node<K extends Comparable<K> , V>{

        private K key;

        private V value;

        private Node<K , V> left;

        private Node<K , V> right;

        private Node<K , V> parent;

        private boolean color;

        public Node(K key , V value , boolean color){
            this.key = key;
            this.value = value;
            this.color = color;
        }

        private void append(Node<K , V> newNode){
            if(null == newNode)
                return;

            newNode.setParent(this);
            if(this.key.compareTo(newNode.key) > 0)
                this.left = newNode;
            else if(this.key.compareTo(newNode.key) < 0)
                this.right = newNode;
            else
                this.value = newNode.value;
        }

        private void removeChild(Node<K , V> removeNode){

            if(null == removeNode)
                return;

            // 自己不能删除自己
            if(removeNode.parent == null)
                return;

            if(null != this.left && this.left.key.compareTo(removeNode.key) > 0)
                this.left = null;
            else if(null != this.right && this.right.key.compareTo(removeNode.key) < 0)
                this.right = null;

            removeNode.parent = null;
        }


        private Node<K , V> getMinNode(){

            if(this.left == null)
                return this;

            return this.left.getMinNode();
        }

        private Node<K , V> getNode(K key){

            if(null != this.left && this.key.compareTo(key) > 0)
                return this.left.getNode(key);
            else if(null != this.right && this.key.compareTo(key) < 0)
                return this.right.getNode(key);
            else
                return this;
        }

        private Node<K , V> getBrother(){

            if(this.parent == null)
                return null;

            if(this.parent.key.compareTo(this.key) > 0)
                return this.parent.right;

            return this.parent.left;
        }


        private boolean getBrotherColor(){
            Node<K , V> brotherNode = getBrother();
            if(null == brotherNode)
                return BLACK;
            return brotherNode.color;
        }

        private void setBrotherColor(boolean color){

            Node<K , V> brotherNode = getBrother();
            if(brotherNode == null)
                return;

            brotherNode.setColor(color);
        }

        private void setParentColor(boolean color){

            Node<K , V> parent = this.parent;
            if(null != parent){
                parent.setColor(color);
            }
        }

        /**
         * 获取子节点在父节点的左右侧
         * @return Direction
         */
        private Direction getParentDirection(){

            if(this.parent == null)
                return null;

            if(this.parent.key.compareTo(this.key) > 0)
                return Direction.LEFT;

            return Direction.RIGHT;
        }

        @Override
        public String toString() {

            StringBuilder stringBuilder = new StringBuilder("{ ");
            stringBuilder.append("key : ").append(key).append(" , ");
            stringBuilder.append("value : ").append(value).append(" , ");
            stringBuilder.append("parent : ").append(this.parent != null ? this.parent.key : null).append(" , ");
            stringBuilder.append("left : ").append(this.left != null ? this.left.key : null).append(" , ");
            stringBuilder.append("right : ").append(this.right != null ? this.right.key : null).append(" , ");
            stringBuilder.append("color : ").append(color);
            stringBuilder.append(" }");

            return stringBuilder.toString();
        }
    }

    /**
     * 判断节点是否是红节点
     * @param node 节点
     * @return true/false
     */
    public boolean isRed(Node node){
        if(null == node)
            return false;

        return node.color == RED;
    }


    /**
     * 判断是否是空树
     * @return true/false
     */
    public boolean isEmpty() {
        return root == null;
    }

    public V getValue(K key){

        if(null == key)
            return null;

        return getValue(this.root , key);
    }

    /**
     * 根据key获取值
     * @param node 查询的node
     * @param key key
     * @return V
     */
    private V getValue(Node<K , V> node , K key){

        if(null == node || null == key)
            return null;

        int i = 0;

        while (node != null){

            i ++;
            System.out.println(i);
            if(node.key.compareTo(key) > 0)
                node = node.left;
            else if(node.key.compareTo(key) < 0)
                node = node.right;
            else
                return node.value;
        }

        return null;
    }

    /**
     * 判断key是否存在
     * @param key key
     * @return true/false
     */
    public boolean contains(K key){

        if(null == key)
            return false;

        return getValue(this.root , key) != null;
    }


    /**
     * 插入
     * @param key Key
     * @param value 值
     */
    public void put(K key , V value){

        if(null == key)
            return;

        // 树是空的，此值是树根
        if(this.root == null){
            this.root = new Node<>(key , value , BLACK );
            return;
        }

        Node<K , V> insertParentNode = this.root.getNode(key);

        Node<K , V> insertNode = new Node<>(key , value , RED);
        insertParentNode.append(insertNode);

        fixUpInsert(insertNode);

        this.root.color = BLACK;
    }

    /**
     * 删除
     * @param key key
     */
    public void remove(K key){

        if(null == key || null == this.root)
            return;

        Node<K , V> removeNode = this.root.getNode(key);
        if(null == removeNode)
            return;

        Node<K , V> replaceNode;

        // 使用while循环，考虑到如果存在删除节点左右节点都存在的情况，使用右面节点的最小节点来代替后，发现最小的节点下还有右节点，所以也要去替换
        while (null != removeNode.left || null != removeNode.right){

            // 如果删除节点的左节点为空，那么删除节点的右节点一定为空
            // 如果删除节点是红色，那么删除节点的左节点一定不可能为空，否则就违背了红黑树的性质(跟路径到叶子节点的黑色节点一致)

            // 如果删除节点有左右节点，那么就寻找右节点下的最小节点
            // 否则左右节点不全都存在的情况下，使用存在的子节点来代替自己
            if(null == removeNode.left)
                replaceNode = removeNode.right;
            else if(null == removeNode.right)
                replaceNode = removeNode.left;
            else
                replaceNode = removeNode.right.getMinNode();

            removeNode.key = replaceNode.key;
            removeNode.value = replaceNode.value;

            removeNode = replaceNode;
        }

        // 双黑调整
        if(!removeNode.color){
            fixUpRemove(removeNode);
        }

        // 如果删除节点存在父节点
        if(null != removeNode.parent){

            if(removeNode.parent.key.compareTo(removeNode.key) > 0)
                removeNode.parent.left = null;
            else
                removeNode.parent.right = null;

        } else {
            this.root = null;
        }

    }


    /**
     * 添加节点后修复树
     * @param node 添加的节点
     */
    private void fixUpInsert(Node<K , V> node){

        // 只有当插入节点不是父节点且插入节点的父节点是红色时才需要修复
        while (null != node.parent && node.parent.color == RED){

            Node<K , V> parent = node.parent;                   // 父亲节点
            Node<K , V> grad = node.parent.parent;              // 爷爷节点


            // 如果插入节点的叔叔节点也是红色，那么只需要变色即可
            // 将父亲和叔叔都变成黑色，将爷爷变成红色
            // 再递归爷爷，防止爷爷出现双红
            if(parent.getBrotherColor() == RED){

                node.parent.color = BLACK;
                node.parent.setBrotherColor(BLACK);
                node.parent.setParentColor(RED);
                node = node.parent.parent;
            }
            // 插入节点的叔叔节点不是红色
            // 1. 如果插入节点的父节点在插入节点的的爷爷节点的左侧，且插入节点在插入节点的父节点的左侧
            else {

                // 如果父亲节点在爷爷节点的左面
                if(grad.key.compareTo(parent.key) > 0){

                    // 如果插入节点在父亲节点的左面
                    if(parent.key.compareTo(node.key) > 0){

                        if(grad.key.compareTo(this.root.key) == 0)
                            this.root = parent;

                        // 对插入节点的爷爷节点进行右旋，并且互换颜色
                        rightRotate(grad);
                        parent.color = BLACK;
                        grad.color = RED;

                    }else {

                        if(grad.key.compareTo(this.root.key) == 0)
                            this.root = node;

                        leftRotate(parent);
                        rightRotate(grad);
                        // 调换颜色 ， 因为在左旋过程中，原父亲和儿子发生了父子颠倒，所以现在的父亲就插入节点的父亲
                        node.color = BLACK;
                        grad.color = RED;
                    }

                } else{

                    // 如果插入节点在父亲节点的左面
                    if(parent.key.compareTo(node.key) < 0){

                        if(grad.key.compareTo(this.root.key) == 0)
                            this.root = parent;

                        // 对插入节点的爷爷节点进行右旋，并且互换颜色
                        leftRotate(grad);
                        parent.color = BLACK;
                        grad.color = RED;

                    }else {

                        if(grad.key.compareTo(this.root.key) == 0)
                            this.root = parent;

                        rightRotate(parent);
                        leftRotate(grad);
                        // 调换颜色 ， 因为在左旋过程中，原父亲和儿子发生了父子颠倒，所以现在的父亲就插入节点的父亲
                        node.color = BLACK;
                        grad.color = RED;
                    }

                }
                return;
            }
        }

        this.root.color = BLACK;

    }




    /**
     * 修复删除
     * @param realNode 实际被删除的节点
     *                 因为理论被删除的节点，被替换节点所替换，那么最后一个替换的就是实际删除的节点，只需要对这个节点进行失黑修复就好了
     */
    private void fixUpRemove(Node<K , V> realNode){

        // 如果被删除节点是父节点，积极什么都不用做了
        while (realNode.parent != null){

            Node<K , V> parent = realNode.parent;
            Node<K , V> brother = realNode.getBrother();


            // 判断兄弟是红色，如果兄弟节点为红色，那么父节点一定为黑色，且兄弟节点一定有两个子节点，且均为黑色
            // 1。交换父亲和兄弟的颜色
            // 2. 此时根据实际删除节点和其父亲节点的位置，对父亲节点进行左旋/右旋
            if(null != brother && brother.color == RED){

                brother.color = BLACK;
                parent.color = RED;

                // 防止父亲就是根结点，那么父亲下去了，兄弟就要上位了
                if(parent.key.compareTo(root.key) == 0)
                    this.root = brother;

                if(parent.key.compareTo(realNode.key) > 0)
                    leftRotate(parent);
                else
                    rightRotate(parent);

                // 重新定义删除节点的父亲和兄弟

                parent = realNode.parent;
                brother = realNode.getBrother();

            }

            // 判断兄弟是黑色，且兄弟有左儿子，且为红色
            // 此时的情况也正好接情况1，单独删除也可能造成这种情况
            // 这种情况比较特殊，根据实际删除节点和其父节点所在位置的不同处理方式也不同
            // 如果被删除节点在父亲节点的左面，那么父亲节点、兄弟节点、兄弟的左儿子就不是一条直线，此时需要旋转2次，对兄弟进行右旋，最父亲进行左旋。让侄子当父亲
            // 如果被删除节点在父亲节点的右，那么父亲节点、兄弟节点、兄弟的左儿子就是一条直线，此时需要旋转1次，对父亲进行右旋。让兄弟当父亲
            // 最后将新替换的父亲，换上原来父亲的颜色
            if(null != brother && null != brother.left && brother.left.color == RED){
                boolean oldParentColor = parent.color;
                parent.color = BLACK;

                if(parent.key.compareTo(realNode.key) > 0){
                    if(parent.parent == null)
                        this.root = brother.left;
                    rightRotate(brother);
                    leftRotate(parent);
                }else {
                    brother.left.color = BLACK;
                    if(parent.parent == null)
                        this.root = brother;
                    rightRotate(parent);
                }

                parent.parent.color = oldParentColor;

                return;
            }
            // 判断兄弟是黑色，且兄弟的右儿子也是黑色
            // 这种情况与上一种情况处理方式相同，只是方向发生了变化
            if(null != brother && null != brother.right && brother.right.color == RED){
                boolean oldParentColor = parent.color;
                parent.color = BLACK;

                if(parent.key.compareTo(realNode.key) > 0){
                    brother.right.color = BLACK;
                    if(parent.parent == null)
                        this.root = brother;
                    leftRotate(parent);
                }else {
                    if(parent.parent == null)
                        this.root = brother.right;
                    leftRotate(brother);
                    rightRotate(parent);
                }

                parent.parent.color = oldParentColor;
                return;
            }

            // 如果父亲的颜色是红色，那么一定有兄弟，且兄弟颜色一定为黑色
            if(null != brother && parent.color == RED){
                parent.color = BLACK;
                brother.color = RED;
                return;
            }

            // 这种情况是递归
            else if(null != brother && parent.color == BLACK) {
                brother.color = RED;
                realNode = parent;
            }





        }






    }




    /**
     * 节点替换他的上级节点
     * @param target 被替换节点
     * @param source 替换节点
     */
    private void replaceUpNode(Node<K , V> target , Node<K , V> source){

        if(null == target || null == source)
            return;

        // 万一你的左右节点替换你，那替换你的人是没有左右节点的
        source.parent = target.parent;
        if(null != target.right && target.right.key.compareTo(source.key) == 0){
            source.left = target.left;
        }else if(null != target.left && target.left.key.compareTo(source.key) == 0){
            source.right = target.right;
        }else {
            source.left = target.left;
            source.right = target.right;
        }

        if(null != target.parent)
            target.parent.append(source);
    }



    /**
     * 左旋
     * @param node 旋转支点
     */
    private void leftRotate(Node<K , V> node){

        Node<K , V> right = node.right;

        node.right = right.left;
        if(node.right != null)
            node.right.parent = node;

        right.parent = node.parent;

        if(node.parent == null)
            this.root = right;
        else
            node.parent.append(right);

        right.left = node;
        node.parent = right;

    }

    /**
     * 右旋
     * @param node 旋转支点
     *
     *         50                                   30
     *        /  \                                 /  \
     *      30   70     已50为支点进行右旋         10    50
     *     /  \                                       /  \
     *    10  35                                     35  70
     *
     */
    private void rightRotate(Node<K , V> node){

        Node<K , V> left = node.left;

        // 支点的左节点(left) < 支点
        // 支点的左节点(left) < left的右节点 < 支点
        // 那么旋转后，left的右节点就在支点的左面了
        node.left = left.right;
        if(null != left.right)
            node.left.parent = node;

        // 因为左节点顶替了支点，所以支点的父节点就指向了左节点
        left.parent = node.parent;


        // 表示root就是根节点了
        if(node.parent == null)
            this.root = left;
        else
            node.parent.append(left);

        left.right = node;
        node.parent = left;
    }


    // 中序遍历
    public void printMiddenTree(){

        List<Node<K , V>> list = new ArrayList<>();
        recurse(this.root , list);
        list.forEach(System.out :: println);


    }


    private void recurse(Node<K , V> node , List<Node<K , V>> result){

        if(node == null)
            return;

        recurse(node.left , result);
        result.add(node);
        recurse(node.right , result);

    }


    public static void main(String[] args) {

        String rbtStr = "10,20,30,40,50,60,70,80,90,100,110,120,130,140,125";

        RedBlackTree<Integer , String> rbTree = new RedBlackTree<>();
        for (String key : rbtStr.split(",")){
            if(key.equals("80"))
                System.out.println(key);

            rbTree.put(Integer.parseInt(key) , key);
            System.out.println(key);
        }

        rbTree.remove(110);

        rbTree.printMiddenTree();
    }






}
