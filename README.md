# UHC Lebrel Plugin
This is a Spigot plugin used for hosting UHC competitions, in the future it will depend on Paper so it's recommended to use Paper instead. This plugin contains the basic features for hosting an UHC, and its main feature is that addons can easily be coded. Information about configuration of the plugin and how to code an addon is in the wiki at https://github.com/palomox/uhclebrel/wiki
## Downloading an artifact
To download an artifact, you have to go to my CI server (https://ci.palomox.ga) or download a jar from Hangar (paper plugin repository, coming soon)
## Compiling from source
As this project uses maven, it's easy to compile it. All the dependencies are hosted in repositories, except the paper dependency, as this plugin uses paper artifact and not paper-api. For this, you can compile paper from source (instructions at https://github.com/PaperMC/Paper) or run paperclip with `-Dpaperclip.install=true` argument.
## Implementing for using the API
This plugin does not contain an API, but it exposes classes, methods and events which can be used to code extensions, and I will add an api interface in next updates. 
The artifact that needs to be implemented is the plugin itself, and you can do so using maven, as the artifacts are hosted on my maven repository.
### Using Maven
You have to add this repository.
```xml
<repository>
  <id>palomox-repo</id>
  <url>https://repo.palomox.ga/maven-public</url>
</repository>
```
And this dependency
```xml
<dependency>
  <groupId>ga.palomox.plugins</groupId>
  <artifactId>uhclebrel</artifactId>
  <version>1.16.4-R0.1-SNAPSHOT</version>
</dependency>
```
### Using Gradle
You have to add this repository:
```groovy
maven {
        url 'https://repo.palomox.ga/maven-public/'
    }
```
And this dependency:
```groovy
compileOnly 'ga.palomox.plugins:uhclebrel:1.16.4-R0.1-SNAPSHOT'
```
