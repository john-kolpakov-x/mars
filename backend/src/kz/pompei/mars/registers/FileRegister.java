package kz.pompei.mars.registers;

import kz.pompei.mars.registers.model.FileMeta;

import java.io.InputStream;

public interface FileRegister {

  void uploadFile(String fileId, String fileName, String mimeType, int portionSize, InputStream inputStream) throws Exception;

  default FileMeta loadFileMeta(String fileId) throws Exception {
    FileMeta fileMeta = loadFileMetaOrNull(fileId);
    if (fileMeta == null) throw new RuntimeException("No file with id = " + fileId);
    return fileMeta;
  }

  FileMeta loadFileMetaOrNull(String fileId) throws Exception;

  byte[] downloadFilePart(String fileId, long offset, int partSize) throws Exception;

}
