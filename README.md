# ClickerGame

## Notatki

Wersja java podczas tworzenia projektu to 8 , nwm czy dziala na nowszych, chyba powinna bo mam w project structure ustawione SDK: openjdk-23 xd.

Zeby tak na serio odpalic projekt trzeba:
- zeby **zbudowac**, wejsc w Gradle po lewej stronie `lwjgl3/Tasks/build/build`, wtedy cos sie tam porobi, potem mozna buildowac z przycisku na gorze (powinien sie pojawic)
![image](https://github.com/user-attachments/assets/eb9b90ce-98a7-4e75-9afa-1933ce00fdb2)

- zeby **odpalic**, wejsc juz w plikach `lwjgl3/build/main/java/com.project.clicker.jwlgl3/Lwjgl3Launcher` i run class, potem mozna odpalac z przycisku na gorze (powinien sie pojawic)
<img width="491" alt="image" src="https://github.com/user-attachments/assets/353d9f29-09bd-4ee6-b6a5-1ca194ef40af" />

- nie mam pojecia kiedy trzeba budowac, jak zmienilem napis na przycisku to nie trzeba bylo, zmienil sie bez budowania

Zrobilem to na lapku, nie sprawdzalem czy jak pullne projekt na kompa to czy wgl zadziala


<br><br><br><br>
## Jakies graddlowe zaklęcia

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.



