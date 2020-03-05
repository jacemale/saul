lazy val commonSettings = Seq(
  organization := "com.itv",
  version := "0.0.2",
  scalaOrganization := "org.typelevel",
  scalaVersion := "2.12.4-bin-typelevel-4",
  scalacOptions ++= Seq(
    "-Yliteral-types"
  ),
  credentials += Credentials(Path.userHome / ".ivy2" / ".hubsvc-credentials"),
  resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/ivy-releases/"))(
    Resolver.ivyStylePatterns
  ),
  resolvers += "Central Maven Repo" at "https://central.maven.org/maven2/",
  sources in(Compile, doc) := Seq.empty,
  publishArtifact in(Compile, packageDoc) := false,
  publishTo in ThisBuild := {
    val artifactory = "https://itvrepos.jfrog.io/itvrepos/oasvc-ivy"
    if (isSnapshot.value)
      Some("Artifactory Realm" at artifactory)
    else
      Some("Artifactory Realm" at artifactory + ";build.timestamp=" + new java.util.Date().getTime)
  },
)

lazy val root = project
  .in(file("."))
  .aggregate(core, http4s021, example)

lazy val core = project
  .in(file("core"))
  .settings(commonSettings)
  .settings(
    name := "saul-core",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.0.0",
      "com.chuusai"   %% "shapeless" % "2.3.3"
    )
  )

lazy val http4s021 = project
  .in(file("http4s-0-21"))
  .settings(commonSettings)
  .settings(
    name := "saul-http4s-0-21",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % "0.21.0"
    )
  )
  .dependsOn(core)

lazy val example = project
  .in(file("example"))
  .settings(commonSettings)
  .settings(
    name := "saul-example"
  )
  .dependsOn(core)
