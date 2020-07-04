package kz.pompei.mars.fs.native_impl;

import kz.greetgo.util.RND;
import kz.pompei.mars.fs.errors.NewNameAlreadyExistsInFolder;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileViewNativeTest {

  @Test(expectedExceptions = NewNameAlreadyExistsInFolder.class)
  public void rename__throws_NewNameAlreadyExistsInFolder() throws Exception {

    var testDir = Paths
      .get("build")
      .resolve(getClass().getSimpleName())
      .resolve("" + RND.intStr(5))
      .toAbsolutePath();

    testDir.toFile().mkdirs();

    testDir.resolve("fileX").toFile().createNewFile();
    var fileY = testDir.resolve("fileY").toFile();
    fileY.createNewFile();

    var nodeViewY = NodeViewNative.create(fileY);

    //
    //
    nodeViewY.rename("fileX");
    //
    //

  }

  @Test
  public void rename__ok() throws Exception {

    var testDir = Paths
      .get("build")
      .resolve(getClass().getSimpleName())
      .resolve("" + RND.intStr(5))
      .toAbsolutePath();

    testDir.toFile().mkdirs();

    var fileX = testDir.resolve("fileX").toFile();

    assertThat(fileX).doesNotExist();

    var fileY = testDir.resolve("fileY").toFile();
    fileY.createNewFile();

    var nodeViewY = NodeViewNative.create(fileY);

    //
    //
    var newNode = nodeViewY.rename("fileX");
    //
    //

    assertThat(fileX).exists();

    assertThat(newNode.name()).isEqualTo("fileX");
  }
}
