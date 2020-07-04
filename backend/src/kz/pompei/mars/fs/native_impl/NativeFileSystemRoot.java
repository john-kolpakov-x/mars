package kz.pompei.mars.fs.native_impl;

import kz.pompei.mars.fs.FileSystemRoot;
import kz.pompei.mars.fs.FolderView;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class NativeFileSystemRoot implements FileSystemRoot {
  @Override
  public @NotNull List<String> rootNames() {
    return List.of(defaultRootName());
  }

  @Override
  public @NotNull String defaultRootName() {
    return "";
  }

  @Override
  public @NotNull FolderView root(String rootName) {
    if (!"".equals(rootName)) {
      throw new RuntimeException("OiA8r2JmVq :: No such root");
    }
    return new FolderViewNative(new File("/"));
  }
}
