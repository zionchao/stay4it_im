package com.kevin.im.util;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class TextUtil {
    public static boolean isValidate(String content) {
        if (content != null && !"".equals(content.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isValidate(String... contents) {
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null || "".equals(contents[i].trim())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidate(ArrayList<?> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
