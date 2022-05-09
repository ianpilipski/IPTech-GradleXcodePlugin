package com.iptech.gradle.xcode

import com.iptech.gradle.xcode.api.*
import com.iptech.gradle.xcode.internal.executors.*
import com.iptech.gradle.xcode.tasks.ExportArchive
import com.iptech.gradle.xcode.tasks.ResignApp
import org.gradle.api.Action
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
import org.gradle.util.ConfigureUtil

import javax.inject.Inject

abstract class XcodeExtension {
    private final ObjectFactory objectFactory
    private final XcodeBuildExecutor xcodeBuildExecutor
    private final ResignAppExecutor resignAppExecutor
    private final ExportArchiveExecutor exportArchiveExecutor
    private final ArchiveExecutor archiveExecutor
    private final ProvisioningProfilesExecutor installProvisioningProfilesExecutor
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
        this.objectFactory = objectFactory
        buildTypes = objectFactory.domainObjectContainer(BuildType)
        this.xcodeBuildExecutor = objectFactory.newInstance(XcodeBuildExecutor.class)
        this.resignAppExecutor = objectFactory.newInstance(ResignAppExecutor.class)
        this.exportArchiveExecutor = objectFactory.newInstance(ExportArchiveExecutor.class, xcodeBuildExecutor)
        this.archiveExecutor = objectFactory.newInstance(ArchiveExecutor.class, xcodeBuildExecutor)
        this.installProvisioningProfilesExecutor = objectFactory.newInstance(ProvisioningProfilesExecutor.class)
        this.testFlightExecutor = objectFactory.newInstance(TestFlightExecutor.class)
    }

    ExecResult xcodeBuild(Action<? extends XcodeBuildSpec> spec) {
        return xcodeBuildExecutor.exec(createSpec(XcodeBuildSpec.class, action))
    }
    
    ExecResult xcodeBuild(XcodeBuildSpec spec) {
        return xcodeBuildExecutor.exec(spec)
    }

    File exportArchive(Action<? extends ExportArchiveSpec> action) {
        return exportArchiveExecutor.exec(createSpec(ExportArchiveSpec.class, action))
    }

    File exportArchive(ExportArchiveSpec spec) {
        return exportArchiveExecutor.exec(spec)
    }

    ExecResult resignApp(Action<? extends ResignAppSpec> action) {
        return resignAppExecutor.exec(createSpec(ResignApp.class, action))
    }

    ExecResult resignApp(ResignAppSpec spec) {
        return resignAppExecutor.exec(spec)
    }

    ExecResult archive(Action<? extends ArchiveSpec> action) {
        return archiveExecutor.exec(createSpec(ArchiveSpec.class, action))
    }

    ExecResult archive(ArchiveSpec spec) {
        return archiveExecutor.exec(spec)
    }

    WorkResult installProvisioningProfiles(Action<? super ProvisioningProfilesSpec> action) {
        return installProvisioningProfiles(createSpec(ProvisioningProfilesSpec.class, action))
    }

    WorkResult installProvisioningProfiles(ProvisioningProfilesSpec spec) {
        return installProvisioningProfilesExecutor.execInstall(spec)
    }

    WorkResult unInstallProvisioningProfiles(Action<? extends ProvisioningProfilesSpec> action) {
        return unInstallProvisioningProfiles(createSpec(ProvisioningProfilesSpec.class, action))
    }

    WorkResult unInstallProvisioningProfiles(ProvisioningProfilesSpec spec) {
        return installProvisioningProfilesExecutor.execUnInstall(spec)
    }

    ExecResult upload(Action<? extends TestFlightSpec> action) {
        return testFlightExecutor.upload(createSpec(TestFlightSpec.class, action))
    }

    ExecResult upload(TestFlightSpec spec) {
        return testFlightExecutor.upload(spec)
    }

    ExecResult validate(Action<? extends TestFlightSpec> action) {
        return testFlightExecutor.validate(createSpec(TestFlightSpec.class, action))
    }

    ExecResult validate(TestFlightSpec spec) {
        return testFlightExecutor.validate(spec)
    }

    private <T> T createSpec(Class<? extends T> aClass, Action<? extends T> action) {
        def spec = objectFactory.newInstance(aClass)
        action.execute(spec)
        return spec
    }
}
