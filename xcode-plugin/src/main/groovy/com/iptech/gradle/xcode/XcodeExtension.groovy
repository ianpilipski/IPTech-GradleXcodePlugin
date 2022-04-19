package com.iptech.gradle.xcode

import com.iptech.gradle.xcode.api.*
import com.iptech.gradle.xcode.internal.executors.*
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.WorkResult
import org.gradle.process.ExecResult

import javax.inject.Inject

abstract class XcodeExtension {
    private final XcodeBuildExecutor xcodeBuildExecutor
    private final ResignAppExecutor resignAppExecutor
    private final ExportArchiveExecutor exportArchiveExecutor
    private final ArchiveExecutor archiveExecutor
    private final InstallProvisioningProfilesExecutor installProvisioningProfilesExecutor
    private final TestFlightExecutor testFlightExecutor

    @InputDirectory abstract DirectoryProperty getProjectPath()
    @InputDirectory abstract DirectoryProperty getDerivedDataPath()
    @InputDirectory abstract DirectoryProperty getBuildDirectory()
    @InputFiles abstract Property<FileCollection> getProvisioningProfiles()
    @Input @Optional abstract Property<String> getUserName()
    @Input @Optional abstract Property<String> getPassword()

    final NamedDomainObjectContainer<BuildType> buildTypes

    @Inject
    XcodeExtension(ObjectFactory objectFactory) {
        buildTypes = objectFactory.domainObjectContainer(BuildType)
        this.xcodeBuildExecutor = objectFactory.newInstance(XcodeBuildExecutor.class)
        this.resignAppExecutor = objectFactory.newInstance(ResignAppExecutor.class)
        this.exportArchiveExecutor = objectFactory.newInstance(ExportArchiveExecutor.class, xcodeBuildExecutor)
        this.archiveExecutor = objectFactory.newInstance(ArchiveExecutor.class, xcodeBuildExecutor)
        this.installProvisioningProfilesExecutor = objectFactory.newInstance(InstallProvisioningProfilesExecutor.class)
        this.testFlightExecutor = objectFactory.newInstance(TestFlightExecutor.class)
    }

    ExecResult xcodeBuild(XcodeBuildSpec spec) {
        return xcodeBuildExecutor.exec(spec)
    }

    ExecResult exportArchive(ExportArchiveSpec spec) {
        return exportArchiveExecutor.exec(spec)
    }

    ExecResult resignApp(ResignAppSpec spec) {
        return resignAppExecutor.exec(spec)
    }

    ExecResult archive(ArchiveSpec spec) {
        return archiveExecutor.exec(spec)
    }

    WorkResult installProvisioningProfiles(InstallProvisioningProfilesSpec spec) {
        return installProvisioningProfilesExecutor.exec(spec)
    }

    ExecResult upload(TestFlightSpec spec) {
        return testFlightExecutor.upload(spec)
    }

    ExecResult validate(TestFlightSpec spec) {
        return testFlightExecutor.validate(spec)
    }
}
