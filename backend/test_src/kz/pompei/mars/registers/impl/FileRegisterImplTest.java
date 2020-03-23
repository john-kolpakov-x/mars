package kz.pompei.mars.registers.impl;

import kz.pompei.mars.database.PostgresConnect;
import kz.pompei.mars.registers.FileRegister;
import kz.pompei.mars.registers.model.FileMeta;
import kz.pompei.mars.util.Ids;
import kz.pompei.mars.util.ParentTestNg;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class FileRegisterImplTest extends ParentTestNg {

  @Autowired
  private FileRegister fileRegister;

  @Autowired
  private PostgresConnect postgresConnect;

  public static byte[] subArray(byte[] array, int offset, int count) {
    if (offset >= array.length) return new byte[0];

    if (offset + count >= array.length) {
      count = array.length - offset;
    }

    var ret = new byte[count];
    System.arraycopy(array, offset, ret, 0, count);
    return ret;
  }

  @Test
  public void uploadFile() throws Exception {

    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);

    ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);

    String fileId = Ids.generate();
    String fileName = Ids.str(10);
    String mimeType = Ids.str(10);

    //
    //
    fileRegister.uploadFile(fileId, fileName, mimeType, 100, inputStream);
    //
    //

    try (Connection con = postgresConnect.getDataSource().getConnection()) {

      try (PreparedStatement ps = con.prepareStatement("select * from file_meta where id = ?")) {

        ps.setString(1, fileId);

        try (ResultSet rs = ps.executeQuery()) {

          if (!rs.next()) {
            throw new RuntimeException("No file_meta with file id = " + fileId);
          }

          assertThat(rs.getString("file_name")).isEqualTo(fileName);
          assertThat(rs.getBoolean("actual")).isTrue();
          assertThat(rs.getString("mime_type")).isEqualTo(mimeType);
          assertThat(rs.getInt("portion_size")).isEqualTo(100);

        }

      }

      try (PreparedStatement ps = con.prepareStatement(
        "select * from file_portion where id = ? order by portion_index asc")) {

        ps.setString(1, fileId);

        try (ResultSet rs = ps.executeQuery()) {

          for (int i = 0; i < 7; i++) {

            if (!rs.next()) {
              Assertions.fail("Слишком мало записей в file_portion: i = " + i);
            }

            assertThat(rs.getInt("portion_index")).isEqualTo(i);
            assertThat(rs.getInt("content_size")).isEqualTo(100);
            assertThat(rs.getBytes("content")).isEqualTo(subArray(fileContent, i * 100, 100));

          }

          if (!rs.next()) {
            Assertions.fail("Слишком мало записей в file_portion: i = 7");
          }

          {
            assertThat(rs.getInt("portion_index")).isEqualTo(7);
            assertThat(rs.getInt("content_size")).isEqualTo(50);
            assertThat(rs.getBytes("content")).isEqualTo(subArray(fileContent, 700, 50));
          }

        }

      }

    }

  }


  @Test
  public void loadFileMeta() throws Exception {

    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);

    String fileId = Ids.generate();
    String fileName = Ids.str(10);
    String mimeType = Ids.str(10);

    fileRegister.uploadFile(fileId, fileName, mimeType, 100, new ByteArrayInputStream(fileContent));

    //
    //
    FileMeta fileMeta = fileRegister.loadFileMeta(fileId);
    //
    //

    assertThat(fileMeta).isNotNull();
    assertThat(fileMeta.id).isEqualTo(fileId);
    assertThat(fileMeta.fileName).isEqualTo(fileName);
    assertThat(fileMeta.mimeType).isEqualTo(mimeType);
    assertThat(fileMeta.portionSize).isEqualTo(100);
    assertThat(fileMeta.fileSize).isEqualTo(750);


  }

  @Test
  public void downloadFilePart__1() throws Exception {

    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);

    String fileId = Ids.generate();
    String fileName = Ids.str(10);
    String mimeType = Ids.str(10);

    fileRegister.uploadFile(fileId, fileName, mimeType, 100, new ByteArrayInputStream(fileContent));

    //
    //
    byte[] filePart = fileRegister.downloadFilePart(fileId, 320, 220);
    //
    //

    byte[] expected = new byte[220];
    System.arraycopy(fileContent, 320, expected, 0, 220);

    assertThat(filePart).isEqualTo(expected);

  }

  @Test
  public void downloadFilePart__2() throws Exception {

    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);

    for (int i = 0; i < 750; i++) {
      fileContent[i] = (byte) (i % 100);
    }

    String fileId = Ids.generate();
    String fileName = Ids.str(10);
    String mimeType = Ids.str(10);

    fileRegister.uploadFile(fileId, fileName, mimeType, 100, new ByteArrayInputStream(fileContent));

    //
    //
    byte[] filePart = fileRegister.downloadFilePart(fileId, 320, 20);
    //
    //

    byte[] expected = new byte[20];
    System.arraycopy(fileContent, 320, expected, 0, 20);

    assertThat(filePart).isEqualTo(expected);

  }

  @Test
  public void downloadFilePart__3() throws Exception {

    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);

    String fileId = Ids.generate();
    String fileName = Ids.str(10);
    String mimeType = Ids.str(10);

    fileRegister.uploadFile(fileId, fileName, mimeType, 100, new ByteArrayInputStream(fileContent));

    //
    //
    byte[] filePart = fileRegister.downloadFilePart(fileId, 710, 20);
    //
    //

    byte[] expected = new byte[20];
    System.arraycopy(fileContent, 710, expected, 0, 20);

    assertThat(filePart).isEqualTo(expected);

  }


  @Test
  public void downloadFilePart__4() throws Exception {

    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);

    String fileId = Ids.generate();
    String fileName = Ids.str(10);
    String mimeType = Ids.str(10);

    fileRegister.uploadFile(fileId, fileName, mimeType, 100, new ByteArrayInputStream(fileContent));

    //
    //
    byte[] filePart = fileRegister.downloadFilePart(fileId, 710, 60);
    //
    //

    byte[] expected = new byte[40];
    System.arraycopy(fileContent, 710, expected, 0, 40);

    assertThat(filePart).isEqualTo(expected);

  }

  @Test
  public void downloadFilePart__5() throws Exception {

    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);

    String fileId = Ids.generate();
    String fileName = Ids.str(10);
    String mimeType = Ids.str(10);

    fileRegister.uploadFile(fileId, fileName, mimeType, 100, new ByteArrayInputStream(fileContent));

    //
    //
    byte[] filePart = fileRegister.downloadFilePart(fileId, 0, 750);
    //
    //
    assertThat(filePart).isEqualTo(fileContent);

  }

  @Test
  public void downloadFilePart__6() throws Exception {

    Random random = new Random();
    byte[] fileContent = new byte[750];
    random.nextBytes(fileContent);

    String fileId = Ids.generate();
    String fileName = Ids.str(10);
    String mimeType = Ids.str(10);

    fileRegister.uploadFile(fileId, fileName, mimeType, 100, new ByteArrayInputStream(fileContent));

    //
    //
    byte[] filePart = fileRegister.downloadFilePart(fileId, 0, 1000);
    //
    //
    assertThat(filePart).isEqualTo(fileContent);

  }

}
