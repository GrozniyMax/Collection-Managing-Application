package com.example.demo.CommonClasses.Utils;

/**
 * ����� ��� �������� ������� ���� Pair
 * @param <K> - ��� �����
 * @param <V> - ��� ��������
 */
public class Pair <K,V>{
    private final K key;
    private final V value;

    /**
     * �����������
     * @param key - ����
     * @param value - ��������
     */
    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
    /**
     * ���������� ����
     * @return ����
     */
    public K getKey() {
        return key;
    }
    /**
     * ���������� ��������
     *
     * @return ��������
     */
    public V getValue() {
        return value;
    }
}
