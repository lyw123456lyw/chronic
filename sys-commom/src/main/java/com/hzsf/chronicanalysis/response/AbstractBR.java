package com.hzsf.chronicanalysis.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBR {
    private int code;
    private String msg;
}
