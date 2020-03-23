package kz.pompei.mars.registers;

import kz.pompei.mars.registers.model.FileMeta;

import java.io.InputStream;

public interface FileRegister {

  String uploadFile(String fileId, String fileName, String mimeType, int portionSize, InputStream inputStream);

  FileMeta loadFileMeta(String fileId);

  byte[] downloadFilePart(String fileId, long offset, long partSize);

}
