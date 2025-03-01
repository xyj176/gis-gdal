package cn.xuyj.gis.gdal.function;

import cn.hutool.core.io.FileUtil;
import cn.xuyj.gis.gdal.config.SystemConstant;
import org.gdal.ogr.*;
import org.gdal.osr.SpatialReference;

/**
 * @Author: xuyj
 * @Date: 2024/11/12 12:17
 * @Email: 1768335576@qq.com
 * @Desc：数据源工具类
 */
public class DatasourceTool {
    /**
     * 读取shp数据（只读）
     *
     * @param shp
     */
    public static void openShp(String shp) {
        String driverName = SystemConstant.Driver.SHP;
        Driver driver = ogr.GetDriverByName(driverName);
        if (driver == null)
            throw new RuntimeException(driverName + "驱动不可用！");
        //只读打开
        String shpName = FileUtil.getName(shp);
        DataSource dataSource = ogr.Open(shp, 0);
        if (dataSource == null)
            throw new RuntimeException("打开【" + shpName + "】失败！");
        //读取图层
        Layer layer = dataSource.GetLayer(0);
        if (layer == null)
            throw new RuntimeException("获取【" + shpName + "】图层失败！");
        openLayer(layer);
    }

    /**
     * 读取gdb（只读）
     *
     * @param gdb
     */
    public static void openGDB(String gdb) {
        String driverName = SystemConstant.Driver.GDB;
        Driver driver = ogr.GetDriverByName(driverName);
        if (driver == null)
            throw new RuntimeException(driverName + "驱动不可用!");

        DataSource dataSource = driver.Open(gdb, 0);
        String gdbName = FileUtil.getName(gdb);
        if (dataSource == null)
            throw new RuntimeException("【" + gdbName + "】打开失败!");
        int layerCount = dataSource.GetLayerCount();
        System.out.println("【" + gdbName + "】图层数量：" + layerCount + "个");
        for (int i = 0; i < layerCount; i++) {
            Layer layer = dataSource.GetLayer(i);
            openLayer(layer);
        }
    }

    public static void openPostgresql(String connStr) {
        String postgresql = SystemConstant.Driver.POSTGRESQL;
        Driver driver = getDriver(postgresql);
        DataSource ds = driver.Open(connStr, 0);
        Layer layer = ds.GetLayer(0);
        openLayer(layer);

    }

    public static void openLayer(Layer layer) {
        String name = layer.GetName();
        System.out.println("\n******************【" + name + "】基本信息******************");
        //几何类型：点、线、面
        String geomTypeName = ogr.GeometryTypeToName(layer.GetGeomType());
        System.out.println("几何类型：" + geomTypeName);
        //坐标系
        SpatialReference spatialReference = layer.GetSpatialRef();
        String spName = spatialReference.GetName();
        System.out.println("坐标系：" + spName);
        //四至
        double[] extent = layer.GetExtent();
        System.out.println("四至范围：" + extent[0] + "," + extent[1] + "," + extent[2] + "," + extent[3]);

        System.out.println("\n********************【" + name + "】属性表信息********************");
        //属性表
        FeatureDefn featureDefn = layer.GetLayerDefn();
        int fieldCount = featureDefn.GetFieldCount();
        System.out.println("字段数量：" + fieldCount);
        for (int i = 0; i < fieldCount; i++) {
            System.out.println("第【" + (i + 1) + "】个字段");
            FieldDefn fieldDefn = featureDefn.GetFieldDefn(i);
            String fieldName = fieldDefn.GetName();
            String fieldTypeName = fieldDefn.GetTypeName();
            int fieldLength = fieldDefn.GetWidth();
            int fieldPrecision = fieldDefn.GetPrecision();
            System.out.println("\t字段名【" + fieldName + "】");
            System.out.println("\t字段类型：【" + fieldTypeName + "】");
            System.out.println("\t字段长度：【" + fieldLength + "】");
            System.out.println("\t字段精度(小数位数)：【" + fieldPrecision + "】");
        }

        System.out.println("\n********************【" + name + "】地块信息********************");
        //地块数量
        long featureCount = layer.GetFeatureCount();
        System.out.println("地块数量：" + featureCount);
        Feature feature;
        int featureIndex = 0;
        layer.ResetReading();
        while ((feature = layer.GetNextFeature()) != null) {
            featureIndex++;
            long fid = feature.GetFID();
            System.out.println("\t第【" + featureIndex + "】个地块的FID：【" + fid + "】");
            for (int i = 0; i < fieldCount; i++) {
                FieldDefn fieldDefn = featureDefn.GetFieldDefn(i);
                String fieldName = fieldDefn.GetName();
                String fieldValue = feature.GetFieldAsString(fieldName);
                System.out.println("\t第【" + featureIndex + "】个地块的字段【" + fieldName + "】的值为：" + fieldValue);
            }

            Geometry geometry = feature.GetGeometryRef();
            String json = geometry.ExportToJson();
            System.out.println("\t第【" + featureIndex + "】个地块的GeoJson：" + json);
        }
    }

    private static Driver getDriver(String driverName) {
        Driver driver = ogr.GetDriverByName(driverName);
        if (driver == null)
            throw new RuntimeException(driverName + "驱动不可用!");
        return driver;
    }
}
