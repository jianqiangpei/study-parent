package com.study.core.common;

import java.util.Arrays;

/**
 * 8种排序
 */
public class SortUtils {


    public static void main(String[] args) {

//        int [] bubbleArray  = new int [] {(1 << 31) -1 , (1 << 31) - 2};
//        SortUtils.bubbleSort(bubbleArray);


        int [] fastArray = new int [] {1 , 9 , 8 , 31 , 10 , 66 , 38 , 98 , 22 , 71 , 99};
//        fastSort(fastArray , 0 , fastArray.length -1);



//        insertSort(fastArray);

//        shellSort(fastArray);

//        selectSort(fastArray);

        mergeSort(fastArray);

        System.out.println(Arrays.toString(fastArray));

    }


    /**
     * 冒泡排序
     * 核心思想
     * 1. 循环length-1次元素（因为最后一个元素一定是最小的，不用循环，所以只需要循环length-1次）
     * 2. 再循环内嵌套循环，比较第二个循环内相邻的2个元素，一直比较完
     * 时间复杂读O(n²)
     */
    public static void bubbleSort(int [] array){

        if(null == array || array.length == 0)
            return;

        int length = array.length;

        for(int i = 0; i < length -1; i++){
            for(int j = 0; j < length -1; j++){
                if(array[j] > array[j + 1]){
                    // 满足条件时交换值，将小的放在前面。

                    // 下面这种方法使用的是临时变量交换顺序，也是最常用的方法，因为简单容易理解
//                    int tempData = array[j + 1];
//                    array[j + 1] = array[j];
//                    array[j] = tempData;

                    // 下面这种方法是算数法
                    // 简单来说就是给个总值，然后去减。但是并不建议这么写。理由是增加cpu运算，其二，也是最重要的，如果想加之后超过int的最大限制呢？？？
//                    array[j] = array[j] + array[j + 1];
//                    array[j + 1] = array[j] - array[j + 1];
//                    array[j] = array[j] - array[j + 1];

                    // 下面这种方法是位运算，经常会在hashMap中等jdk源码中看到这么做，通过二进制的位运算进行匹配。这种方法运算快，也不会出现超限尴尬情况
                    array[j] = array[j] ^ array[j + 1];
                    array[j + 1] = array[j] ^ array[j + 1];
                    array[j] = array[j] ^ array[j + 1];
                }
            }
        }

        System.out.println(Arrays.toString(array));
    }


    /**
     * 快速排序
     * 快速排序是机遇冒泡排序，对冒泡排序的一种改进，借用了分治的思想。
     * 通过一次排序将排序的数据分割为独立的两部分，其中一步法的所有数据都比另一部分的所有数据小，然后再按此方法对两部分数据分别进行快速排序，整个排序过程可以递归进行，以此外达到整个数据变为有序序列
     *
     * 1. 假设有一个无序数组，那么把第0个位置（这个位置不固定，可以是数组长度内的任意位置）记录为临时变量，起个名字：坑
     * 2. 从后往前遍历，遇到第一个小于等于临时变量的值，上一个坑用这个值填平，并将这个值挖空，这是新坑
     * 3. 从前往后遍历，遍历的长度为上一个坑的位置，遇到第一个小于等于临时变量的值，将第二步的坑填平，填的值就是第三步遇到的这个值
     * 4. 将第三步挖的坑填平，用第一步的临时变量填。
     * 5. 方法结束。剩下的就是递归，以坑的位置（不包括坑），左右进行重复上述方法，知道排序完成
     *
     * 平均时间复杂度为O(logn),如果遇到极限情况，也是O(n²)，例如恰巧了，每一步都需要移动
     * @param array
     */
    public static void fastSort(int [] array , int start , int end){

        if(null == array || array.length == 0)
            return;

        if(end <= start)
            return;

//        if(start >= end || start < 0 || end >= array.length)
//            return;

        int low = start;
        int high = end;
        int temp = array[low];

        while (low < high){

            while (low < high && array[high] >= temp)
                high --;

            array[low] = array[high];

            while (low < high && array[low] <= temp)
                low ++;

            array[high] = array[low];
        }

        array[low] = temp;
        fastSort(array , start , low -1);
        fastSort(array , low + 1 , end);
    }


    /**
     * 直接插入排序
     * 将数组中的元素从前往后，从第1个开始，一次与前一个（第一个的前一个是第0个）比较，如果比前一个小，那么就交换元素，知道完成
     *
     * 1. 从第0个元素开始，将第0个元素认为是已经排好序的，记录下第0个元素，作为参考值
     * 2. 从第0个元素后一个开始，一次对前一个元素进行比较，如果比之前的元素小，那么就交换位置
     * @param array
     */
    public static void insertSort(int [] array){

        if(null == array || array.length == 0)
            return;

        for(int i = 1 ; i < array.length ; i++){
            int j = i - 1;
            while (j >= 0 && array[j] > array[j + 1]){
                int temp = array[j];
                array[j] =  array[j + 1];
                array[j + 1] = temp;
                j --;
            }

        }
    }


    /**
     * 希尔排序
     * 希尔排序是插入排序的升级版，高速且稳定，但他从另一角度来说也是最不稳定的排序，因为他的时间复杂度难以琢磨
     *
     * 希尔排序是借助步长的方式进行排序，假设有一个数组长度为11，步长默认为11/2向下取整。这样就变成1 -> 1+ 5 , 2 -> 2 +5 ， 依次类推, 5 -> 5 + 5，对这行进行对应的元素进行插入排序， 剩余最后一个先不管
     * 此时再对上次的步长进行/2向下取整，也就是5/2=2。再根据这次的步长对第一次排序的数组进行对应插入排序，再次根据这次的步长/2向下取整排序。直到步长为1的时候，完成了数组最终的排序
     * @param array
     */
    public static void shellSort(int [] array){

        if(null == array || array.length == 0)
            return;

        int gap = array.length / 2;

        for(; gap > 0 ; gap = gap / 2){

            for(int j = 0 ; (j + gap) < array.length ; j++){

                for(int k = 0 ; (k + gap) < array.length ; k += gap){

                    if(array[k] > array[k + gap]){
                        array[k] = array[k] + array[k + gap];
                        array[k + gap] = array[k] - array[k + gap];
                        array[k] = array[k] - array[k + gap];
                    }
                }
            }
        }
    }


    /**
     * 选择排序
     * 在未排序序列中找到最小（大）的元素，存放在未排序序列的起始位置。
     * @param array
     */
    public static void selectSort(int [] array){

        if(null == array || array.length == 0)
            return;

        for(int i = 0 ; i < array.length - 1; i ++){

            int min = i;

            for(int j = i + 1 ; j < array.length ; j ++){
                if(array[j] < array[min])
                    min = j;
            }

            if(min != 1 && i != min){
                array[min] = array[i] + array[min];
                array[i] = array[min] - array[i];
                array[min] = array[min] - array[i];
            }
        }
    }

    /**
     * 归并排序
     * 将一个长度为l的数组，按照n/2向下取整的方式分成2份，如果分割后的数组长度仍然大于2，继续分割，直到分割到长度<=2为止。
     * 将分割到最小单元的数组进行排序，将两个合并为1个数组，直到最后合并为最终的数组
     * @param array
     */
    public static void mergeSort(int [] array){

        if(null == array || array.length == 0)
            return;

        mergeSort(array , 0 , array.length - 1);


    }


    private static void mergeSort(int [] array , int low , int high){

        if(null == array || array.length == 0)
            return;

        if(low >= high)
            return;

        int mind = (low + high) / 2;
//                low + (high - low >> 2);

        // 递归将中间左右的进行再次分割
        mergeSort(array , low , mind);

        mergeSort(array , mind + 1 , high);

        // 左右归并
        merge(array , low , mind , high);


    }


    private static void merge(int [] array , int low , int mid , int high){

        // 申请一个临时的数组
        int [] temp = new int[array.length];

        int i = low;
        int k = 0;
        int j = mid + 1;

        while (i <= mid && j <= high){

            if(array[i] <= array[j])
                temp[k++] = array[i++];
            else
                temp[k++] = array[j++];
        }

        while (i <= mid)
            temp[k++] = array[i++];

        while (j <= high)
            temp[k++] = array[j++];

        for (int m = 0 ; m < k ; m ++)
            array[m + low] = temp[m];

    }









}
