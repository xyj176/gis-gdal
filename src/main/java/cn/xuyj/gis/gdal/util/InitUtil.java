package cn.xuyj.gis.gdal.util;

import org.gdal.gdal.gdal;
import org.gdal.ogr.ogr;

/**
 * @Author: xuyj
 * @Date: 2025/2/22 20:55
 * @Email: 1768335576@qq.com
 * @Desc：gdal初始化工具类
 */
public class InitUtil {
    /**
     * gdal初始化
     */
    public static void init() {
        ogr.RegisterAll();
        gdal.AllRegister();
        //支持中文路径
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        //属性表支持中文
        gdal.SetConfigOption("SHAPE_ENCODING", "CP936");
    }
}
