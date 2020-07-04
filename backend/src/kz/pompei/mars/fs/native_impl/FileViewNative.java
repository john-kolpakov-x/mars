package kz.pompei.mars.fs.native_impl;

import kz.pompei.mars.fs.FileView;
import kz.pompei.mars.fs.errors.NodeIsAbsent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileViewNative extends NodeViewNative implements FileView {

  public FileViewNative(@NotNull File file) {
    super(file);
  }

  @Override
  public long length() throws NodeIsAbsent {
    return file.length();
  }

  @Override
  public void setLength(long newLength) throws NodeIsAbsent {
    throw new RuntimeException("not impl yet: FileViewNative.setLength");
  }

  @Override
  public OutputStream appendOutput() throws NodeIsAbsent {
    try {
      return new FileOutputStream(file, true);
    } catch (FileNotFoundException e) {
      throw new NodeIsAbsent();
    }
  }

  @Override
  public OutputStream resetOutput() throws NodeIsAbsent {
    try {
      return new FileOutputStream(file, false);
    } catch (FileNotFoundException e) {
      throw new NodeIsAbsent();
    }
  }

  @Override
  public InputStream inputStream() throws NodeIsAbsent {
    throw new RuntimeException("not impl yet: FileViewNative.inputStream");
  }

}
