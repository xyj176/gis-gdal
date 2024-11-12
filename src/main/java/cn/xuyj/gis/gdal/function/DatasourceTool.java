package cn.xuyj.gis.gdal.function;

import cn.hutool.core.io.FileUtil;
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
        String driverName = "ESRI Shapefile";
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
        //几何类型：点、线、面
        String geomTypeName = ogr.GeometryTypeToName(layer.GetGeomType());
        System.out.println("【" + shpName + "】的几何类型：" + geomTypeName);
        //四至
        double[] extent = layer.GetExtent();
        System.out.println("四至范围：" + extent[0] + "," + extent[1] + "," + extent[2] + "," + extent[3]);
        //地块数量
        long featureCount = layer.GetFeatureCount();
        System.out.println("【" + shpName + "】有" + featureCount + "个地块");
        Feature feature;
        int featureIndex = 0;
        layer.ResetReading();
        System.out.println("遍历地块读取FID：");
        while ((feature = layer.GetNextFeature()) != null) {
            featureIndex++;
            long fid = feature.GetFID();
            System.out.println("第【" + featureIndex + "】个地块的FID：【" + fid + "】");
            Geometry geometry = feature.GetGeometryRef();
            String json = geometry.ExportToJson();
            System.out.println("第【" + featureIndex + "】个地块的GeoJson：" + json);
        }
        //坐标系
        SpatialReference spatialReference = layer.GetSpatialRef();
        String spName = spatialReference.GetName();
        System.out.println("【" + shpName + "】坐标系：" + spName);
        //属性表
        FeatureDefn featureDefn = layer.GetLayerDefn();
        int fieldCount = featureDefn.GetFieldCount();
        System.out.println("【" + shpName + "】有" + fieldCount + "个属性字段");
        for (int i = 0; i < fieldCount; i++) {
            System.out.println("\t第【" + (i + 1) + "】个字段");
            FieldDefn fieldDefn = featureDefn.GetFieldDefn(i);
            String fieldName = fieldDefn.GetName();
            String fieldTypeName = fieldDefn.GetTypeName();
            int fieldLength = fieldDefn.GetWidth();
            int fieldPrecision = fieldDefn.GetPrecision();
            System.out.println("\t字段【" + fieldName + "】");
            System.out.println("\t字段类型：【" + fieldTypeName + "】");
            System.out.println("\t字段长度：【" + fieldLength + "】");
            System.out.println("\t字段精度(小数位数)：【" + fieldPrecision + "】");
        }
    }
}
