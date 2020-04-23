package com.iptech.gradle.xcode

import com.iptech.gradle.xcode.api.ArchiveSpec
import com.iptech.gradle.xcode.api.BuildType
import com.iptech.gradle.xcode.api.ExportArchiveType
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import com.iptech.gradle.xcode.api.XcodeProjectPathSpec
import com.iptech.gradle.xcode.tasks.Archive
import com.iptech.gradle.xcode.tasks.Clean
import com.iptech.gradle.xcode.tasks.ExportArchive
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class XcodePlugin implements Plugin<Project> {
    private Project project
    private XcodeExtension xcode

    @Override
    void apply(Project project) {
        this.project = project
        applyBasePlugin()
        createXcodeExtension()
        establishConventions()
        createCleanTask()
        xcode.buildTypes.all(this.&buildTypeAdded)
    }

    private void applyBasePlugin() {
        if(!project.pluginManager.hasPlugin('base')) {
            project.pluginManager.apply('base')
        }
    }

    private void createXcodeExtension() {
        xcode = project.extensions.create('xcode', XcodeExtension.class)
    }

    private void establishConventions() {
        project.tasks.withType(XcodeProjectPathSpec).configureEach {
            projectPath.convention(xcode.projectPath)
        }

        project.tasks.withType(XcodeBuildSpec).configureEach {
            projectPath.convention(xcode.projectPath)
            derivedDataPath.convention(xcode.derivedDataPath)
        }

        project.tasks.withType(ArchiveSpec).configureEach {
            projectPath.convention(xcode.projectPath)
            derivedDataPath.convention(xcode.derivedDataPath)
        }

        project.tasks.withType(Clean).configureEach {
            projectPath.convention(xcode.projectPath)
            derivedDataPath.convention(xcode.derivedDataPath)
        }
    }

    private void createCleanTask() {
        project.tasks.clean.dependsOn(project.tasks.create('xcodeClean'))
    }

    private void buildTypeAdded(BuildType buildType) {
        buildType.archivePath.convention(project.layout.buildDirectory.dir("archives/${buildType.name}.xcarchive"))

        project.tasks.xcodeClean.dependsOn(
            project.tasks.create("xcodeClean${buildType.name}", Clean) {
                scheme = buildType.scheme
            }
        )

        Task taskArchive = project.tasks.create("xcodeArchive${buildType.name}", Archive) {
            scheme = buildType.scheme
            configuration = buildType.configuration
            archivePath = buildType.archivePath
        }

        buildType.exportArchives.all { ExportArchiveType eat ->
            eat.exportPath.convention(project.layout.buildDirectory.dir("archives-exported/${buildType.name}-${eat.name}"))
            eat.archivePath.convention(buildType.archivePath)

            project.tasks.create("xcodeExportArchive${buildType.name}-${eat.name}", ExportArchive) {
                exportPath = eat.exportPath
                exportOptionsPlist = eat.exportOptionsPlist
                archivePath = eat.archivePath

                dependsOn(taskArchive)
            }
        }
    }
}
