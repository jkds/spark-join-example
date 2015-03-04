import AssemblyKeys._ // put this at the top of the file

assemblySettings

mainClass in assembly := Some("com.datastax.demo.DemoApp")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

