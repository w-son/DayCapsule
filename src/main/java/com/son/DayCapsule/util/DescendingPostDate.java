package com.son.DayCapsule.util;

import com.son.DayCapsule.domain.Post;

import java.util.Comparator;

public class DescendingPostDate implements Comparator<Post> {
    @Override
    public int compare(Post o1, Post o2) {
        return o2.getPostDate().compareTo(o1.getPostDate());
    }
}
