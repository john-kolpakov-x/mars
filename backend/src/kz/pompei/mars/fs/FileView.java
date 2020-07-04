package kz.pompei.mars.fs;

import kz.pompei.mars.fs.errors.NodeIsAbsent;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileView extends NodeView {

  long length() throws NodeIsAbsent;

  void setLength(long newLength) throws NodeIsAbsent;

  OutputStream appendOutput() throws NodeIsAbsent;

  OutputStream resetOutput() throws NodeIsAbsent;

  InputStream inputStream() throws NodeIsAbsent;

}
