package com.tuyasmart.five;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


public class Store {
    Vector<T> x = new Vector<T>();

    public void add(int xx, int yy, int s) {
        T a = new T();
        a.x = xx;
        a.y = yy;
        a.score = s;
        x.add(a);
    }

    public void sort() {
        Collections.sort(x, new Comparator<Object>() {

            public int compare(Object left, Object right) {
                T l = (T) left;
                T r = (T) right;
                return r.score - l.score;
            }
        });
    }

    public void deleteAll() {
        x.clear();
    }

    public static void main(String args[]) {
        Store a = new Store();
        a.add(0, 1, 6);
        a.add(0, 2, 4);
        a.add(0, 3, 5);
        a.add(0, 4, 7);
        a.sort();
        for (T t : a.x) {
            System.out.println(t.x + " " + t.y + " " + t.score);
        }
    }

}
