# Larry

Larry is a chatbot developed as a greenfield Java project. Given below are instructions on how to use it.

## Building and running with Gradle

This project uses the Gradle Wrapper, so you do not need to install Gradle globally.
From the project root, use:

```bash
./gradlew build
./gradlew run --console=plain
```

The first command compiles the project and runs its automated tests. The second
command starts Larry. Gradle downloads the project's declared Gradle version on
the first run and reuses it afterwards.

## Creating and running a fat JAR

Create a fresh fat JAR with the Shadow plugin from the project root:

```bash
./gradlew clean shadowJar
```

The generated file is `build/libs/larry.jar`. It contains Larry's compiled
classes and all runtime dependencies, and its manifest identifies `larry.Larry`
as the entry point.

Run the JAR using Java 25:

```bash
java -jar build/libs/larry.jar
```

Larry reads and writes `data/larry.txt` relative to the directory where this
command is run. Enter `bye` to exit the application.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/larry/Larry.java` file, right-click it, and choose `Run Larry.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
   ██╗      █████╗ ██████╗ ██████╗ ██╗   ██╗
   ██║     ██╔══██╗██╔══██╗██╔══██╗╚██╗ ██╔╝
   ██║     ███████║██████╔╝██████╔╝ ╚████╔╝
   ██║     ██╔══██║██╔══██╗██╔══██╗  ╚██╔╝
   ███████╗██║  ██║██║  ██║██║  ██║   ██║
   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.
