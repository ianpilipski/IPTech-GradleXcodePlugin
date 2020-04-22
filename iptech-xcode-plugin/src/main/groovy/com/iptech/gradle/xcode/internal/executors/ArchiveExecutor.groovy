package com.iptech.gradle.xcode.internal.executors

import com.iptech.gradle.xcode.XcodeExtension
import com.iptech.gradle.xcode.api.ArchiveSpec
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import org.gradle.api.model.ObjectFactory
import org.gradle.process.ExecResult

import javax.inject.Inject

class ArchiveExecutor {
    private final XcodeBuildExecutor xcodeBuildExecutor
    private final ObjectFactory objectFactory

    @Inject
    ArchiveExecutor(XcodeBuildExecutor xcodeBuildExecutor, ObjectFactory objectFactory) {
        this.xcodeBuildExecutor = xcodeBuildExecutor
        this.objectFactory = objectFactory
    }

    ExecResult exec(ArchiveSpec archiveSpec) {
        XcodeBuildSpec spec = objectFactory.newInstance(XcodeBuildSpec.class)
        spec.configuration = archiveSpec.configuration
        spec.scheme = archiveSpec.scheme
        spec.archivePath = archiveSpec.archivePath
        spec.derivedDataPath = archiveSpec.derivedDataPath
        spec.xcodeWorkspace = archiveSpec.xcodeWorkspace
        spec.xcodeProject = archiveSpec.xcodeProject
        spec.additionalArguments = archiveSpec.additionalArguments

        if(archiveSpec.CODE_SIGN_IDENTITY.isPresent()) spec.additionalArguments.add(archiveSpec.CODE_SIGN_IDENTITY.get())
        if(archiveSpec.CODE_SIGN_STYLE.isPresent()) spec.additionalArguments.add(archiveSpec.CODE_SIGN_STYLE.get())
        if(archiveSpec.DEVELOPMENT_TEAM.isPresent()) spec.additionalArguments.add(archiveSpec.DEVELOPMENT_TEAM.get())
        if(archiveSpec.PROVISIONING_PROFILE_SPECIFIER.isPresent()) spec.additionalArguments.add(archiveSpec.PROVISIONING_PROFILE_SPECIFIER.get())
        if(archiveSpec.CODE_SIGNING_REQUIRED.isPresent()) spec.additionalArguments.add(archiveSpec.CODE_SIGNING_REQUIRED.get())
        if(archiveSpec.CODE_SIGNING_ALLOWED.isPresent()) spec.additionalArguments.add(archiveSpec.CODE_SIGNING_ALLOWED.get())

        return xcodeBuildExecutor.xcodeBuild(spec)
    }
}
