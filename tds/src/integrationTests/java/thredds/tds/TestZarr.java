package thredds.tds;

import static com.google.common.truth.Truth.assertThat;

import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import org.junit.Ignore;
import org.junit.Test;
import thredds.test.util.TestOnLocalServer;

public class TestZarr {
  final private static String ZARR_DIR_PATH = "localContent/zarr/zarr_test_data.zarr";
  final private static String ZARR_ZIP_PATH = "localContent/zarr/zarr_test_data.zip";
  final private static String ZARR_S3_PATH = "s3-zarr/zarr_test_data.zarr";

  @Test
  public void shouldOpenZarrDirectory() {
    checkWithOpendap(ZARR_DIR_PATH);
  }

  @Test
  public void shouldOpenZarrZip() {
    checkWithOpendap(ZARR_ZIP_PATH);
  }

  @Ignore("Still working on S3 Zarr")
  @Test
  public void shouldOpenObjectStoreZarrFile() {
    checkWithOpendap(ZARR_S3_PATH);
  }

  private static void checkWithOpendap(String path) {
    final String endpoint = TestOnLocalServer.withHttpPath("dodsC/" + path + ".dds");
    final byte[] content = TestOnLocalServer.getContent(endpoint, HttpServletResponse.SC_OK);
    final String stringContent = new String(content, StandardCharsets.UTF_8);

    assertThat(stringContent).contains("Int32 /group_with_attrs/F_order_array[dim0 = 20][dim1 = 20];");
  }
}
