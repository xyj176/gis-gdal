import org.gdal.gdal.gdal;
import org.gdal.ogr.Driver;
import org.gdal.ogr.ogr;

public class Main {
    public static void main(String[] args) {
        ogr.RegisterAll();
        gdal.AllRegister();
        int vectorDriverCount = ogr.GetDriverCount();
        int rasterDriverCount = gdal.GetDriverCount();
        System.out.println("矢量数据格式：");
        for (int i = 0; i < vectorDriverCount; i++) {
            Driver driver = ogr.GetDriver(i);
            String name = driver.GetName();
            System.out.println(name);
        }
        System.out.println("栅格+矢量数据格式：");
        for (int i = 0; i < rasterDriverCount; i++) {
            org.gdal.gdal.Driver driver = gdal.GetDriver(i);
            String longName = driver.getShortName();
            System.out.println(longName);
        }
    }
}
