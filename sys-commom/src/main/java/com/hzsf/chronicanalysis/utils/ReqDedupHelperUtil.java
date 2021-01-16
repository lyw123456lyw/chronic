package com.hzsf.chronicanalysis.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * 接口幂等设计思路(只针对请求参数是JSON的接口)：
 *  那么当同一个用户访问同一个接口，带着同样的reqParam过来，我们就可以判断他是重复的请求了。但是接口中如果带有时间戳格式或者经纬度等信息
 *  就不能判断他是一个重复的请求，但实际上他还是同一个请求。所以需要对请求参数去重。然后将参数转换成一个md5格式的密文就减少了那么长的key
 *  计算请求参数的摘要作为参数标识
 *      用户ID:接口名:请求参数 用户ID是唯一的+接口名字+请求参数的内容(内容是JSON格式的参数)
 */
@Slf4j
public class ReqDedupHelperUtil {


    /**
     *
     * @param reqJSON 请求的参数，这里通常是JSON
     * @param excludeKeys 请求参数里面要去除哪些字段再求摘要
     * @return 去除参数的MD5摘要
     */
    public static String dedupParamMD5(final String reqJSON, String... excludeKeys) {
        String decreptParam = reqJSON;
        TreeMap paramTreeMap = JSONObject.parseObject(decreptParam, TreeMap.class);
        if (excludeKeys!=null) {
            List<String> dedupExcludeKeys = Arrays.asList(excludeKeys);
            if (!dedupExcludeKeys.isEmpty()) {
                for (String dedupExcludeKey : dedupExcludeKeys) {
                    paramTreeMap.remove(dedupExcludeKey);
                }
            }
        }

        String paramTreeMapJSON = JSONObject.toJSONString(paramTreeMap);
        String md5deDupParam = jdkMD5(paramTreeMapJSON);
        log.debug("md5deDupParam = {}, excludeKeys = {} {}", md5deDupParam, Arrays.deepToString(excludeKeys), paramTreeMapJSON);
        return md5deDupParam;
    }

    public static String jdkMD5(String src) {
        String res = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] mdBytes = messageDigest.digest(src.getBytes());
            res = DatatypeConverter.printHexBinary(mdBytes);
        } catch (Exception e) {
            log.error("",e);
        }
        return res;
    }
}
