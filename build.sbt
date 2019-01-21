lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion    = "2.5.11"
lazy val slickVersion = "3.2.1"
lazy val liquibaseVersion = "3.6.2"

lazy val scalaTestVersion = "3.0.1"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.trip",
      scalaVersion    := "2.12.4"
    )),
    name := "trip",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
      "com.typesafe.slick" %% "slick" % slickVersion,
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      //Надо понять, нужны ли ниже идущие библиотеки
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
      "com.github.tminglei" %% "slick-pg" % "0.15.2",
      "com.github.tminglei" %% "slick-pg_spray-json" % "0.15.2",
      //??
      "org.postgresql" % "postgresql" % "42.1.4",

      "org.liquibase" % "liquibase-core" % liquibaseVersion,

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion  % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion      % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion      % Test,
      "org.scalatest"     %% "scalatest"            % scalaTestVersion % Test
    )
  )
