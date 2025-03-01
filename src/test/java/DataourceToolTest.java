import cn.xuyj.gis.gdal.function.DatasourceTool;
import cn.xuyj.gis.gdal.util.InitUtil;
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

    @Test
    public void testOpenPostgresql() {
        InitUtil.init();

        String database = "test";
        String host = "localhost";
        Integer port = 5432;
        String user = "xyj";
        String password = "1234";
        String connStr = String.format("PG:dbname='%s' host='%s' port='%s' user='%s' password='%s'", database, host, port, user, password);
        DatasourceTool.openPostgresql(connStr);
    }
}
