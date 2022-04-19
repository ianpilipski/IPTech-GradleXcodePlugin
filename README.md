# IPTech-GradleXcodePlugin
A gradle plugin that builds Xcode projects

## Configuration

Using the plugin DSL:
```groovy
plugins {
    id 'com.iptech.gradle.xcode-plugin' version '0.1.0'
}
```
Using legacy plugin application:
```groovy
buildscript {
    repositories {
        maven {
            url 'https://plugins.gradle.org/m2/'
        }   
    }
    dependencies {
        classpath 'gradle.plugin.com.iptech.gradle:xcode-plugin:0.1.0'
    }   
}

apply plugin: 'com.iptech.gradle.xcode-plugin'
```

Configuring your build.gradle:
```groovy
xcode {
    projectPath = layout.projectDirectory.dir('xcode-project.xcodeproj')
    buildTypes {
        Debug {
            configuration = 'Debug'
            scheme = 'xcode-project'
            exportArchives {
                dev {
                    exportOptionsPlist = file('export-plists/export-development.plist')
                }
                adhoc {
                    exportOptionsPlist = file('export-plists/export-adhoc.plist')
                }
            }
        }
        Release {
            configuration = 'Release'
            scheme = 'xcode-project'
            exportArchives {
                dev {
                    exportOptionsPlist = file('export-plists/export-development.plist')
                }
                appstore {
                    exportOptionsPlist = file('export-plists/export-app-store.plist')
                }
            }
        }
    }
}
```

Configuratioin Options:
```groovy
xcode {
    projectPath = file('*.xcproj|*.xcworkspace')
        //(required) path to your xcode project or workspace

    buildDirectory = project.layout.dir('custompath')
        //(optional) path to the build output for the xcode plugin
        //Default: project.layout.buildDirectory.dir('xcode') // build/xcode
 
    derivedDataPath = project.layout.dir('custompath') 
        //(optional) path to where you want the derived data folder to be placed
        //Default: '<buildDirectory>/DerivedData'
    
    buildTypes {
        // here is where you define the flavors of builds you want to perform
        // you can use any name you would like for each flavor
        <buildName> {
            configuration = 'configuration name in xcode'
                //(required)
    
            scheme = 'schem in xcode'
                //(required)
            
            archivePath = file('custompath/archivename.xcarchive')
                //(optional) the path to the output filename of the archive to be produced
                //Default: <xcode.buildDirectory/archives/buildName.xcarchive>
  
            exportArchives {
                // the exports you want to produce from the archive
                // this uses xcodebuild -exportArchve option to produce the ipa
                // you can use any name you would like for each
                <exportName> {
                    exportOptionsPlist = file('pathtoyour.export.plist')
                        //(required) this is the full path to the plist that you would like to 
                        //           specify the type of export from the archive.
        
                    exportPath = project.layout.dir('customexportpath')
                        //(optional) this is the path you would like the exported archive
                        //           to be placed in.
                        //Default: <xcode.buildDirectory/archives-exported/bulidName>
  
                    archivePath = file('path to the archive to process for export')
                        //(optional) this is the path to a generated archive
                        //Default: <xcode.buildName.archivePath>
                }
            }   
        }
    }   
}
```

## Building
Once you hav configured your gradle.plugin file

```groovy
./gradlew build<buildType>
```