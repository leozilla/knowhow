import sbt.Keys._
import sbt.{Def, Test, _}
object Packaging {
  import com.typesafe.sbt.packager.MappingsHelper.contentOf
  import com.typesafe.sbt.packager.Keys._
  import com.typesafe.sbt.packager.docker.{Cmd, ExecCmd}
  import sbtdocker.Instructions
  import sbtdocker.staging.CopyFile

  private val PlainDockerBaseImage = "myimage:openjdk8-alpine-4"

  // the directories inside the container
  private object ContainerDirs {
    val AppInstallDir = "/opt" // use same install location for cloudflow and none cloudflow apps
    val AppConfigDir = "/etc/myapp" // directory containing application config files
    val AppConfigLoggingDir = s"$AppConfigDir/logging"
  }

  // directories in the git repository where to find the application config which is then copied into the docker image
  object ResourcesDirs {
    val appConfigDir: File = RootDir / "build" / "app-config"
    val appConfigLoggingDir: File = appConfigDir / "logging"
  }

  object PackageMappingKeys {
    val Logging = "logging-config"
  }

  // see: https://www.scala-sbt.org/sbt-native-packager/formats/docker.html#publishing-settings
  def plainAppSettings(projectBaseDir: File) = Seq(
    dockerCommands := dockerCommands.value.flatMap {
      case useDaemonUserCmd@Cmd("USER", DockerUserId0) =>
        Seq(
          Cmd("COPY", PackageMappingKeys.Logging, ContainerDirs.AppConfigLoggingDir),
          useDaemonUserCmd
        )
      case cmd =>
        Seq(cmd)
    }
  )

  private def packageMappings =
      contentOf(ResourcesDirs.appConfigLoggingDir).map { case (src, dst) => src -> s"${PackageMappingKeys.Logging}/$dst" }
}