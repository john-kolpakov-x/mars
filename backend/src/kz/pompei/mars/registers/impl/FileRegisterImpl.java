package kz.pompei.mars.registers.impl;

import kz.pompei.mars.database.PostgresConnect;
import kz.pompei.mars.registers.FileRegister;
import kz.pompei.mars.registers.model.FileMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class FileRegisterImpl implements FileRegister {

  @Autowired
  private PostgresConnect postgresConnect;


  @Override
  public void uploadFile(String fileId, String fileName, String mimeType, int portionSize, InputStream inputStream) throws Exception {

    try (Connection con = postgresConnect.getDataSource().getConnection()) {

      try (PreparedStatement ps = con.prepareStatement("insert into file_meta" +
        "        (id, actual, file_name, mime_type, portion_size)" +
        " values (?,  false,  ?,         ?,          ?)")) {

        ps.setString(1, fileId);
        ps.setString(2, fileName);
        ps.setString(3, mimeType);
        ps.setInt(4, portionSize);

        ps.executeUpdate();

      }

      long fileSize = 0;

      try (InputStream ignore = inputStream) {

        byte[] buffer = new byte[portionSize];

        long index = 0;

        while (true) {

          int readByteCount = inputStream.read(buffer);
          if (readByteCount < 0) {
            break;
          }

          fileSize += readByteCount;

          byte[] bytes = buffer;
          if (readByteCount < portionSize) {
            bytes = new byte[readByteCount];
            System.arraycopy(buffer, 0, bytes, 0, readByteCount);
          }

          try (PreparedStatement ps = con.prepareStatement("insert into file_portion " +
            "(id, portion_index, content, content_size) values (?, ?, ?, ?)")) {

            ps.setString(1, fileId);
            ps.setLong(2, index++);
            ps.setBytes(3, bytes);
            ps.setInt(4, bytes.length);

            ps.executeUpdate();

          }

        }


      }

      try (PreparedStatement ps = con.prepareStatement("update file_meta set actual = true, file_size = ? where id = ?")) {

        ps.setLong(1, fileSize);
        ps.setString(2, fileId);

        int updateCount = ps.executeUpdate();
        if (updateCount == 0) {
          throw new RuntimeException("Cannot update just created file_meta for id = " + fileId);
        }
      }

    }

  }

  @Override
  public FileMeta loadFileMetaOrNull(String fileId) throws Exception {

    try (Connection con = postgresConnect.getDataSource().getConnection()) {

      try (PreparedStatement ps = con.prepareStatement("select * from file_meta where id = ? and actual = true")) {

        ps.setString(1, fileId);

        try (ResultSet rs = ps.executeQuery()) {

          if (!rs.next()) return null;

          {
            var ret = new FileMeta();
            ret.id = rs.getString("id");
            ret.fileName = rs.getString("file_name");
            ret.mimeType = rs.getString("mime_type");
            ret.portionSize = rs.getInt("portion_size");
            ret.fileSize = rs.getInt("file_size");
            return ret;
          }
        }

      }

    }

  }

  @Override
  public byte[] downloadFilePart(String fileId, long offset, int partSize) throws Exception {

    FileMeta fileMeta = loadFileMeta(fileId);

    if (offset >= fileMeta.fileSize) {
      return new byte[0];
    }

    if (offset + partSize >= fileMeta.fileSize) {
      partSize = (int) (fileMeta.fileSize - offset);
    }

    byte[] ret = new byte[partSize];

    {
      int portionSize = fileMeta.portionSize;

      int leftSkip = (int) (offset % portionSize);
      int index = (int) (offset / portionSize);

      byte[] firstPortion = loadPortion(fileId, index);
      if (portionSize - leftSkip >= partSize) {
        System.arraycopy(firstPortion, leftSkip, ret, 0, partSize);
        return ret;
      }
      System.arraycopy(firstPortion, leftSkip, ret, 0, portionSize - leftSkip);

      int position = portionSize - leftSkip;

      while (position < partSize) {

        byte[] portion = loadPortion(fileId, ++index);

        if (position + portionSize > partSize) {
          System.arraycopy(portion, 0, ret, position, partSize - position);
        } else {
          System.arraycopy(portion, 0, ret, position, portionSize);
        }

        position += portionSize;

      }
    }

    return ret;
  }

  private byte[] loadPortion(String fileId, long portionIndex) throws Exception {

    try (Connection con = postgresConnect.getDataSource().getConnection()) {

      try (PreparedStatement ps = con.prepareStatement(
        "select content from file_portion where id = ? and portion_index = ?")) {

        ps.setString(1, fileId);
        ps.setLong(2, portionIndex);

        try (ResultSet rs = ps.executeQuery()) {

          if (!rs.next()) {
            throw new RuntimeException("No file portion: fileId = " + fileId + ", portionIndex = " + portionIndex);
          }

          return rs.getBytes(1);

        }

      }

    }

  }
}
