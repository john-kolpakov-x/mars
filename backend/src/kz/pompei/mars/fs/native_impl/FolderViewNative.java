package kz.pompei.mars.fs.native_impl;

import kz.pompei.mars.fs.FileCreating;
import kz.pompei.mars.fs.FolderView;
import kz.pompei.mars.fs.NodeView;
import kz.pompei.mars.fs.errors.NewNameAlreadyExistsInFolder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderViewNative extends NodeViewNative implements FolderView {

  public FolderViewNative(@NotNull File file) {
    super(file);
  }

  @Override
  public @NotNull List<NodeView> children() {

    var files = file.listFiles();

    if (files == null) return List.of();

    List<NodeView> ret = new ArrayList<>();

    for (File subFile : files) {
      ret.add(NodeViewNative.create(subFile));
    }

    return ret;
  }

  @Override
  public @NotNull FolderView createFolder(@NotNull String folderName) throws NewNameAlreadyExistsInFolder {
    throw new RuntimeException("not impl yet: FolderViewNative.createFolder");
  }

  @Override
  public @NotNull FileCreating createFile(@NotNull String fileName) throws NewNameAlreadyExistsInFolder {
    throw new RuntimeException("not impl yet: FolderViewNative.createFile");
  }

}
