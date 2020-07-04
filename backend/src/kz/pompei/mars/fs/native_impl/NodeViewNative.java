package kz.pompei.mars.fs.native_impl;

import kz.pompei.mars.fs.FolderView;
import kz.pompei.mars.fs.NodeView;
import kz.pompei.mars.fs.errors.AlreadyDeleted;
import kz.pompei.mars.fs.errors.CannotBeDeleted;
import kz.pompei.mars.fs.errors.NewNameAlreadyExistsInFolder;
import kz.pompei.mars.fs.errors.NoParent_ItIsRoot;
import kz.pompei.mars.fs.errors.NodeIsAbsent;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class NodeViewNative implements NodeView {
  protected final File file;

  protected NodeViewNative(File file) {
    this.file = file;
    if (!file.exists()) {
      throw new RuntimeException("File is absent: " + file);
    }
  }

  public static NodeView create(File file) {
    return file.isDirectory() ? new FolderViewNative(file) : new FileViewNative(file);
  }

  @Override
  public @NotNull String name() throws NodeIsAbsent {
    return file.getName();
  }

  @Override
  public NodeView rename(@NotNull String newName) throws NewNameAlreadyExistsInFolder {

    var newFile = this.file.getParentFile().toPath().resolve(newName).toFile();

    if (newFile.exists()) throw new NewNameAlreadyExistsInFolder();

    file.renameTo(newFile);

    return create(newFile);
  }

  @Override
  public @NotNull FolderView parent() throws NoParent_ItIsRoot {
    throw new RuntimeException("not impl yet: NodeViewNative.parent");
  }

  @Override
  public void delete() throws AlreadyDeleted, CannotBeDeleted {
    throw new RuntimeException("not impl yet: NodeViewNative.delete");
  }
}
