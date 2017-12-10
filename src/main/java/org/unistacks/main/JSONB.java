package org.unistacks.main;

import org.unistacks.vo.Book;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * @author Gyges Zean
 * @date 2017/12/9
 */
public class JSONB {

    public static void main(String[] args) {
        Book book = new Book(1, "Fun with Java", "Alex Theedom");
        Jsonb jsonb = JsonbBuilder.create();
        System.out.println(jsonb.toJson(book));
    }
}
