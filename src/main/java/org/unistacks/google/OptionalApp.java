package org.unistacks.google;

import org.unistacks.vo.Book;

import java.util.Optional;

/**
 * Created by Gyges on 2017/10/18
 */
public class OptionalApp {

    public static void main(String[] args) {
        Book book = new Book();
        Optional<Integer> possible = Optional.ofNullable(1);
        if (possible.isPresent()) {
            System.out.println(possible.toString());
        }
    }
}
