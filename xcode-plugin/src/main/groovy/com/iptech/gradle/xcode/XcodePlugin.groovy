package com.iptech.gradle.xcode

import com.iptech.gradle.xcode.api.ArchiveSpec
import com.iptech.gradle.xcode.api.BuildType
import com.iptech.gradle.xcode.api.ExportArchiveType
import com.iptech.gradle.xcode.api.ProvisioningProfilesSpec
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import com.iptech.gradle.xcode.api.XcodeProjectPathSpec
import com.iptech.gradle.xcode.api.TestFlightUploadConfig
import com.iptech.gradle.xcode.tasks.Archive
import com.iptech.gradle.xcode.tasks.Clean
import com.iptech.gradle.xcode.tasks.ExportArchive
import com.iptech.gradle.xcode.tasks.InstallProvisioningProfiles
import com.iptech.gradle.xcode.tasks.UnInstallProvisioningProfiles
import com.iptech.gradle.xcode.tasks.TestFlightUpload
import com.iptech.gradle.xcode.tasks.TestFlightValidate
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class XcodePlugin implements Plugin<Project> {
    private Project project
    private XcodeExtension xcode
    private BuildType defaultBuildType
    private Task installProfilesTask
    private Task removeProfilesTask

    @Override
    void apply(Project project) {
        this.project = project
        applyBasePlugin()
        createXcodeExtension()
        establishConventions()
        createCoreTasks()
        xcode.buildTypes.all(this.&buildTypeAdded)
        xcode.testFlightUploads.all(this.&testFlightUploadAdded)
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
        xcode.buildDirectory.convention(project.layout.buildDirectory.dir('xcode'))
        xcode.derivedDataPath.convention(xcode.buildDirectory.map { it.dir('DerivedData') })

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

        project.tasks.withType(ProvisioningProfilesSpec).configureEach {
            provisioningProfiles.convention(xcode.provisioningProfiles)
        }
    }

    private void createCoreTasks() {
        createCleanTask()
        createInstallProfilesTask()
    }

    private void createCleanTask() {
        project.tasks.clean.dependsOn(project.tasks.create('xcodeClean'))
    }

    private void createInstallProfilesTask() {
        removeProfilesTask = project.tasks.create("xcodeRemoveProvisioningProfiles", UnInstallProvisioningProfiles)
        installProfilesTask = project.tasks.create("xcodeInstallProvisioningProfiles", InstallProvisioningProfiles) {
            dependsOn(removeProfilesTask)
        }
    }

    private void buildTypeAdded(BuildType buildType) {
        XcodeExtension xcode = this.xcode
        if(!defaultBuildType) defaultBuildType = buildType

        buildType.archivePath.convention(xcode.buildDirectory.dir("archives/${buildType.name}.xcarchive"))
        buildType.projectPath.convention(xcode.projectPath)

        project.tasks.xcodeClean.dependsOn(
            project.tasks.create("xcodeClean${buildType.name}", Clean) {
                scheme = buildType.scheme
            }
        )

        Task taskArchive = project.tasks.create("xcodeArchive${buildType.name}", Archive) {
            scheme = buildType.scheme
            configuration = buildType.configuration
            archivePath = buildType.archivePath
            projectPath = buildType.projectPath
            dependsOn(installProfilesTask)
        }

        Task taskAssemble = project.tasks.create("assemble${buildType.name}") {
            group 'build'
        }

        Task taskBuild = project.tasks.create("build${buildType.name}") {
            group 'build'
            dependsOn(taskAssemble) //TODO: add check tasks for Xcode
        }

        if(defaultBuildType == buildType) {
            project.tasks.assemble.dependsOn(taskAssemble)
        }

        buildType.exportArchives.all { ExportArchiveType eat ->
            eat.exportPath.convention(xcode.buildDirectory.dir("archives-exported/${buildType.name}-${eat.name}"))
            eat.archivePath.convention(buildType.archivePath)
            eat.upload.convention(false)
            eat.validate.convention(false)

            def exportTask = project.tasks.create("xcodeExportArchive${buildType.name}-${eat.name}", ExportArchive) {
                exportPath = eat.exportPath
                exportOptionsPlist = eat.exportOptionsPlist
                archivePath = eat.archivePath

                dependsOn(taskArchive)
                taskAssemble.dependsOn(delegate)
            }

            def validateTask = project.tasks.create("xcodeValidateApp${buildType.name}-${eat.name}", TestFlightValidate) {
                appFile = eat.appFile
                appType = eat.appType
                password = eat.password
                userName = eat.userName

                onlyIf { eat.validate.get() }
                dependsOn(exportTask)
            }

            project.tasks.create("xcodeUploadApp${buildType.name}-${eat.name}", TestFlightUpload) {
                appFile = eat.appFile
                appType = eat.appType
                password = eat.password
                userName = eat.userName

                onlyIf { eat.upload.get() }
                dependsOn(validateTask, exportTask)
            }
        }
    }

    private void testFlightUploadAdded(TestFlightUploadConfig testFlightUpload) {
        def validateTask = project.tasks.create("xcodeValidateApp${testFlightUpload.name}", TestFlightValidate) {
            appFile = testFlightUpload.appFile
            appType = testFlightUpload.appType
            password = testFlightUpload.password
            userName = testFlightUpload.userName
        }

        project.tasks.create("xcodeUploadApp${testFlightUpload.name}", TestFlightUpload) {
            group "Deploy"
            description "Upload ${testFlightUpload.name} to TestFlight"
            appFile = testFlightUpload.appFile
            appType = testFlightUpload.appType
            password = testFlightUpload.password
            userName = testFlightUpload.userName

            dependsOn(validateTask)
        } 
    }
}
