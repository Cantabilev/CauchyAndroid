package com.you.cauchy.net.response;

import android.widget.ListView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MoneyDetailResponse {

    private BigDecimal cashAmount;
    private BigDecimal freeAmount;
    private BigDecimal accumulativeAmount;

    private List<MoneyFlowing> feeDetail;

    @Data
    public class MoneyFlowing{
        private Integer id;
        private Integer userId;
        private BigDecimal amount;
        private BigDecimal cashAmount;
        private BigDecimal freeAmount;
        private String category;
        private String source;
        private Integer courseCode;
        private Integer employeeId;
        private Date createTime;
    }
}
