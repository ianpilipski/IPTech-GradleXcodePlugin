package com.iptech.gradle.xcode

import com.iptech.gradle.xcode.api.ArchiveSpec
import com.iptech.gradle.xcode.api.BuildType
import com.iptech.gradle.xcode.api.ExportArchiveSpec
import com.iptech.gradle.xcode.api.ResignAppSpec
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import com.iptech.gradle.xcode.internal.executors.ArchiveExecutor
import com.iptech.gradle.xcode.internal.executors.ExportArchiveExecutor
import com.iptech.gradle.xcode.internal.executors.ResignAppExecutor
import com.iptech.gradle.xcode.internal.executors.XcodeBuildExecutor
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.process.ExecResult

import javax.inject.Inject

abstract class XcodeExtension {
    private final XcodeBuildExecutor xcodeBuildExecutor
    private final ResignAppExecutor resignAppExecutor
    private final ExportArchiveExecutor exportArchiveExecutor
    private final ArchiveExecutor archiveExecutor

    @InputDirectory abstract DirectoryProperty getProjectPath()
    @InputDirectory abstract DirectoryProperty getDerivedDataPath()
    @InputDirectory abstract DirectoryProperty getBuildDirectory()

    final NamedDomainObjectContainer<BuildType> buildTypes

    @Inject
    XcodeExtension(ObjectFactory objectFactory) {
        buildTypes = objectFactory.domainObjectContainer(BuildType)
        this.xcodeBuildExecutor = objectFactory.newInstance(XcodeBuildExecutor.class)
        this.resignAppExecutor = objectFactory.newInstance(ResignAppExecutor.class)
        this.exportArchiveExecutor = objectFactory.newInstance(ExportArchiveExecutor.class, xcodeBuildExecutor)
        this.archiveExecutor = objectFactory.newInstance(ArchiveExecutor.class, xcodeBuildExecutor)
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
}
