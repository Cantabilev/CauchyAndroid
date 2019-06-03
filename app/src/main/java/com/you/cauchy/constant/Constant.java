package com.you.cauchy.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public enum  MaterialType{
        PIC,
        VIDEO,
        EXAM;

        private MaterialType(){

        }
    }

    public static Map<String, Integer> examQuestionType = new HashMap<>();
    static {
        examQuestionType.put("single", 0);
        examQuestionType.put("multiple", 1);
        examQuestionType.put("true_or_false", 2);
        examQuestionType.put("describe", 3);
    }

    public static Map<String, String> moneyCategoryMap = new HashMap<>();
    static {
        moneyCategoryMap.put("recharge", "充值");
        moneyCategoryMap.put("giving", "赠送");
        moneyCategoryMap.put("consume", "消费");
    }

}
