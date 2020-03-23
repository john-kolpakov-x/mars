package kz.pompei.mars.registers.impl;

import kz.pompei.mars.registers.FileRegister;
import kz.pompei.mars.registers.model.FileMeta;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FileRegisterImpl implements FileRegister {
  @Override
  public String uploadFile(String fileId, String fileName, String mimeType, int portionSize, InputStream inputStream) {
    return null;
  }

  @Override
  public FileMeta loadFileMeta(String fileId) {
    return null;
  }

  @Override
  public byte[] downloadFilePart(String fileId, long offset, long partSize) {
    return new byte[0];
  }
}
