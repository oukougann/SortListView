package com.example.wanghong.sortlistview.util;

import com.example.wanghong.sortlistview.bean.SortModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel> {

    public int compare(SortModel o1, SortModel o2) {
        if (o1.getSortAlphabet().equals("@")
                || o2.getSortAlphabet().equals("#")) {
            return -1;
        } else if (o1.getSortAlphabet().equals("#")
                || o2.getSortAlphabet().equals("@")) {
            return 1;
        } else {
            return o1.getSortAlphabet().compareTo(o2.getSortAlphabet());
        }
    }

}
