import cn.xuyj.gis.gdal.function.DatasourceTool;
import org.gdal.gdal.gdal;
import org.gdal.ogr.ogr;
import org.junit.jupiter.api.Test;

/**
 * @Author: xuyj
 * @Date: 2024/11/12 12:32
 * @Email: 1768335576@qq.com
 * @Desc：类描述
 */
public class DataourceToolTest {
    @Test
    public void testOpenShp() {
        ogr.RegisterAll();
        //支持中文路径
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        //属性表支持中文
        gdal.SetConfigOption("SHAPE_ENCODING", "CP936");

        String shp = "E:\\work\\data\\shp\\polygon.shp";
        DatasourceTool.openShp(shp);
    }

    @Test
    public void testOpenGDB() {
        ogr.RegisterAll();
        //支持中文路径
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        //属性表支持中文
        gdal.SetConfigOption("SHAPE_ENCODING", "CP936");

        String gdb = "E:\\work\\data\\gdb\\xsd.gdb";
        DatasourceTool.openGDB(gdb);
    }
}
