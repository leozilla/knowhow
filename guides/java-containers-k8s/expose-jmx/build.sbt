import sbt.Keys._
import sbt.{Def, Test, _}
object Packaging {
  import com.typesafe.sbt.packager.MappingsHelper.contentOf
  import com.typesafe.sbt.packager.Keys._
  import com.typesafe.sbt.packager.docker.{Cmd, ExecCmd}
  import sbtdocker.Instructions
  import sbtdocker.staging.CopyFile

  // the directories inside the container
  private object ContainerDirs {
    val AppInstallDir = "/opt" // use same install location for cloudflow and none cloudflow apps
    val AppConfigDir = "/etc/myapp" // directory containing application config files
    val AppConfigJmxDir = s"$AppConfigDir/jmx"
  }

  // directories in the git repository where to find the application config which is then copied into the docker image
  object ResourcesDirs {
    val appConfigDir: File = RootDir / "build" / "app-config"
    val appConfigJmxDir: File = appConfigDir / "jmx"
  }

  object Jmx {
    // usually credentials should be added via k8s secrets but unfortunately this is currently not possible as the jmx password file access must be modified after its
    // mounted, for this we would need to use k8s initContainers which are not yet supported by cloudflow
    // for testing and dev purposes we therefor add the credentials file directly to the container, this should not be used in prod!
    private val DevPasswordFileName = "dev-jmxremote.password"
    val devPasswordFilePath: String = s"${ContainerDirs.AppConfigJmxDir}/$DevPasswordFileName" // file path in docker

    def passwdFileChownCmd(user: String): Seq[String] = Seq("chown", user, devPasswordFilePath) // jmx password file must be owned by user of container

    val passwdFileChmodCmd: Seq[String] = Seq("chmod", "0400", devPasswordFilePath) // jmx password file must have 0400 access
  }

  object PackageMappingKeys {
    val Jmx = "jmx-config"
  }

  // see: https://www.scala-sbt.org/sbt-native-packager/formats/docker.html#publishing-settings
  def plainAppSettings(projectBaseDir: File) = Seq(
    Docker / dockerPackageMappings ++= packageMappings,
    dockerCommands := dockerCommands.value.flatMap {
      case useDaemonUserCmd@Cmd("USER", DockerUserId0) =>
        Seq(
          Cmd("COPY", PackageMappingKeys.Jmx, ContainerDirs.AppConfigJmxDir),
          ExecCmd("RUN", Jmx.passwdFileChownCmd(DockerUserId): _*),
          ExecCmd("RUN", Jmx.passwdFileChmodCmd: _*),
          useDaemonUserCmd
        )
      case cmd =>
        Seq(cmd)
    }
  )

  private def packageMappings =
    contentOf(ResourcesDirs.appConfigJmxDir).map { case (src, dst) => src -> s"${PackageMappingKeys.Jmx}/$dst" }
}